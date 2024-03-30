import React from 'react'
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";
import space from "./locations";

const Roll = () => {
    const [player, setPlayer] = useState(null);

    const nextPosition = async () => {
        if (!player) return; // No player data available
        const roll = 1; // Change roll logic if needed
        if (player.cur_dir == "right") {
            var next_pos = (player.cur_pos + roll) % 60;
        } 
        else if (player.cur_dir == "left") {
            var next_pos = (player.cur_pos - roll) % 60;
        } 

        console.log(`Player at position ${player.cur_pos} facing ${player.cur_dir}. Moving to position ${next_pos}.`);

       
        try {
            await axios.post("http://localhost:8080/updatePos", {
                user_id: "1",
                game_id: "1",
                move_to: String(next_pos)
            });
            // Assuming successful update, update the player state locally
            setPlayer(prevPlayer => ({
                ...prevPlayer,
                cur_pos: next_pos
            }));
        } catch (error) {
            console.error('Error updating position:', error);
        }
    };

    useEffect(() => {
        const loadPlayer = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/getPlayerInGame/1/1`);
                setPlayer({
                    cur_pos: response.data.currentPosition,
                    cur_dir: response.data.currentDirection
                });
            } catch (error) {
                console.error('Error fetching player:', error);
            }
        };
        loadPlayer();
    }, []);

    const parseStyle = (styleString) => {
        if (!styleString) return {}; // Return empty object if styleString is undefined or falsy
    
        const styleArray = styleString.split(';').map(pair => pair.split(':').map(s => s.trim()));
        const styleObject = {};
        styleArray.forEach(pair => {
            if (pair.length === 2) {
                styleObject[pair[0]] = pair[1];
            }
        });
        return styleObject;
    };


    return (
        <>
            <div className="container_board">
                <div className="icon" id="player_icon" style={player ? parseStyle(space[player.cur_pos]) : {}}>
                    <div className="eyes">
                    <div className="eye"></div>
                    <div className="eye"></div>
                        </div>
                    </div>
                </div>
            <div className="center" id="roll_button" style={{ display: "none", margin:"auto auto" }}>
                <button onClick={nextPosition}>Roll!</button>
            </div>
        </>
        )
    };  


export default Roll;