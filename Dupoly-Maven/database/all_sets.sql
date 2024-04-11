CREATE TABLE all_sets (
    set_name varchar(50) UNIQUE NOT NULL,
    PRIMARY KEY (set_name)
);

INSERT INTO all_sets (set_name) VALUES
('New York'), ('Florida'), ('Massachusetts'), ('Michigan'), ('Ohio'), ('Vermont'),
('California'), ('Nevada'), ('Texas'), ('Oregon'), ('Washington'), ('New Mexico'),
('Airports'), ('Utility');