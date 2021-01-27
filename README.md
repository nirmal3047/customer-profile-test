# customer-profile-service

## Overview
This API is called after the user has signed up on the EzyReach platform and successfully logged in. The purpose of this API is to take user input (GSTIN, PAN, Udyog Aadhaar, Turnover), fetch details about the user from GST server using GSTIN and create customer's profile using those details and persist the customer profile in PostgreSQL database.


### Entities

1. UserInput
|Fields      |Type  |Mandatory?|Description?|
|------------|:----:|:--------:|:-----------|
|gstin       |String|Yes       |15 characters alphanumeric|
|pan         |String|Yes       |10 characters alphanumeric|
|udyogAadhaar|String|Yes       |12 characters numeric|
|turnover    |float |Yes       |Must be positive|

2. Customer
|Fields      |Type  |Mandatory?|Description?|
|------------|:----:|:--------:|:-----------|
|customerId  |String|Yes       |UUID|
|gstin       |String|Yes       |15 characters alphanumeric|
|pan         |String|Yes       |10 characters alphanumeric|
|udyogAadhaar|String|Yes       |12 characters numeric|
|turnover    |float |Yes       |Must be positive|
|address     |json  ||         |
|name        |String||         ||

![alt text](https://github.com/EzReach/customer-profile-service/blob/main/Diagrams/ER-customer-profile.PNG "Entities for customer/profile")


### Project Setup
This is a multi build Gradle project. The specifications are as follows:
* Gradle version: 6.7
* Java version: 1.8
* Spring Boot version: 2.3.2.RELEASE

Spring dependencies:
* spring-


### Running project in local
Clone the repository and run the following command at the root directory
```
./gradlew customer-profile-service:bootRun
```

Go to **http://localhost:9288/v1/customer/greeting** and you should see **welcome** on the screen



### API specs
Please visit [customer/profile API specs](https://github.com/EzReach/customer-profile-service/blob/main/customer-profile-api.yaml) for detailed API specification

#### Request payload example
```
{
  "gstin": "05ABNTY3290P8ZA",
  "pan": "CPAA1234A",
  "turnover": 25000000,
  "udyogAadhaar": "423001224256"
}
```


#### Responses
* 201 Created
* 400 Bad Request
* 401 Unauthorized
* 403 Forbidden
* 404 Not Found