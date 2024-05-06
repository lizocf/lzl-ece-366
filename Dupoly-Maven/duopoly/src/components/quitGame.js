import React, { useState, useRef, useEffect } from 'react';
import axios from "axios";
import {useNavigate, useParams, BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { FaFlag, FaDoorOpen } from 'react-icons/fa';

const QuitGame = ({gameCode,userId,gameId}) => {
    const navigate = useNavigate();

    const backToMenu = async () => {
        try {
            await axios.post("http://18.191.154.84:8080/delPlayer",{
                user_id : String(userId),
                game_id : String(gameId)
            })



            navigate('/');
        } catch (error) {
            console.error('Error deleting player:', error);
        }
    };

    return(
        <>
            {/*<div className="container_button_back2menu" style={{margin: "-73vh 510px"}}>*/}
            <>
                <div className="container_button" style={{ margin: "2vh 400px" }}>
                    <button style={{
                        backgroundColor: 'rgb(84,142,220)',
                        color: 'white',
                        width: '80px',
                        height: '40px',
                        fontSize: '14px',
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center'
                    }} onClick={backToMenu}>
                        Exit Game <h1><FaDoorOpen size={18} style={{ color: 'white', marginLeft: '5px', marginTop: '10px' }} /></h1>
                    </button>
                </div>
            </>
        </>
    )

}

export default QuitGame;

