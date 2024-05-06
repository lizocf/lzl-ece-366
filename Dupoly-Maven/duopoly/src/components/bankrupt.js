import React, { useState, useRef, useEffect } from 'react';
import axios from "axios";
import {FaFlag, FaPaperPlane} from 'react-icons/fa';

const UpdateBankrupt = ({userId,gameId}) =>{

    const [player, setPlayer] = useState(null);
    const [turnOrder, setTurnOrder] = useState(null);

    console.log("Player is bankrupt: " + userId);

    const loadPlayer = async () => {
        try {
            const response = await axios.get(`http://18.191.154.84:8080/getPlayerInGame/${gameId}/${userId}`);

            // we want to modify that they are "dead" when bankrupt
            setPlayer(
                {
                    is_dead: response.data.dead
                }
            )
            console.log(`Is Player dead: ${response.data.dead}.`);
        } catch (error){
            console.error('Error fetching player:', error);
        }
    };

    // not sure if I need this for a delete
    // const loadTurnOrder = async () => {
    //     try{
    //         const response = await axios.get('http://18.191.154.84:8080/getGameTurnOrder/${gameId}/${userId}');
    //
    //         setTurnOrder(
    //             {
    //
    //             }
    //         )
    //
    //
    //     }catch (error){
    //
    //     }
    // }

    function UpdateDead(){

        // something here to get the id of the bankrupt button
        // var bankrupt_button = document.getElementById("bankrupt")
        // axios.post("http://18.191.154.84:8080/updateDead", {
        //     user_id : String(userId),
        //     game_id : String(gameId)
        // })

        axios.post("http://18.191.154.84:8080/delPlayer",{
            user_id : String(userId),
            game_id : String(gameId)
        })

        console.log("Player is bankrupt gg");
        loadPlayer();
    }

    // idk what to return
    return(
        <>
            <div className="container_button" style={{margin: "9vh 400px"}}>
                <button style={{
                    backgroundColor: 'rgb(226,121,89)',
                    color: 'white',
                    width: '80px',
                    height: '40px',
                    fontSize: '14px',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center'
                }} onClick={UpdateDead}>Bankrupt! <h1><FaFlag size={18} style={{color: 'white', marginLeft: '5px', marginTop: '-10px'}}/></h1>
                </button>
            </div>
        </>
    )
}
export default UpdateBankrupt
