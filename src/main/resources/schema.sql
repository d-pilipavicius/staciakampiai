CREATE TABLE "user" (
  id UUID PRIMARY KEY,
  full_name VARCHAR(255) NOT NULL,
  phone_number VARCHAR(31) NOT NULL,
  email_address VARCHAR(255) NOT NULL,
  business_id UUID,
  role VARCHAR(30) NOT NULL
);

CREATE TABLE "business" (
  id UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  owner_id UUID NOT NULL,
  address VARCHAR(255) NOT NULL,
  phone_number VARCHAR(31) NOT NULL,
  email_address VARCHAR(255) NOT NULL,
  CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES "user"(id)
);

ALTER TABLE "user"
ADD CONSTRAINT fk_business
FOREIGN KEY (business_id)
REFERENCES "business"(id);