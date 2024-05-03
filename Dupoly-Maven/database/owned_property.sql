CREATE TABLE owned_property (
    game_id int NOT NULL,
    user_id int NOT NULL,
    set_name varchar(50) NOT NULL,
    property_name varchar(50) NOT NULL,
    num_hotels int DEFAULT 0,
    upgradeable boolean DEFAULT FALSE,
    tradeable boolean DEFAULT FALSE,
    PRIMARY KEY (game_id, property_name), -- change primary key to game_id, property_name
    FOREIGN KEY (property_name) REFERENCES all_property(property_name),
    FOREIGN KEY (set_name) REFERENCES all_sets(set_name),
    FOREIGN KEY (game_id) REFERENCES game_meta(game_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES accounts(user_id) ON DELETE CASCADE
);
