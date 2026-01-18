-- V2__create_addresses.sql
-- Creates the addresses table matching com.shopverse.userservice.domain.entity.Address and BaseEntity

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS addresses (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  version BIGINT,
  user_id UUID NOT NULL,
  type VARCHAR(20),
  is_default BOOLEAN NOT NULL,
  line1 VARCHAR(255),
  city VARCHAR(255),
  state VARCHAR(255),
  country VARCHAR(255),
  pincode VARCHAR(255),
  CONSTRAINT fk_addresses_customer FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT addresses_type_check CHECK (type IN ('SHIPPING','BILLING'))
);

CREATE INDEX IF NOT EXISTS idx_addresses_user_id ON addresses (user_id);

-- Ensure at most one default address per customer
CREATE UNIQUE INDEX IF NOT EXISTS ux_addresses_customer_default ON addresses (user_id) WHERE is_default;

