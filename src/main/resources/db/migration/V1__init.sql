CREATE TABLE IF NOT EXISTS products (
    id bigserial,
    title VARCHAR(255),
    cost int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp ,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE IF NOT EXISTS roles (
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE IF NOT EXISTS users_roles (
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS orders (
    id              bigserial primary key,
    user_id         bigint not null references users (id),
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

INSERT INTO products (title, cost) VALUES
('bread', 43),
('meat', 413),
('milk', 66),
('chicken', 166),
('ham', 278),
('liver', 200),
('bacon', 366),
('sausage', 206),
('fish', 300),
('beans', 156),
('beet', 83),
('broccoli', 217),
('carrot', 46),
('cucumber', 99),
('onion', 45),
('potato', 53),
('tomato', 199),
('blueberry', 300),
('grape ', 213),
('lemon ', 59);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_MODER'),
       ('ROLE_ADMIN');

INSERT INTO users (username, password, email)
VALUES ('user', '$2a$12$.sFNWKxkJd1pY5//LTbFNuOwM21ZcEg/pcyaazKU10NlhC1OMcl0m', 'user@gmail.com'),
       ('moder', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'moder@gmail.com'),
       ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO orders(user_id, total_price, address, phone) VALUES (1, 86, 'BakerStreet', '123456789');
INSERT INTO order_items(product_id, order_id, quantity, price_per_product, price) VALUES (1, 1, 2, 43, 86);