CREATE TABLE IF NOT EXISTS onboard.gst_profile (
    profile_id UUID NOT NUll,
    user_id UUID NOT NULL UNIQUE,
    gst_details VARCHAR(1000) NOT NULL,
    PRIMARY KEY (profile_id),
    FOREIGN KEY (user_id)
	REFERENCES onboard.customer(user_id)
);