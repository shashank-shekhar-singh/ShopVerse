-- V1__create_users.sql
-- Creates the users table matching com.shopverse.userservice.domain.entity.User and BaseEntity

-- Use pgcrypto for gen_random_uuid(); if unavailable, application should provide UUIDs
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  version BIGINT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255),
  phone VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  gender VARCHAR(20) NOT NULL,
  status VARCHAR(20),
  CONSTRAINT users_email_unique UNIQUE (email),
  CONSTRAINT users_phone_unique UNIQUE (phone),
  CONSTRAINT users_gender_check CHECK (gender IN ('MALE','FEMALE','OTHER')),
  CONSTRAINT users_status_check CHECK (status IN ('ACTIVE','BLOCKED','DELETED'))
);

CREATE INDEX IF NOT EXISTS idx_users_created_at ON users (created_at);

