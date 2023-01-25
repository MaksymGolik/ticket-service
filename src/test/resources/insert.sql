INSERT INTO bus_routes (id, arrival, available_tickets, departure, departure_time, ticket_price) VALUES
(1001,'Одеса', 75, 'Ужгород', '2022-01-25 18:00', 749.99),
(1002,'Львів', 50, 'Дніпро', '2022-01-27 12:00', 550.55);

INSERT INTO tickets(id, first_name, last_name, middle_name, bus_route_id) VALUES
(1001, 'Іван', 'Іванов', 'Іванович', 1001),
(1002, 'Іван', 'Іванов', 'Іванович', 1002)
;