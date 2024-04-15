CREATE TABLE all_cards (
    card_name varchar(50) UNIQUE NOT NULL,
    PRIMARY KEY (card_name)
);

INSERT INTO all_cards (card_name) VALUES
    ('Bankrupt'), ('Market Crash'), ('Based'), ('f off'), ('Squatters Rights'), ('Jailed'),
    ('No Go'), ('Divorced'), ('Swap'), ('West Coast Best Coast'), ('West Coast Worst Coast'), ('Dust Bowl'),
    ('Triple Skull emoji'), ('We''re So Back'), ('Self Sabotage'), ('What Do You Bring To The Table'),
    ('Rent Stabilized'),('Eviction'),('Winner Winner Chicken Dinner'), ('Force Trade'), ('The Federal Reserve'),
    ('King of the City'), ('California Love'), ('Truth or Consequences'), ('Cringe'),
    ('Get Out of Jail Free'), ('Reverse'), ('Debt Collector'), ('Surprise Party'), ('Run it back'), ('Lottery'),
    ('Vaycay'), ('Housing Lottery'), ('GO TO'), ('Mercy'),('Skull Town'),('FREE AS AIR AND WATER'),('Among US');