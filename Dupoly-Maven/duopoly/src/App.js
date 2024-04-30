import "./App.css";
import "./style.css";
import {Component } from "react";
import {useNavigate, useParams, BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PlayerTable from "./components/playertable";
import UpdateDirection from "./components/direction";
import Roll from "./components/update_position";
import Tiles from "./components/tiles";
import LeftTables from "./components/lefttables";
import Login from './components/Login/Login';
import axios from "axios";
import React, { useState, useEffect } from 'react';
import useToken from './components/useToken';
// import express from 'express';

// // const app = express();


// // const cors = require('cors');
// // const corsOptions = {
// //     origin:'http://localhost:3000', 
// //     credentials:true,            //access-control-allow-credentials:true
// //     optionSuccessStatus:200
// // }
// // app.use(cors(corsOptions));

// const express = require('express')()
// const path = require('path')
// const app = express()
// const PORT = process.env.PORT || 4000
// const server = app.listen(PORT, () => {console.log(`Server is running on port ${PORT}`)})

// app.use(express.static(path.join(__dirname + 'public')))

var username = "";
var r = "";
 
const JoinGame = () => {
    return (
        <div className="login-bg">
            <div className="container_middle">
            <div className="center" style={{margin: "10vh auto"}}>
            <form>
                <input className="nickname" type="text" placeholder="Enter nickname..." />
                <button className="button" style={{margin: "-10vh auto", top: "30px", left: "0px", backgroundColor: "#7e5ef4"}}> Create Game</button>
                <button className="button" style={{margin: "-10vh auto", top: "30px", padding: "15px 20px"}}> Join Game</button>
            </form>
            </div>
            </div>
        </div>
    );
};

const Lobby = ({userToken}) => {
    const navigate = useNavigate();
    console.log(userToken)
    const createGame = () => {
        var create_button = document.getElementById("create_game");
  
        create_button.onclick = function() {
            r = (Math.random() + 1).toString(36).substring(4,10);
            axios.post("http://localhost:8080/createNewGame", {
                game_code : r // make this randomized
            })
            console.log('Game has been created. Game Code: ' + r);
            // window.location.href = "/game";
            navigate(`/game/${r}`);
        }
      }
    return (
        <div className="login-bg">
            <div className="container_middle">
            <div id="join_screen" className="center" style={{margin: "14vh auto", display: "flex", flexDirection: "column"}}>
                    <h1 id="welcome_h1">Welcome!</h1>
                    <button className="button" id="create_game" onClick={() => createGame()} style={{margin: "auto auto", top: "1vh", backgroundColor: "#7e5ef4"}}> Create Game</button>
                    <button className="button" id="join_game" style={{margin: "1.5vh auto", top: "1vh"}}> Join Game</button>
            </div>
            </div>
        </div>
    );
};

const Game = () => {
    const [player, setPlayer] = useState(null);
    const { gameCode } = useParams();
    const userId = 0;

    
    const getGame = async () => {
    try {
        const response = await axios.get('http://localhost:8080/getGameInfo/' + gameCode);
        console.log(response.data);
    } catch (error) {
        console.error('Error fetching players:', error);
    }}

    const getUser = async () => {
        try {       
            const user_response = await axios.get('http://localhost:8080/getUserName/' + username);
            console.log('User', user_response.data.userId);
            userId = user_response.data.userId;
        } catch (error) {
            console.error('Error fetching players:', error);
        }}
    

    getGame();
    getUser();

    console.log("caught user id: " + userId)

    return (
        <div>
            <div className="container_right" style={{margin: "-20vh auto"}}>
                <PlayerTable gameCode={gameCode} userId={userId} />
            </div>
            <div className="container_middle">
                <div className="center" id="direction_div">
                    <h1>Choose a direction!</h1>
                </div>
                <Roll gameCode={gameCode} />
            </div>
            <UpdateDirection gameCode={gameCode} />
            <div className="container_button">
                
            </div>
        </div>
    );
};

  function App() {

    const { token, setToken } = useToken();
  
    if(!token) {
      return <Login setToken={setToken} />
    }
    return (
        <Router>
                <Routes>
                    <Route path="/" element={<Lobby userToken={token}/>} />
                    {/* <Route path="/game" element={<Game />} /> change to /game/{gamecode} */}
                    <Route path="/game/:gameCode" element={<Game />} />
                </Routes>
            </Router>
    );
  }
  
export default App;