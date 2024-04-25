import "./App.css";
import "./style.css";
import {Component } from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PlayerTable from "./components/playertable";
import UpdateDirection from "./components/direction";
import Roll from "./components/update_position";
import Tiles from "./components/tiles";
import LeftTables from "./components/lefttables";
import axios from "axios";
import React, { useState, useEffect } from 'react';


const LogIn = () => {
    function createGame() {
        var create_button = document.getElementById("create_game");

        create_button.onclick = function() {
            axios.post("http://localhost:8080/createNewGame", {
                game_code : "sdsd" // make this randomized
            })
            console.log('Game has been created.');
            // window.location.href = "/game";
        }

    }

    // function joinGame() {
    //     var join_button = document.getElementById("join_game");

    //     create_button.onclick = function() {
    //         axios.post("http://localhost:8080/createNewGame", {
    //             game_code : "EEEE"
    //         })
    //         console.log('Game has been created.');
    //     }
    // }

    // useEffect(() => {
    //     setGame();
    //     }, []);

    return (
        <div className="login-bg">
            <div className="container_middle">
            <div className="center" style={{margin: "10vh auto"}}>
            <form>
                <input className="nickname" type="text" placeholder="Enter nickname..." />
                </form>
            </div>
            <a href="/game">
            <button className="button" id="create_game"onClick={() => createGame()} style={{margin: "25vh auto", top: "30px", left: "0px", backgroundColor: "#7e5ef4"}}> Create Game</button>
            </a>
            <a href="/game">
            <button className="button" id="join_game" style={{margin: "25vh auto", top: "30px", padding: "15px 20px"}}> Join Game</button>
            </a>
            </div>
        </div>
    );
};


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

const Lobby = () => {
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

const Game = () => {
    return (
        <div>
            <div className="container_right" style={{margin: "-20vh auto"}}>
                <PlayerTable />
            </div>
            <div className="container_middle">
                <div className="center" id="direction_div">
                    <h1>Choose a direction!</h1>
                </div>
                <Roll />
            </div>
            <UpdateDirection />
        </div>
    );
};

class App extends Component {
    render() {
        return (
            <Router>
                <Routes>
                    <Route path="/" element={<LogIn />} />
                    <Route path="/game" element={<Game />} /> {/* change to /game/{gamecode} */}
                </Routes>
            </Router>
        );
    }
}

export default App;