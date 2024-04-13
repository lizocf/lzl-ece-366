import React from 'react'
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";
import space from "./locations";
import PlayerTable, { handleButtonClick } from "./playertable";

const Roll = () => {
    const [player, setPlayer] = useState(null);
    const [players, setPlayers] = useState(null);
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

            land_div.style.display='block';
            if (!(not_purchaseable.includes(player_response.data.currentPosition)) && (prop_response.data.propertyName == null)) {
                buy_div.style.display='block';
                buttons.style.display='block';
            }  
            
            console.log(`loadProperty: Player has moved to position ${player_response.data.currentPosition}.`);
        } catch (error) {
            console.error('Error fetching property:', error);
        }
    };

    // purchase property

    const purchaseProperty = async () => {
        try {
            await axios.post("http://localhost:8080/gameMove", {
                user_id: "1",           // how do we NOT hardcode?
                game_id: "1",
                game_code: "PEPE",
                space: String(player.cur_pos)
            });

            // console.log(`Player has purchased ${property.property_name}!`);
        } catch (error) {
            console.error('Error fetching player:', error);
        }
    };
    
    const cancelProperty = async () => {
        try {
            const response = await axios.post(`http://localhost:8080/gameMove`, {
                user_id: "1",           // how do we NOT hardcode?
                game_id: "1",
                game_code: "PEPE"
            });

            console.log(`cancelProperty: Player has moved to position ${response.data.currentPosition}.`);
        } catch (error) {
            console.error('Error fetching player:', error);
        }
    };
    
    const loadUpdate = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getPlayerInGame/1/1`);
            setPlayer({
                cur_pos: response.data.currentPosition,
                cur_dir: response.data.currentDirection,
            });
        

            var roll_button = document.getElementById("roll_button");
            var roll_div = document.getElementById("roll_div");
            roll_button.style.display='none';
            roll_div.style.display='none';
    
            // check if already owned through /getOwnedProperty -> change 

            // if (!(not_purchaseable.includes(response.data.currentPosition))) {
            //     buy_div.style.display='block';
            //     // have a post that makes purchaseable boolean true
            // }  

            console.log(`loadUpdate: Player has moved to position ${response.data.currentPosition}.`);
        } catch (error) {
            console.error('Error fetching player:', error);
        }
    };

    const nextPosition = async () => {
        try {
            await axios.post("http://localhost:8080/updateRoll", {
                user_id: "1",           // how do we NOT hardcode?
                game_id: "1",
                game_code: "PEPE"
            });

            await axios.post("http://localhost:8080/updatePos", {
                user_id: "1",
                game_id: "1",
                game_code: "PEPE"
            });
            loadUpdate(); // Reload player data after updating position
            loadProperty();

        } catch (error) {
            console.error('Error updating roll number:', error);
            console.error('Error updating position:', error);
        }      
    };



    useEffect(() => {
        loadPlayer(); // Load player data when the component mounts
        // loadProperty();
    }, []);

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
            <div className="center" id="land" style={{ display: "none", margin:"auto auto", flexDirection: "column" }}>
                <h2 style={{marginTop:"10vh"}}>You landed on</h2>
                <h1> {property && property.property_name}, {property && property.set_name}.</h1>
                <div className="center" id="buy" style={{ display: "none", margin:"13vh auto", flexDirection: "column" }}>
                    <h2 id = "buy_question" style={{marginTop:"1.2vh"}}> Buy Property?</h2>
                    <div className="center" id="buttons" style={{ display: "block", margin:"-2vh -1vh", flexDirection: "column" }}>
                        <button id = "buy_yes" onClick={purchaseProperty}> Yes</button>
                        <button id = "buy_no" onClick={cancelProperty} style={{backgroundColor: "#50514F", marginLeft: "2vh"}}>No</button>
                    </div>
            </div>
            </div>
        </>
        )
    };  

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


export default Roll;