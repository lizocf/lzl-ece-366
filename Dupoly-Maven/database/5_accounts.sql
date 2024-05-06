\c duopoly

CREATE TABLE accounts (
    user_id SERIAL UNIQUE,
    user_name VARCHAR(10) NOT NULL UNIQUE,
    user_pw VARCHAR(40) DEFAULT NULL,
    token VARCHAR(10) DEFAULT NULL,
    num_wins INT DEFAULT 0,
    num_losses INT DEFAULT 0,
    elo_rating varchar(20) DEFAULT ('Iron'),
    duo_points INT DEFAULT 0,
    PRIMARY KEY (user_id),
    FOREIGN KEY (elo_rating) REFERENCES elo(rank)
);

-- password