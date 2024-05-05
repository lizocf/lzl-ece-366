import React from 'react'
import {useNavigate, useParams, BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";
import "./update_position";


const Endscreen = () => {
    const navigate = useNavigate();

    // end screen that drops down when one player is not bankrupt. This might happen in app.js

    // need to implement a back to main menu function

    function backToMenu(){
        console.log("Back to Menu")
    }

    return(
        <>
            <div className="container_board" style={{
                position: 'absolute',
                backgroundColor: 'rgba(128, 0, 128, 0.8)',
                width: '100%',
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center'
            }}>
                <h1 style={{color: 'white'}}>GAME OVER</h1>
                <button className="main_menu_button" style={{
                    position: 'relative',
                    padding: '15px 30px',
                    fontSize: '1.5em',
                    backgroundColor: 'rgba(255, 255, 255, 0.7)',
                    borderRadius: '10px',
                    border: 'none',
                    cursor: 'pointer'
                }} onClick={() => navigate("/")}>Back to Main Menu
                </button>
            </div>


        </>
    )
}

export default Endscreen;