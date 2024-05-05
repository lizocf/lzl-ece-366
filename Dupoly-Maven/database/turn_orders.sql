CREATE TABLE turn_orders (
    id SERIAL PRIMARY KEY,
    game_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (game_id, user_id) REFERENCES player_in_game(game_id, user_id) ON DELETE CASCADE
    -- FOREIGN KEY (user_id) REFERENCES accounts(user_id) ON DELETE CASCADE
--     FOREIGN KEY (user_id) REFERENCES player_in_game(user_id) ON DELETE CASCADE
);

-- HOW TO GET TURN ORDER OF GAME:
-- SELECT player_id
-- FROM turn_orders
-- WHERE game_id = <game_id>
-- ORDER BY id;