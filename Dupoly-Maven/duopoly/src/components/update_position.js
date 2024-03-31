import React from 'react'
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";
import space from "./locations";

const Roll = () => {
    const [player, setPlayer] = useState(null);

    const loadPlayer = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getPlayerInGame/1/1`);
            setPlayer({
                cur_pos: response.data.currentPosition,
                cur_dir: response.data.currentDirection,
            });
            console.log(`Player is at position ${response.data.currentPosition}.`);
        } catch (error) {
            console.error('Error fetching player:', error);
        }
    };

    const nextPosition = async () => {
        try {
            await axios.post("http://localhost:8080/updateRoll", {
                user_id: "1",
                game_id: "1",
                game_code: "PEPE"
            });

            await axios.post("http://localhost:8080/updatePos", {
                user_id: "1",
                game_id: "1",
                game_code: "PEPE"
            });
            console.log('Updated position.');
            loadPlayer(); // Reload player data after updating position
        } catch (error) {
            console.error('Error updating roll number:', error);
            console.error('Error updating position:', error);
        }

        var roll_button = document.getElementById("roll_button");
        var roll_div = document.getElementById("roll_div");
        var buy_div = document.getElementById("buy");
        roll_button.style.display='none';
        roll_div.style.display='none';
        buy_div.style.display='block';
    };

    useEffect(() => {
        loadPlayer(); // Load player data when the component mounts
    }, []);

    // fix dictionary to style
    const parseStyle = (styleString) => { 
        if (!styleString) return {};
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
                <div className="icon" id="player_icon" style={player && player ? parseStyle(space[player.cur_pos]) : {}}>
                    <div className="eyes">
                    <div className="eye"></div>
                    <div className="eye"></div>
                        </div>
                    </div>
                </div>
            <div className="center" id="roll_button" style={{ display: "none", margin:"auto auto" }}>
                <button onClick={nextPosition}>Roll!</button>
            </div>
            <div className="center" id="buy" style={{ display: "none", margin:"auto auto", flexDirection: "column" }}>
                <h1 style={{marginTop:"10vh"}}>Buy Property?</h1>
                <button>Yes</button>
                <button style={{backgroundColor: "#50514F"}}>No</button>
            </div>
        </>
        )
    };  


export default Roll;