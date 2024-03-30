-- players make username
INSERT INTO accounts(user_name) VALUES ('spiceless'), ('mudkip'), ('esaya');

-- correct error once duplicate username found
-- default iron elo, 0 wins/loss/DP

-- host creates game
INSERT INTO game_meta(game_code)
VALUES ('PEPE'); -- random four letter sequence

-- game_code should be unique tbh?

-- three players join game
-- INSERT INTO player_in_game(game_id, user_id)
-- VALUES (1, 1), -- spiceless
--        (1, 2), -- mudkip 
--        (1, 3); -- esaya
INSERT INTO player_in_game(game_id, user_id, user_name)
SELECT 
    1 AS game_id, -- Replace 1 with your actual game_id
    user_id,
    user_name
FROM 
    accounts
WHERE 
    user_id IN (1, 2, 3);

-- duplicate (game_id, user_id) rows will not be added

-- host starts game
UPDATE game_meta 
SET joinable = FALSE, 
    num_players = 3,
    num_turns = 1  -- automate this
WHERE game_id = 1; -- automate this

-- BEGIN GAME

-- spice's turn
UPDATE game_meta 
SET which_player_turn = 1 -- aka spiceless 
WHERE game_id = 1; -- automate this

-- go right and land 10th space
UPDATE player_in_game
SET current_direction = 'RIGHT',
    current_position = 11 -- go space is 0
WHERE game_id = 1 AND user_id = 1; -- game_id part overkill bc users arent allowed in > 1 game, safer ig

-- debt space is passed!
UPDATE game_meta
SET debt_pot = 50
WHERE game_id = 1;

-- buys property
INSERT INTO owned_property(game_id, user_id, set_name, property_name)
VALUES (1,1,'Michigan', 'Rochester');

UPDATE player_in_game
SET cash = 1940     -- rochester costs 60
WHERE game_id = 1 AND user_id = 1;


-- mudkip's turn
UPDATE game_meta 
SET which_player_turn = 2 -- aka mudkip 
WHERE game_id = 1; 

UPDATE player_in_game
SET current_direction = 'RIGHT',
    current_position = 8 -- skull card!
WHERE game_id = 1 AND user_id = 2;

-- skull card pulled: bankrupt L
UPDATE player_in_game
SET cash = 0,

-- mudkip loses
    dead = TRUE
    -- now in spectator mode
    -- should be used to skip turn
    -- remove this player from this table when they quit/exit the game
WHERE game_id = 1 AND user_id = 2;

-- esaya's turn
UPDATE game_meta 
SET which_player_turn = 3-- aka esaya 
WHERE game_id = 1; 

UPDATE player_in_game
SET current_direction = 'LEFT',
    current_position = 52   -- going counter clockwise now
WHERE game_id = 1 AND user_id = 3;

-- suprise card pulled: Cali Crib: gain all of Cali

INSERT INTO owned_property(game_id, user_id, set_name, property_name)
VALUES (1,3,'California', 'Los Angeles'),
       (1,3,'California', 'San Francisco');

-- buy hotel for SF
UPDATE player_in_game
SET cash = 1920 -- SF hotel cost = $80
WHERE game_id = 1 AND user_id = 3;

UPDATE owned_property
SET num_hotels = 1
WHERE game_id = 1 AND user_id = 3 AND property_name = 'San Francisco';

-- need a statement to restrict user from
-- upgrading before other hotels are bought, idk how tho

-- buy hotel for LA
UPDATE player_in_game
SET cash = 1820 -- LA hotel cost = $100
WHERE game_id = 1 AND user_id = 3;

UPDATE owned_property
SET num_hotels = 1
WHERE game_id = 1 AND user_id = 3 AND property_name = 'Los Angeles';

-- new round (2)
UPDATE game_meta
SET num_turns = 2
WHERE game_id = 1;

-- spice's turn
UPDATE game_meta 
SET which_player_turn = 1 
WHERE game_id = 1; 

UPDATE player_in_game
SET current_position = 15 -- rolled a 4, land on airport
WHERE game_id = 1 AND user_id = 1; -- game_id part overkill bc users arent allowed in > 1 game, safer ig

-- buy airport
UPDATE player_in_game
SET cash = 1840 -- airport = $100
WHERE game_id = 1 AND user_id = 1;

INSERT INTO owned_property(game_id, user_id, set_name, property_name)
VALUES (1,1,'Airports', 'DTW');

-- esaya's turn
UPDATE game_meta 
SET which_player_turn = 3 
WHERE game_id = 1; 

UPDATE player_in_game
SET current_position = 40 -- rolled 12
WHERE game_id = 1 AND user_id = 3;

-- eviction space: lose a hotel
UPDATE owned_property
SET num_hotels = 0
WHERE game_id = 1 AND user_id = 3 AND property_name = 'Los Angeles';

-- esaya rolled a double! gain extra turn :D
UPDATE player_in_game
SET current_position = 30 -- rolled 10, reverse space
WHERE game_id = 1 AND user_id = 3;

-- lands on reverse space
UPDATE player_in_game
SET current_direction = 'RIGHT'
WHERE game_id = 1 AND user_id = 3;

-- new round (3)
UPDATE game_meta
SET num_turns = 3
WHERE game_id = 1;

-- spice's turn
UPDATE game_meta 
SET which_player_turn = 1 
WHERE game_id = 1; 

UPDATE player_in_game
SET current_position = 18 -- rolled 3, landed on skull
WHERE game_id = 1 AND user_id = 1;

-- skull card: JAIL :(
UPDATE player_in_game
SET current_position = 50,
    jail = TRUE -- added jail bool, be used to skip their turn
WHERE game_id = 1 AND user_id = 1;

-- esaya's turn
UPDATE game_meta 
SET which_player_turn = 3 
WHERE game_id = 1; 

UPDATE player_in_game
SET current_position = 32 -- going clockwise! rolled 2
WHERE game_id = 1 AND user_id = 3;

-- surprise card: debt collector
UPDATE game_meta
SET debt_pot = 0
WHERE game_id = 1;

UPDATE player_in_game
SET cash = 1870 -- +$50 from debt pot
WHERE game_id = 1 AND user_id = 3;

-- new round (4)
UPDATE game_meta
SET num_turns = 4
WHERE game_id = 1;

-- spice's turn, but must roll double to get out of jail.
UPDATE game_meta
SET which_player_turn = 1
WHERE game_id = 1;

-- pretend spice rolled a double!
UPDATE player_in_game
SET jail = FALSE, -- added jail bool, be used to skip their turn
    current_position = 52 -- going clockwise! rolled 2
WHERE game_id = 1 AND user_id = 1;

-- surprise card landed: go to LA!
UPDATE player_in_game
SET current_position = 31 -- going clockwise! rolled 2
WHERE game_id = 1 AND user_id = 1;


-- esaya owns this property, spice must pay him $500
UPDATE player_in_game
SET cash = 2370 -- +$500 from spice
WHERE game_id = 1 AND user_id = 3;

UPDATE player_in_game
SET cash = 1340 --  -$500 
WHERE game_id = 1 AND user_id = 1;

-- mudkip exits game
DELETE FROM player_in_game WHERE game_id = 1 and user_id = 2;

UPDATE game_meta
SET num_players = 2
WHERE game_id = 1;

-- mudkip gains 10 dp and num_losses = 1!
UPDATE accounts
SET num_losses = 1,
    duo_points = 10
WHERE user_id = 2;

-- esaya's turn
UPDATE game_meta 
SET which_player_turn = 3 
WHERE game_id = 1; 

UPDATE player_in_game
SET current_position = 44 -- rolled 12 again! on skull L
WHERE game_id = 1 AND user_id = 3;

-- skull card: WEST COAST WORST COAST
DELETE FROM owned_property
WHERE game_id = 1 and user_id = 3 and set_name = 'California';


-- end game
-- DELETE FROM "game_meta" WHERE "game_id" = 1;
-- delete on cascade !! (player_in_game rows get deleted too)

-- reset game_id and user_id: 
-- ALTER SEQUENCE game_meta_game_id_seq RESTART WITH 1;
-- ALTER SEQUENCE accounts_user_id_seq RESTART WITH 1;