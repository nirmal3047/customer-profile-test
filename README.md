# customer-profile-service

## Overview
This API is called after the user has signed up on the EzReach platform and successfully logged in. The purpose of this API is to take user input (Name, GSTIN, PAN, Udyog Aadhaar, Turnover) and create Customer's profile for EzReach platform. Further this service fetches details about the user from GST server using GSTIN and create customer's GST profile for OCEN based lending using those details and persist both the profiles in PostgreSQL database.

## Project Setup
This is a multi build Gradle project. The specifications are as follows:
* Gradle version: 6.7
* Java version: 1.8
* Spring Boot version: 2.3.2.RELEASE

Spring dependencies:
|Dependency                          |Purpose                     |
|:-----------------------------------|:---------------------------|
|spring-boot-starter-web             |For building web application|
|spring-boot-starter-validation      |For Bean Validation         |
|spring-cloud-starter-sleuth         |For tracibility             |
|spring-cloud-starter-netflix-hystrix|For circuit breaking        |
|spring-boot-starter-security        |For token validation        |
|spring-boot-starter-test            |For testing                 |
|spring-data-jpa                     |For persisting data         |

Other dependencies:
|Dependency                    |Purpose                     |
|:-----------------------------|:---------------------------|
|JUnit 5                       |For unit testing            |
|zonky                         |Embedded database for testing|
|java-jwt                      |For decoding id token       |
|flyway-core                   |For database migration      |
|wiremock                      |For mocking responses from GST for testing|
|springfox-swagger2            |For API documentation|


### Running project in local
Clone the repository and run the following command at the root directory
```
./gradlew service:bootRun
```

Go to **http://localhost:9290/health** in your browser and you should see **"Up"** on the screen

### Project structure
This project contains the following folders and files:

* **.github**: Github workflow

* **Diagram**: Sequence diagrams for the project

* **service**: Source code
    * src:
        * main
            * java
                * com.ezreach.customer.profile
                    * configuration
                    * controller
                    * entity
                    * exception
                    * repository
                    * service
                    * CustomerProfileApp.java
                    * HealthController.java
            * resources
        * test
            * java
                * com.ezreach.customer.profile
                    * controller
                    * repository
                    * service
            * resources


* **customer-profile-api.YAML**: API specifications




### Entities

##### 1. UserInput
|Fields      |Type  |Mandatory?|Description?|
|------------|:----:|:--------:|:-----------|
|name        |String|Yes       ||
|gstin       |String|No        |15 characters alphanumeric|
|pan         |String|No        |10 characters alphanumeric|
|udyogAadhaar|String|No        |12 characters numeric|
|turnover    |float |No        |Must be positive|

##### 2. Customer
|Fields      |Type  |Mandatory?|Description?|
|------------|:----:|:--------:|:-----------|
|customerId  |UUID  |Yes       |Generated customer id|
|userId      |UUID  |Yes       |UserId from AWS cognito|
|gstin       |String|Yes       |15 characters alphanumeric|
|pan         |String|Yes       |10 characters alphanumeric|
|udyogAadhaar|String|Yes       |12 characters numeric|
|turnover    |float |Yes       |Must be positive|
|name        |String||         |Legal name of the organisation (fetched from GST)|

##### 3. GstProfile
|Fields      |Type  |Mandatory?|Description?|
|------------|:----:|:--------:|:-----------|
|profileId   |UUID  |Yes       |Generated profile id|
|userId      |UUID  |Yes       |UserId from AWS cognito|
|gstDetails  |json  |          |Details fetched from GST server|

![alt text](https://github.com/EzReach/customer-profile-service/blob/main/Diagrams/ER-customer-profile.PNG "Entities for customer/profile")

##### Creating customer table
```
CREATE TABLE IF NOT EXISTS customer (
    customer_id UUID NOT NUll,
    user_id UUID NOT NULL UNIQUE,
    name VARCHAR(45),
    gstin CHAR(15),
    pan CHAR(10),
    udyog_aadhaar CHAR(12),
    email VARCHAR(45) NOT NULL,
    mobile VARCHAR(13),
    turnover DECIMAL,
    PRIMARY KEY (customer_id)
);
```

##### Creating GST Details table
```
CREATE TABLE IF NOT EXISTS onboard.gst_profile (
    profile_id UUID NOT NUll,
    user_id UUID NOT NULL UNIQUE,
    gst_details VARCHAR(1000) NOT NULL,
    PRIMARY KEY (profile_id),
    FOREIGN KEY (user_id)
  REFERENCES onboard.customer(user_id)
);
```


### Action sequence
* After the user logs in, the API Gateway provides the user(React) an access token
* This access token is sent along with the request for **POST /customer/profile** API call
* The API call is intercepted by the AWS API Gateway, the access token is verified with AWS Cognito
* If the token is valid, the request is sent to AWS Load Balancer, otherwise 401 Unauthorized is thrown to the user
* The ALB then sends the request to Spring Boot App running in EKS
* Spring Boot (customer-profile-service) again validates the token with AWS cognito
* The microservice creates the customer's profile using the input provided and persists the details in the database
* After creating Customer profile, the user requests for the creation of GST based profile
* The microservice calls GST/Mock GST server and fetches the user's data using GST number
* The GST/Mock GST server sends the data or Error message (if any) back to the customer-profile-service
* Then this data is persisted in AWS RDS PostgreSQL
* Http status **201 CREATED** or Error message (if any) is returned to the API Gateway and from there to the end user (React app)
![alt text](https://github.com/EzReach/customer-profile-service/blob/main/Diagrams/Sequence-Diagram.PNG "Sequence Diagram")





### API specs
Please visit [customer/profile API specs](https://github.com/EzReach/customer-profile-service/blob/main/customer-profile-api.yaml) for detailed API specification

### Exception Handling
In order to uniquely identify the exception and its source, we have defined custom error codes.

|Fields   |Type  |Description?|
|---------|:----:|:-----------|
|id       |UUID  |Id of the error message|
|errorCode|String|Unique code for every exception|
|message  |String|Description of the exception
|params   |Object|(Optional) Fields related to the exception|


##### Sample error message
```
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "errorCode": "404.001.001",
  "message": "Customer Not Found",
  "params": {}
}
```
**Error Code for the microservice = "001"**

Exceptions defined in this microservice are the following:
|Fields                        |Error code|Description?|
|------------------------------|:----:|:-----------|
|GstServerDownException        |001   |Gst server cannot be reached|
|GstNotFoundException          |001   |Given GSTIN Not Found|
|CustomerNotFoundException     |002   |Given customer does not exist in the database|
|BadRequestException           |001   |Invalid user input
|TokenExpiredException         |001   |The token has expired
|BadDatabaseConnectionException|002   |Database connection failed|