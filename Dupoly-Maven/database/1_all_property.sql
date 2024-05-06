\c duopoly

CREATE TABLE all_property (
    property_name varchar(50) UNIQUE NOT NULL,
    PRIMARY KEY (property_name)
);

-- add tax
-- lightning
-- airports

INSERT INTO all_property (property_name) VALUES
('New York City'),      -- East $$$ (New York 1)
('Poughkeepskie'),      -- East $$$ (New York 2)

('Miami'),              -- East $$ 1 (Florida 1)
('Tampa'),              -- East $$ 1 (Florida 2)
('Orlando'),            -- East $$ 1 (Florida 3)

('Boston'),             -- East $$ 2 (Massachusetts 1)
('Cambridge'),          -- East $$ 2 (Massachusetts 2)
('Salem'),              -- East $$ 2 (Massachusetts 3)

('Detroit'),            -- East $$ 3 (Michigan 1)
('Flint'),              -- East $$ 3 (Michigan 2)
('Rochester'),          -- East $$ 3 (Michigan 3)

('Cleveland'),          -- East $$ 4 (Ohio 1)
('Cincinnati'),         -- East $$ 4 (Ohio 2)
('Beavercreek'),        -- East $$ 4 (Ohio 3)

('Stowe'),              -- East $ (Vermont 1)
('Burlington'),         -- East $ (Vermont 2)

-- EAST COAST BEST COAST RAHHHH

('Los Angeles'),        -- West $$$ (Cali 1)
('San Francisco'),      -- West $$$ (Cali 2)

('Las Vegas'),          -- West $$ 1 (Nevada 1)
('Henderson'),          -- West $$ 1 (Nevada 2)
('Boulder City'),       -- West $$ 1 (Nevada 3)

('San Antonio'),        -- West $$ 2 (Texas 1)
('Dallas'),             -- West $$ 2 (Texas 2)
('Houston'),            -- West $$ 2 (Texas 3)

('Portland'),           -- West $$ 3 (Oregon 1)
('Eugene'),             -- West $$ 3 (Oregon 2)
('Oregon City'),        -- West $$ 3 (Oregon 3)

('Seattle'),            -- West $$ 4 (Washington 1)
('Olympia'),            -- West $$ 4 (Washington 2)
('Vancouver'),          -- West $$ 4 (Washington 3)

('Albuquerque'),        -- West $ (New Mexico 1)
('Santa Fe'),            -- West $ (New Mexico 2)

-- utility
('Electric Company'),
('Water Company'),

-- airports
('JFK'), -- bottom east (jfk)
('DTW'), -- east        (Detroit Metropolitan Wayne County Airport)
('CLE'), -- top east    (Cleveland Hopkins International Airport)
('LAX'), -- bottom west (Los Angeles International Airport)
('DFW'), -- west        (Dallas Fort Worth International Airport)
('DCA'), -- top west    (Ronald Reagan Washington National Airport)

-- tax
('Tax_4'),
('Tax_26'),
('Tax_36'),
('Tax_56'),

-- skulls
('SKULL_8'),
('SKULL_28'),
('SKULL_38'),
('SKULL_44'),
('SKULL_58'),

-- surprise
('SURPRISE_2'),
('SURPRISE_16'),
('SURPRISE_22'),
('SURPRISE_32'),
('SURPRISE_52'),

-- 10s
('Go'),
('Debt Space'),
('Free Parking'),
('Reverse Space'),
('Eviction Space'),
('Jail')

;