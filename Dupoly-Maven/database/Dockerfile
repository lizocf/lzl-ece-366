FROM postgres

COPY 0_setup.sql /docker-entrypoint-initdb.d/0_setup.sql
COPY 1_all_property.sql /docker-entrypoint-initdb.d/1_all_property.sql
COPY 2_all_sets.sql /docker-entrypoint-initdb.d/2_all_sets.sql
COPY 3_full_property.sql /docker-entrypoint-initdb.d/3_full_property.sql
COPY 4_elo.sql /docker-entrypoint-initdb.d/4_elo.sql
COPY 5_accounts.sql /docker-entrypoint-initdb.d/5_accounts.sql
COPY 6_all_cards.sql /docker-entrypoint-initdb.d/6_all_cards.sql
COPY 7_game_meta.sql /docker-entrypoint-initdb.d/7_game_meta.sql
COPY 8_owned_property.sql /docker-entrypoint-initdb.d/8_owned_property.sql
COPY 90_player_in_game.sql /docker-entrypoint-initdb.d/90_player_in_game.sql
COPY 91_turn_orders.sql /docker-entrypoint-initdb.d/91_turn_orders.sql