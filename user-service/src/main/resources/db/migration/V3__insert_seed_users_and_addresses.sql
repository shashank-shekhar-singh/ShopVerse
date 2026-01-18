-- V3__insert_seed_users_and_addresses.sql
-- Inserts sample users and addresses for development/testing.

-- We insert users and capture their generated UUIDs with a CTE, then insert
-- addresses that reference those UUIDs. This keeps the migration atomic and
-- ensures FK consistency.

WITH new_users AS (
  INSERT INTO users (first_name, last_name, phone, email, gender, status, version)
  VALUES
    ('Alice', 'Smith', '555-0100', 'alice@example.com', 'FEMALE', 'ACTIVE', 0),
    ('Bob', 'Jones', '555-0101', 'bob@example.com', 'MALE', 'ACTIVE', 0),
    ('Charlie', NULL, '555-0102', 'charlie@example.com', 'OTHER', 'ACTIVE', 0)
  RETURNING id, email
),
addr_a AS (
  INSERT INTO addresses (user_id, type, is_default, line1, city, state, country, pincode, version)
  SELECT id, 'SHIPPING', true,  '123 Main St',  'Metropolis', 'State', 'Country', '10001', 0
  FROM new_users WHERE email = 'alice@example.com'
),
addr_a2 AS (
  INSERT INTO addresses (user_id, type, is_default, line1, city, state, country, pincode, version)
  SELECT id, 'BILLING', false,  'Suite 2, 123 Main St',  'Metropolis', 'State', 'Country', '10002', 0
  FROM new_users WHERE email = 'alice@example.com'
),
addr_b AS (
  INSERT INTO addresses (user_id, type, is_default, line1, city, state, country, pincode, version)
  SELECT id, 'BILLING', true,  '456 Oak Ave',  'Gotham', 'State', 'Country', '20002', 0
  FROM new_users WHERE email = 'bob@example.com'
),
addr_c AS (
  INSERT INTO addresses (user_id, type, is_default, line1, city, state, country, pincode, version)
  SELECT id, 'SHIPPING', true,  '789 Pine Rd',  'Star City', 'State', 'Country', '30003', 0
  FROM new_users WHERE email = 'charlie@example.com'
)
SELECT count(*) FROM new_users;

