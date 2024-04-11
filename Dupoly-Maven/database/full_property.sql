CREATE TABLE full_property (
    space int UNIQUE NOT NULL,
    property_name varchar(50) NOT NULL,
    set_name varchar(50) NULL,
    -- PRIMARY KEY (property_name)
    PRIMARY KEY (space),
    FOREIGN KEY (property_name) REFERENCES all_property(property_name) ON DELETE CASCADE,
    FOREIGN KEY (set_name) REFERENCES all_sets(set_name) ON DELETE CASCADE
);

-- I ONLY MADE FOR PURCHASEABLE PROPERTIES!

INSERT INTO full_property (space, property_name, set_name) VALUES
(29, 'New York City', 'New York'),      -- East $$$ (New York 1)
(27, 'Poughkeepskie', 'New York'),      -- East $$$ (New York 2)

(24, 'Miami', 'Florida'),              -- East $$ 1 (Florida 1)
(23, 'Tampa', 'Florida'),              -- East $$ 1 (Florida 2)
(21, 'Orlando', 'Florida'),            -- East $$ 1 (Florida 3)

(19, 'Boston', 'Massachusetts'),             -- East $$ 2 (Massachusetts 1)
(18, 'Cambridge', 'Massachusetts'),          -- East $$ 2 (Massachusetts 2)
(17, 'Salem', 'Massachusetts'),              -- East $$ 2 (Massachusetts 3)

(14, 'Detroit', 'Michigan'),            -- East $$ 3 (Michigan 1)
(13, 'Flint', 'Michigan'),              -- East $$ 3 (Michigan 2)
(11, 'Rochester', 'Michigan'),          -- East $$ 3 (Michigan 3)

(9, 'Cleveland', 'Ohio'),          -- East $$ 4 (Ohio 1)
(7, 'Cincinnati', 'Ohio'),         -- East $$ 4 (Ohio 2)
(6, 'Beavercreek', 'Ohio'),        -- East $$ 4 (Ohio 3)

(3, 'Stowe', 'Vermont'),              -- East $ (Vermont 1)
(1, 'Burlington', 'Vermont'),         -- East $ (Vermont 2)

-- EAST COAST BEST COAST RAHHHH

(31, 'Los Angeles', 'California'),        -- West $$$ (Cali 1)
(33, 'San Francisco', 'California'),      -- West $$$ (Cali 2)

(36, 'Las Vegas', 'Nevada'),          -- West $$ 1 (Nevada 1)
(37, 'Henderson', 'Nevada'),          -- West $$ 1 (Nevada 2)
(39, 'Boulder City', 'Nevada'),       -- West $$ 1 (Nevada 3)

(41, 'San Antonio', 'Texas'),        -- West $$ 2 (Texas 1)
(42, 'Dallas', 'Texas'),             -- West $$ 2 (Texas 2)
(43, 'Houston', 'Texas'),            -- West $$ 2 (Texas 3)

(46, 'Portland', 'Oregon'),           -- West $$ 3 (Oregon 1)
(47, 'Eugene', 'Oregon'),             -- West $$ 3 (Oregon 2)
(49, 'Oregon City', 'Oregon'),        -- West $$ 3 (Oregon 3)

(51, 'Seattle', 'Washington'),            -- West $$ 4 (Washington 1)
(53, 'Olympia', 'Washington'),            -- West $$ 4 (Washington 2)
(54, 'Vancouver', 'Washington'),          -- West $$ 4 (Washington 3)

(57, 'Albuquerque', 'New Mexico'),        -- West $ (New Mexico 1)
(59, 'Santa Fe', 'New Mexico'),            -- West $ (New Mexico 2)

-- utility
(12, 'Electric Company', 'Utility'),
(48, 'Water Company', 'Utility'),

-- airports
(25, 'JFK', 'Airports'), -- bottom east (jfk)
(15, 'DTW', 'Airports'), -- east        (Detroit Metropolitan Wayne County Airport)
(5, 'CLE', 'Airports'), -- top east    (Cleveland Hopkins International Airport)
(35, 'LAX', 'Airports'), -- bottom west (Los Angeles International Airport)
(45, 'DFW', 'Airports'), -- west        (Dallas Fort Worth International Airport)
(55, 'DCA', 'Airports') -- top west    (Ronald Reagan Washington National Airport)

-- tax
(4, 'Tax_4', NULL),
(26,'Tax_26', NULL),
(34,'Tax_36', NULL),
(56,'Tax_56', NULL).

-- skulls
(8, 'SKULL_8', NULL),
(28,'SKULL_28', NULL),
(38, 'SKULL_38', NULL),
(44, 'SKULL_44', NULL),
(58, 'SKULL_58', NULL)

-- surprise
(2, 'SURPRISE_2', NULL),
(16, 'SURPRISE_16', NULL),
(22, 'SURPRISE_22', NULL),
(32, 'SURPRISE_32', NULL),
(52, 'SURPRISE_52', NULL)

-- 10s
(0, 'Go', NULL),
(10, 'Debt Space', NULL),
(20, 'Free Parking', NULL),
(30, 'Reverse Space', NULL),
(40, 'Eviction Space', NULL),
(50, 'Jail', NULL)

;
