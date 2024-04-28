import "./App.css";
import "./style.css";
import {Component } from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PlayerTable from "./components/playertable";
import UpdateDirection from "./components/direction";
import Roll from "./components/update_position";
import Chat from "./components/chat"

import Tiles from "./components/tiles";
import LeftTables from "./components/lefttables";
import axios from "axios";
import React, { useState, useEffect } from 'react';


const LogIn = () => {
    const [userCredentials, setUserCredentials] = useState({ username: '', password: '' });
    const login = (username, password) => {
        setUserCredentials({ username, password });
    };

    function handleLogIn() {
        var log_in_button = document.getElementById("log_in");
        var sign_up_button = document.getElementById("sign_up");
        var create_user_screen = document.getElementById("create_user_screen");
        var menu_screen = document.getElementById("menu_screen");
        var login_screen = document.getElementById("login_screen");
        var go_back_log = document.getElementById("go_back_log");
        var enter_log = document.getElementById("enter_log");
        var go_back = document.getElementById("go_back");
        var enter = document.getElementById("enter");


        log_in_button.onclick = function() {
            login_screen.style.display = "block";
            menu_screen.style.display = "none";
        }

        sign_up_button.onclick = function() {
            create_user_screen.style.display = "block";
            menu_screen.style.display = "none";
    
        }

        go_back.onclick = function() {
            login_screen.style.display = "none";
            create_user_screen.style.display = "none";
            menu_screen.style.display = "block";
            menu_screen.style.flexDirection = "column";
        }
        
        enter.onclick = function() {
            console.log("Username: " + document.getElementById("new_username").value + " Password: " + document.getElementById("new_password").value)
        }

        go_back_log.onclick = function() {
            login_screen.style.display = "none";
            create_user_screen.style.display = "none";
            menu_screen.style.display = "block";
            menu_screen.style.flexDirection = "column";
        }
        
        enter_log.onclick = function() {
            console.log("Username: " + document.getElementById("username").value + " Password: " + document.getElementById("password").value)
        }
    }

    function createGame() {
        var create_button = document.getElementById("create_game");

        create_button.onclick = function() {
            var r = (Math.random() + 1).toString(36).substring(4,10);
            axios.post("http://localhost:8080/createNewGame", {
                game_code : r // make this randomized
            })
            console.log('Game has been created. Game Code: ' + r);
            // window.location.href = "/game";
        }

    }

    return (
        <div className="login-bg">
            <div className="container_middle">
            <div className="center" id="menu_screen" style={{margin: "auto auto", display: "flex", flexDirection: "column"}}>
                    <button type="button" className="button" id="log_in" onClick={() => handleLogIn()} style={{margin: "auto auto", backgroundColor: "#7e5ef4", padding: "1vh 5.4vh"}}> Log In!</button>
                    <button type="button" className="button" id="sign_up" onClick={() => handleLogIn()}  style={{margin: "1vh auto", padding: "1vh 4.5vh", color:"black"}}> Sign Up!</button>
            </div>
            <div className="center" style={{margin: "10vh auto"}}>
                <form id="create_user_screen" style={{display: 'none'}}>
                    <h1 style={{margin: "1vh auto", fontStyle:"italic"}}>Create Username :)</h1>
                    <input id="new_username" className="nickname" type="text" placeholder="Enter username..." />
                    <input  id="new_password" style={{margin: "1vh auto"}}  className="nickname" type="password" placeholder="Enter password..." />
                    <button type="button" className="button" id="go_back" style={{margin: "-2vh auto", top: "30px", left: "0px", padding: "15px 20px", backgroundColor: "#7e5ef4"}}> Go Back</button>
                    <button type="button" className="button" id="enter" style={{margin: "-2vh auto", top: "30px", padding: "15px 20px", backgroundColor: "#7e5ef4"}}> Enter</button>
                </form>
                <form id="login_screen" style={{display: 'none'}}>
                    <h1 style={{margin: "1vh auto", fontStyle:"italic"}}>Log In :)</h1>
                    <input id="username" className="nickname" type="text" placeholder="Enter username..." />
                    <input  id="password" style={{margin: "1vh auto"}}  className="nickname" type="password" placeholder="Enter password..." />
                    <button type="button" className="button" id="go_back_log" style={{margin: "-2vh auto", top: "30px", left: "0px", padding: "15px 20px", backgroundColor: "#7e5ef4"}}> Go Back</button>
                    <button type="button" className="button" id="enter_log" style={{margin: "-2vh auto", top: "30px", padding: "15px 20px", backgroundColor: "#7e5ef4"}}> Enter</button>
                </form>
            </div>
            {/* <div className="center" style={{margin: "10vh auto"}}>
                <a href="/game">
                    <button className="button" id="create_game"onClick={() => createGame()} style={{margin: "28.5vh auto", top: "30px", left: "0px", backgroundColor: "#7e5ef4"}}> Create Game</button>
                </a>
                <a href="/game">
                    <button className="button" id="join_game" style={{margin: "28.5vh auto", top: "30px", padding: "15px 20px"}}> Join Game</button>
                </a>
            </div> */}
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
                <PlayerTable/>
            </div>
            <div className="container_middle">
                <div className="center" id="direction_div">
                    <h1>Choose a direction!</h1>
                </div>
                <Roll/>
            </div>
            <UpdateDirection/>
            <div className="container_left" style={{margin: "-20vh auto"}}>
                <Chat/>
            </div>
        </div>
    );
};

class App extends Component {
    render() {
        return (
            <Router>
                <Routes>
                    <Route path="/" element={<LogIn />} />
                    <Route path="/lobby" element={<Game />} />
                    <Route path="/game" element={<Game />} /> {/* change to /game/{gamecode} */}
                    {/* https://www.sitepoint.com/get-url-parameters-with-javascript/ */}
                </Routes>
            </Router>
        );
    }
}

export default App;