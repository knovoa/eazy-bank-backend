
-- Customers table definition
CREATE TABLE IF NOT EXISTS CUSTOMERS (
  ID bigint AUTO_INCREMENT PRIMARY KEY,
  EMAIL varchar(100),
  PWD varchar(250),
  ROLE varchar(30)
);

-- Inserts for customers table
INSERT INTO CUSTOMERS (EMAIL, PWD, ROLE) VALUES
('kevin@example.com', '{noop}EazyBytes@12345', 'read'),
('admin@example.com', '{bcrypt}$2a$12$88.f6upbBvy0okEa7OfHFuorV29qeK.sVbB9VQ6J6dWM1bW6Qef8m', 'admin');
