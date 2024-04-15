#!/bin/bash

database=duopoly

# # drop all current tables
# psql -h localhost -U postgres -d $database -f database/drop_all.sql


# # create all tables
psql -h localhost -U postgres -d $database -f database/all_property.sql
psql -h localhost -U postgres -d $database -f database/all_sets.sql
psql -h localhost -U postgres -d $database -f database/full_property.sql
psql -h localhost -U postgres -d $database -f database/elo.sql

psql -h localhost -U postgres -d $database -f database/accounts.sql
psql -h localhost -U postgres -d $database -f database/game_meta.sql
psql -h localhost -U postgres -d $database -f database/owned_property.sql
psql -h localhost -U postgres -d $database -f database/player_in_game.sql
psql -h localhost -U postgres -d $database -f database/all_cards.sql

