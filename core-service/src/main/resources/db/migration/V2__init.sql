CREATE TABLE IF NOT EXISTS categories (
    id bigserial,
    title VARCHAR(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp ,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS products (
    id bigserial,
    category_id bigserial,
    title VARCHAR(255),
    cost int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp ,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders (
    id              bigserial primary key,
    username        varchar(255) not null,
    total_price     int not null,
    address         varchar(255),
    phone           varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE IF NOT EXISTS order_items (
    id                      bigserial primary key,
    product_id              bigint not null references products (id),
    order_id                bigint not null references orders (id),
    quantity                int not null,
    price_per_product       int not null,
    price                   int not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

INSERT INTO categories (title) VALUES
('meat'),
('fish'),
('bird'),
('vegetables'),
('fruit'),
('soft_drink'),
('alcoholic_drink');

INSERT INTO products (title, cost, category_id) VALUES
('bread', 43, 1),
('meat', 413, 1),
('milk', 66, 6),
('chicken', 166, 3),
('ham', 278, 1),
('liver', 200, 1),
('bacon', 366, 1),
('sausage', 206, 1),
('fish', 300, 2),
('beans', 156, 4),
('beet', 83, 4),
('broccoli', 217, 4),
('carrot', 46, 4),
('cucumber', 99, 4),
('onion', 45, 4),
('potato', 53, 4),
('tomato', 199, 4),
('blueberry', 300, 5),
('grape', 213, 5),
('vodka', 800, 7),
('lemon ', 59, 5);