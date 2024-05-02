DROP TABLE game_meta CASCADE;
DROP TABLE owned_property;
DROP TABLE accounts CASCADE;
DROP TABLE player_in_game;
DROP TABLE turn_orders;
-- DROP TABLE all_property CASCADE;
-- DROP TABLE all_sets CASCADE;
-- DROP TABLE elo CASCADE;

ALTER SEQUENCE game_meta_game_id_seq RESTART WITH 1;
ALTER SEQUENCE accounts_user_id_seq RESTART WITH 1;