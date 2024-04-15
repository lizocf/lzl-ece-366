#!/bin/bash

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"user_name": "Clarence"}' \
  "http://localhost:8080/createNewAccount"

# create Jeffery account
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"user_name": "Jeffery"}' \
  "http://localhost:8080/createNewAccount"

# create new game with game code AS123
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"game_code": "AS123"}' \
  "http://localhost:8080/createNewGame"

# add player with user_id 1 into PlayerInGame
curl --header "Content-Type: application/json" \
  --request POST \
  --data '\\n{
    "user_id": "1",
    "game_id": "1"
  }\\n' \
  "http://localhost:8080/createPlayerInGame"

# add player with user_id 2 into PlayerInGame
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{
    "user_id": "2",
    "game_id": "1"
  }' \
  "http://localhost:8080/createPlayerInGame"

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{
    "game_id": "1",
    "user_id": "1",
    "direction": "left"
    }' \
    "http://localhost:8080/updateDir"

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{
    "user_id": "1",
    "game_id": "1"
  }' \
  "http://localhost:8080/createPlayerInGame"

