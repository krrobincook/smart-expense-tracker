-- Initial database shape. Business rules and migrations will be added later.
CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    category VARCHAR(100) NOT NULL,
    expense_date DATE NOT NULL,
    notes VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS budgets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(100) NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);