import React from 'react'
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";
import propData from "./locations";
import PlayerTable, { handleButtonClick } from "./playertable";
import Tiles from "./tiles";

const Roll = ({gameCode, userId, gameId}) => {
    const [player, setPlayer] = useState(null);
    const [game, setGame] = useState(null);
    const [players, setPlayers] = useState(null);
    const [property, setProperty] = useState(null);
    const [ownedProperty, setOwnedProperty] = useState(null);

    console.log("(Roll) Game Code: " + gameCode);

    let not_purchaseable = [0,2,4,8,10,16,20,22,26,28,30,32,34,38,40,44,50,52,56,58];
    let skullsurprise = [2,8,16,22,28,32,38,44,52,58];

    
    const loadAllPlayers = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/${gameId}`);
            setPlayers(response.data);
            console.log("Loaded players: ", response.data);
        } catch (error) {
            console.error('Error fetching players:', error);
        }
    };

    useEffect(() => {
        loadAllPlayers();  // Load all player data when the component mounts
    }, [gameId]);

    const loadPlayer = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getPlayerInGame/${gameId}/${userId}`);
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
            const player_response = await axios.get(`http://localhost:8080/getPlayerInGame/${gameId}/${userId}`); // how do we make this a funciton??
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
            var end_div = document.getElementById("end");
            land_div.style.display='block';

            // console.log(prop_response.data.propertyName);
            if (!(not_purchaseable.includes(player_response.data.currentPosition)) && (prop_response.data.propertyName === null)) {
                buy_div.style.display='block';
                buttons.style.display='block';
            } 
            else {
                console.log(prop_response.data.propertyName);
                end_div.style.display='block'; 
                land_div.style.display='block';
                document.getElementById("special_prop").style.display='none';
                buy_div.style.display='none';
                buttons.style.display='none';

                await axios.post("http://localhost:8080/gameMove", {
                    user_id : String(userId),
                    game_id : String(gameId),
                    game_code: String(gameCode),
                    space: String(player_response.data.currentPosition)
                })
                
                const card_response = await axios.get(`http://localhost:8080/getGameInfo/${gameCode}`);
                setGame({
                    recent_card: card_response.data.recent_card
                })
                if (skullsurprise.includes(player_response.data.currentPosition)) {
                    console.log(card_response.data.recent_card);

                    document.getElementById("special_prop").innerHTML = card_response.data.recent_card;
                    document.getElementById("special_prop").style.display='block';
                    document.getElementById("land_prop").style.display='none';
                    end_div.style.display='block'; 
                } else {  
                    document.getElementById("special_prop").style.display='none';
                    document.getElementById("land_prop").style.display='block';
                }
            }
            console.log(`loadProperty: Player has moved to position ${player_response.data.currentPosition}.`);
        } catch (error) {
            console.error('Error fetching property:', error);
        }
    };

    // purchase property

    const purchaseProperty = async () => {
        try {
            await axios.post("http://localhost:8080/updatePurchaseable", {
                game_code: String(gameCode),
                purchase: "true"
            });

            await axios.post("http://localhost:8080/gameMove", {
                user_id : String(userId),
                game_id : String(gameId),
                game_code: String(gameCode),
                space: String(player.cur_pos)
            });

            const name_response = await axios.get(`http://localhost:8080/getNames/${gameId}/${userId}`);

            const prop_response = await axios.get(`http://localhost:8080/getOwnedProperty/${gameId}/${name_response.data.propertyName}`);

            if (prop_response.data.propertyName === null) {
                console.log(`You're broke！`);
                document.getElementById("broke_h1").style.display = "block";
            } else console.log(`Player has purchased ${property.property_name}!`);
            // var buy_div = document.getElementById("buy");    
            // var end_div = document.getElementById("end");
            // var end_button = document.getElementById("end_button");
            // end_div.style.display='block';
            // end_button.style.display='block';

        } catch (error) {
            console.error('Error fetching player:', error);
        }
    };
    
    const loadUpdate = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getPlayerInGame/${gameId}/${userId}`);
            setPlayer({
                cur_pos: response.data.currentPosition,
                cur_dir: response.data.currentDirection,
                game_id: response.data.gameId,
                user_id: response.data.userId,
            });
    
            var roll_button = document.getElementById("roll_button");
            // var roll_div = document.getElementById("roll_div");
            roll_button.style.display='none';
            // roll_div.style.display='none';

            console.log(`loadUpdate: Player has moved to position ${response.data.currentPosition}.`);
        } catch (error) {
            console.error('Error fetching player:', error);
        }
    };

    const nextPosition = async () => {
        try {
            await axios.post("http://localhost:8080/updateRoll", {
                user_id : String(userId),
                game_id : String(gameId),
                game_code: String(gameCode),
            });

            await axios.post("http://localhost:8080/updatePos", {
                user_id : String(userId),
                game_id : String(gameId),
                game_code: String(gameCode),
            });
            loadUpdate(); // Reload player data after updating position
            loadProperty();
            loadAllPlayers();

        } catch (error) {
            console.error('Error updating roll number:', error);
            console.error('Error updating position:', error);
        }      
    };



    useEffect(() => {
        loadPlayer(); // Load player data when the component mounts
        loadAllPlayers();
        // loadProperty();
    }, [players]);


    if (!players) {
        return <div>Loading players...</div>;
    }


    console.log("All players!!", players)
    return (
        <>
                {/* lime */}
                <div className="container_board"> 
                    <div className="icon" id="player_icon_lime" 
                        style={{
                            ...players[0] ? parseStyle(propData.space[players[0].currentPosition]) : {display:"none"},
                            // display: "block", 
                            backgroundColor: "lime", 
                            boxShadow: "0.4vh 0.4vh rgb(12, 87, 13), 0.8vh 0.8vh 0.8vh rgba(0, 0, 0, 0.557)"
                        }}>
                        <div className="eyes">
                            <div className="eye"></div>
                            <div className="eye"></div>
                        </div>
                    </div>
                </div>
                {/* pink */}
                <div className="container_board">
                    <div className="icon" id="player_icon_pink" 
                        style={{
                            ...players[1] ? parseStyle(propData.space[players[1].currentPosition]) : {display:"none"}, // Ensure the correct use of player index and current position
                            // display: "block", 
                            // backgroundColor: "lime", 
                            // boxShadow: "0.4vh 0.4vh rgb(12, 87, 13), 0.8vh 0.8vh 0.8vh rgba(0, 0, 0, 0.557)"
                        }}>
                        <div className="eyes">
                            <div className="eye"></div>
                            <div className="eye"></div>
                        </div>
                    </div>
                </div>
                {/* light blue */}
                <div className="container_board">
                    <div className="icon" id="player_icon_blue" 
                        style={{
                            ...players[2] ? parseStyle(propData.space[players[2].currentPosition]) : {display:"none"},
                            // display: "block", 
                            backgroundColor: "rgb(76, 201, 255)", 
                            boxShadow: "0.4vh 0.4vh rgb(10, 75, 103), 0.8vh 0.8vh 0.8vh rgba(0, 0, 0, 0.557)"
                        }}>
                        <div className="eyes">
                            <div className="eye"></div>
                            <div className="eye"></div>
                        </div>
                    </div>
                </div>
                {/* red */}
                <div className="container_board">
                    <div className="icon" id="player_icon_red" 
                        style={{
                            ...players[3] ? parseStyle(propData.space[players[3].currentPosition]) : {display:"none"},
                            // display: "block", 
                            backgroundColor: "rgb(190, 0, 0)", 
                            boxShadow: "0.4vh 0.4vh rgb(79, 6, 6), 0.8vh 0.8vh 0.8vh rgba(0, 0, 0, 0.557)"
                        }}>
                        <div className="eyes">
                            <div className="eye"></div>
                            <div className="eye"></div>
                        </div>
                    </div>
                </div>
                {/* purple */}
                <div className="container_board">
                    <div className="icon" id="player_icon_purple" 
                        style={{
                            ...players[4] ? parseStyle(propData.space[players[4].currentPosition]) : {display:"none"},
                            // display: "block", 
                            backgroundColor: "rgb(102, 0, 255)",
                            boxShadow: "0.4vh 0.4vh rgb(27, 0, 67), 0.8vh 0.8vh 0.8vh rgba(0, 0, 0, 0.557)"
                        }}>
                        <div className="eyes">
                            <div className="eye"></div>
                            <div className="eye"></div>
                        </div>
                    </div>
                </div>
                {/* orange */}
                <div className="container_board">
                    <div className="icon" id="player_icon_orange" 
                        style={{
                            ...players[5] ? parseStyle(propData.space[players[5].currentPosition]) : {display:"none"},
                            // display: "block", 
                            backgroundColor: "rgb(255, 128, 0)", 
                            boxShadow: "0.4vh 0.4vh rgb(94, 53, 0), 0.8vh 0.8vh 0.8vh rgba(0, 0, 0, 0.557)"
                        }}>
                        <div className="eyes">
                            <div className="eye"></div>
                            <div className="eye"></div>
                        </div>
                    </div>
                </div>
                {/* yellow */}
                <div className="container_board">
                    <div className="icon" id="player_icon_yellow" 
                        style={{
                            ...players[6] ? parseStyle(propData.space[players[6].currentPosition]) : {display:"none"},
                            // display: "block", 
                            backgroundColor: "gold", 
                            boxShadow: "0.4vh 0.4vh rgb(97, 84, 12), 0.8vh 0.8vh 0.8vh rgba(0, 0, 0, 0.557)"
                        }}>
                        <div className="eyes">
                            <div className="eye"></div>
                            <div className="eye"></div>
                        </div>
                    </div>
                </div>
                {/* gray */}
                <div className="container_board">
                    <div className="icon" id="player_icon_gray" 
                        style={{
                            ...players[7] ? parseStyle(propData.space[players[7].currentPosition]) : {display:"none"},
                            // display: "block", 
                            backgroundColor: "rgb(114, 114, 114)", 
                            boxShadow: "0.4vh 0.4vh rgb(55, 55, 55), 0.8vh 0.8vh 0.8vh rgba(0, 0, 0, 0.557)"
                        }}>
                        <div className="eyes">
                            <div className="eye"></div>
                            <div className="eye"></div>
                        </div>
                    </div>
                </div>
                <Tiles gameId={gameId}/>
                
            <div className="center" id="roll_button" style={{ display: "none", margin:"auto auto" }}>
                <h1>Click to roll! </h1>
                <button onClick={nextPosition}>Roll!</button>
            </div>
            <div className="center" id="land" style={{ display: "none", margin:"auto auto", flexDirection: "column" }}>
                <h2 style={{marginTop:"10vh"}}>You landed on</h2>
                <h1 id="land_prop"> {property && property.property_name}, {property && property.set_name}.</h1>
                <h1 id="special_prop"></h1>
                <div className="center" id="buy" style={{ display: "none", margin:"13vh auto", flexDirection: "column" }}>
                    <h2 id = "buy_question" style={{marginTop:"1.2vh"}}> Buy Property?</h2>
                    <div className="center" id="buttons" style={{ display: "block", margin:"-2vh -1vh", flexDirection: "column" }}>
                        <button id = "buy_yes" onClick={purchaseProperty}> Yes</button>
                        <button id = "buy_no" style={{backgroundColor: "#50514F", marginLeft: "2vh"}}>No</button>
                    </div>
                </div>
            </div>
            <div className="center" id="end" style={{ display: "none", margin:"6vh auto", flexDirection: "column"}}>
                    <h1 id="broke_h1" style={{display: "none", marginTop:"1.2vh"}}> You're broke! You can't buy this! >:(</h1>
                    <button id = "end_button"style={{backgroundColor: "white", color: "black"}}> End Turn </button>
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