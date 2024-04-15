import React from 'react'
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";
import "./update_position";


const LeftTables = () => {
    const [player, setPlayer] = useState(null);
    const [property, setProperty] = useState(null);
    const [ownedProperty, setOwnedProperty] = useState(null);

    let not_purchaseable = [0,2,4,8,10,16,20,22,26,28,30,32,34,38,40,44,50,52,56,58];

    const loadPlayer = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getPlayerInGame/1/1`);
            setPlayer({
                cur_pos: response.data.currentPosition,
                cur_dir: response.data.currentDirection,
            });
            // setPlayer(response.data.filter(player => player !== null).map(player => ({ cur_pos: player.currentPosition, cur_dir: player.currentDirection })));
            console.log(`loadPlayer: Player is currently at position ${response.data.currentPosition}.`);
        } catch (error) {
            console.error('Error fetching player:', error);
        }
    };

    // get property name from space
    const loadProperty = async () => {
        try {
            const player_response = await axios.get(`http://localhost:8080/getPlayerInGame/1/1`); // how do we make this a funciton??
            setPlayer({
                cur_pos: player_response.data.currentPosition,
                cur_dir: player_response.data.currentDirection,
                game_id: player_response.data.gameId,
                user_id: player_response.data.userId,
                
            });
            
            const name_response = await axios.get(`http://localhost:8080/getNames/${player_response.data.gameId}/${player_response.data.userId}`);
            setProperty({
                set_name: name_response.data.setName,
                property_name: name_response.data.propertyName,
            });
            console.log(`loadProperty: Player has landed on ${name_response.data.propertyName} of the ${name_response.data.setName} set.`);

            // check if already owned through /getOwnedProperty
            const prop_response = await axios.get(`http://localhost:8080/getOwnedProperty/${player_response.data.gameId}/${name_response.data.propertyName}`);
            setOwnedProperty({
                user_id: prop_response.data.userId,
                property_name: prop_response.data.propertyName
            });

            var land_div = document.getElementById("land");
            var buy_div = document.getElementById("buy");
            var buttons = document.getElementById("buttons");
            const end_div = document.getElementById("end");
            land_div.style.display='block';

            if (!(not_purchaseable.includes(player_response.data.currentPosition) && (prop_response.data.propertyName == null))) {
                buy_div.style.display='block';
                buttons.style.display='block';
            } 
            else {
                await axios.post("http://localhost:8080/gameMove", {
                    user_id: "1",           // how do we NOT hardcode?
                    game_id: "1",
                    game_code: "PEPE",
                    space: String(player_response.data.currentPosition)
                })
                end_div.style.display='block'; 
                land_div.style.display='block';
            }
            // else if () {
            //     buy_div.style.display='block';
            //     buttons.style.display='block';

            //     console.log('This property is already owned by User', prop_response.data.userId);
            // }  
            
            console.log(`loadProperty: Player has moved to position ${player_response.data.currentPosition}.`);
        } catch (error) {
            console.error('Error fetching property:', error);
        }
    };
    



}
export default LeftTables;