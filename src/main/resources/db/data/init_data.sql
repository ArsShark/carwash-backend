-- Начальные данные для тестирования

-- Услуги
INSERT INTO services (name, description, price, duration_minutes, deleted) VALUES
                                                                               ('Мойка кузова', 'Комплексная мойка кузова с пеной', 500.00, 30, false),
                                                                               ('Мойка салона', 'Влажная уборка салона', 800.00, 45, false),
                                                                               ('Химчистка салона', 'Глубокая химчистка всех поверхностей', 3500.00, 180, false),
                                                                               ('Полировка кузова', 'Восстановительная полировка', 5000.00, 240, false),
                                                                               ('Мойка двигателя', 'Мойка двигателя с консервацией', 1200.00, 60, false);

-- Филиалы
INSERT INTO branches (name, address, phone, deleted) VALUES
                                                         ('Центральный', 'г. Москва, ул. Ленина, 1', '+7 (495) 123-45-67', false),
                                                         ('Северный', 'г. Москва, ул. Северная, 25', '+7 (495) 765-43-21', false);

-- Клиенты
INSERT INTO clients (full_name, phone, car_model, deleted) VALUES
                                                               ('Иванов Иван Иванович', '+7 (999) 123-45-67', 'Toyota Camry', false),
                                                               ('Петров Петр Петрович', '+7 (999) 987-65-43', 'BMW X5', false),
                                                               ('Сидоров Сидор Сидорович', '+7 (999) 555-55-55', 'Mercedes E-Class', false);

-- Записи
INSERT INTO appointments (client_id, service_id, date_time, status, deleted) VALUES
                                                                                 (1, 1, '2024-01-15 10:00:00', 'BOOKED', false),
                                                                                 (2, 3, '2024-01-15 14:00:00', 'BOOKED', false),
                                                                                 (1, 2, '2024-01-16 11:00:00', 'COMPLETED', false);
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO users (username, password, deleted)
VALUES ('admin', '$2a$10$v75TlzXkNkOmUlur/h4Q1eU6kLHpJvuG10.D3Ts.WoyUII4EeXc86', false);
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username='admin'), 2);