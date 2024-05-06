\c duopoly

CREATE TABLE game_meta (
    game_id SERIAL UNIQUE,
    game_code varchar(6) UNIQUE DEFAULT NULL, -- how to make random char sequence unique
    joinable boolean DEFAULT TRUE,
    which_player_turn int DEFAULT NULL, -- foreign kkey to user_id
    date_time_start varchar(50) DEFAULT now(),
    num_players int DEFAULT 0,
    num_turns int DEFAULT 0, -- ????
    debt_pot int DEFAULT 0,
    roll_number int DEFAULT 0,
    purchaseable boolean DEFAULT TRUE,
    clicked boolean DEFAULT FALSE,
    recent_card varchar(50) DEFAULT NULL,
    host int DEFAULT NULL, -- foreign key to user_id
    last_player int DEFAULT NULL, -- foreign key to user_id
    PRIMARY KEY (game_id),
    FOREIGN KEY (which_player_turn) REFERENCES accounts(user_id),
    FOREIGN KEY (recent_card) REFERENCES all_cards(card_name),
    FOREIGN KEY (host) REFERENCES accounts(user_id),
    FOREIGN KEY (last_player) REFERENCES accounts(user_id)
);