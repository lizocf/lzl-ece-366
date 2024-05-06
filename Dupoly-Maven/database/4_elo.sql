\c duopoly

CREATE TABLE elo (
    rank varchar(50) UNIQUE NOT NULL,
    PRIMARY KEY (rank)
);

INSERT INTO elo (rank) VALUES 
('Grandmaster'), ('Master'), ('Diamond'), ('Platinum'), 
('Gold'), ('Silver'), ('Bronze'), ('Iron');
