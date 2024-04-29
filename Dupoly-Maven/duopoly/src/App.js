import "./App.css";
import "./style.css";
import {Component } from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PlayerTable from "./components/playertable";
import UpdateDirection from "./components/direction";
import Roll from "./components/update_position";
import Tiles from "./components/tiles";
import LeftTables from "./components/lefttables";
import Login from './components/Login/Login';
import axios from "axios";
import React, { useState, useEffect } from 'react';
import useToken from './components/useToken';

// const express = require('express')()
// const path = require('path')
// const app = express()
// const PORT = process.env.PORT || 4000
// const server = app.listen(PORT, () => {console.log(`Server is running on port ${PORT}`)})

// app.use(express.static(path.join(__dirname + 'public')))

// var username = "";

// const LogIn = () => {
//     const [userCredentials, setUserCredentials] = useState({ username: '', password: '' });
//     const [token, setToken] = useState();
//     const login = (username, password) => {
//         setUserCredentials({ username, password});
//     };

//     const checkUser = async() => {
//         try {
//             const response = await axios.get('http://localhost:8080/getUserName/' + userCredentials.username);
//         } catch (error) {
//             console.error('checkUser: Error getting user:', error);
//         }
//     };

//     const createUser = async () => {
//         try {
//             const response = await axios.post("http://localhost:8080/createNewAccount", {
//             user_name : document.getElementById("username").value,
//             user_pw : document.getElementById("password").value
//         })
//         setUserCredentials({ username: document.getElementById("username").value, password: document.getElementById("password").value });
//         return true
//         } catch (error) {
//             if (error.response) {
//                 // console.log(error.response.data);
//                 // console.log(error.response.status);
//                 // console.log(error.response.headers);
//                 console.log("This username is already taken. Please try again.")
//                 let create_h1 = document.getElementById("create_h1");
//                 create_h1.innerHTML = "This username is already taken. Please try again :(";
//             } else if (error.request) {
//                 /*
//                  * The request was made but no response was received, `error.request`
//                  * is an instance of XMLHttpRequest in the browser and an instance
//                  * of http.ClientRequest in Node.js
//                  */
//                 console.log(error.request);
//             } else {
//                 // Something happened in setting up the request and triggered an Error
//                 console.log('Error', error.message);
//             }
//             console.log(error);
//             return false;
//         }
//     }


//     function handleLogIn() {
//         var log_in_button = document.getElementById("log_in");
//         var sign_up_button = document.getElementById("sign_up");
//         var menu_screen = document.getElementById("menu_screen");
//         var login_screen = document.getElementById("login_screen");
//         var go_back = document.getElementById("go_back");
//         var enter = document.getElementById("enter");
//         var login_h1 = document.getElementById("login_h1");
//         var create_h1 = document.getElementById("create_h1");

//         log_in_button.onclick = function() {
//             login_screen.style.display = "block";
//             menu_screen.style.display = "none";
//             login_h1.style.display = "block";
//             create_h1.style.display = "none";
//         }

//         sign_up_button.onclick = function() {
//             login_screen.style.display = "block";
//             menu_screen.style.display = "none";
//             login_h1.style.display = "none";
//             create_h1.style.display = "block";

//             enter.onclick = function() {
//                 let join_screen = document.getElementById("join_screen");
//                 let create_user_screen = document.getElementById("create_user_screen");
//                 let welcome_h1 = document.getElementById("welcome_h1");
//                 username = document.getElementById("username").value;
//                 createUser().then((createCheck) => {
//                     if (createCheck) {
//                         create_user_screen.style.display = "none";
//                         join_screen.style.display = "flex";
//                         welcome_h1.innerHTML = "Welcome " + username + "!";
//                     } else {
//                         console.log("Error creating account");
//                     }
//                 });
//                 console.log("Created Account: " + username + " Password: " + document.getElementById("password").value); // I still dont know how useState works :(
                
//             }
//         }

//         go_back.onclick = function() {
//             login_screen.style.display = "none";
//             menu_screen.style.display = "block";
//             menu_screen.style.flexDirection = "column";
//         }

//         // go_back_log.onclick = function() {
//         //     login_screen.style.display = "none";
//         //     create_user_screen.style.display = "none";
//         //     menu_screen.style.display = "block";
//         //     menu_screen.style.flexDirection = "column";
//         // }

//         // enter_log.onclick = function() {
//         //     console.log("Username: " + document.getElementById("username").value + " Password: " + document.getElementById("password").value)
//         // }
//     }

//     function createGame() {
//         var create_button = document.getElementById("create_game");

//         create_button.onclick = function() {
//             var r = (Math.random() + 1).toString(36).substring(4,10);
//             axios.post("http://localhost:8080/createNewGame", {
//                 game_code : r // make this randomized
//             })
//             console.log('Game has been created. Game Code: ' + r);
//             // window.location.href = "/game";
//         }

//     }


//     return (
//         <div className="login-bg">
//             <div className="container_middle">
//             <div className="center" id="menu_screen" style={{margin: "auto auto", display: "flex", flexDirection: "column"}}>
//                     <button type="button" className="button" id="log_in" onClick={() => handleLogIn()} style={{margin: "auto auto", backgroundColor: "#7e5ef4", padding: "1vh 5.4vh"}}> Log In!</button>
//                     <button type="button" className="button" id="sign_up" onClick={() => handleLogIn()}  style={{margin: "1vh auto", padding: "1vh 4.5vh", color:"black"}}> Sign Up!</button>
//             </div>
//             <div className="center" style={{margin: "10vh auto"}}>
//                 <form id="login_screen" style={{display: 'none'}}>
//                     <h1 id="login_h1" style={{margin: "1vh auto", fontStyle:"italic", display:"none"}}>Log In :)</h1>
//                     <h1 id="create_h1" style={{margin: "1vh auto", fontStyle:"italic", display:"none"}}>Create Username :)</h1>
//                     <input id="username" className="nickname" type="text" placeholder="Enter username..." />
//                     <input  id="password" style={{margin: "1vh auto"}}  className="nickname" type="password" placeholder="Enter password..." />
//                     <button type="button" className="button" id="go_back" style={{margin: "-2vh auto", top: "30px", left: "0px", padding: "15px 20px", backgroundColor: "#7e5ef4"}}> Go Back</button>
//                     <button type="button" className="button" id="enter" style={{margin: "-2vh auto", top: "30px", padding: "15px 20px", backgroundColor: "#7e5ef4"}}> Enter</button>
//                 </form>
//             </div>
//             <div id="join_screen" className="center" style={{margin: "14vh auto", display: "none", flexDirection: "column"}}>
//                     <h1 id="welcome_h1">Welcome!</h1>
//                     <button className="button" id="create_game"onClick={() => createGame()} style={{margin: "auto auto", top: "1vh", backgroundColor: "#7e5ef4"}}> Create Game</button>
//                     <button className="button" id="join_game" style={{margin: "1.5vh auto", top: "1vh"}}> Join Game</button>
//             </div>
//             </div>
//         </div>
//     );
// };

function LogIn() {
    const [userCredentials, setUserCredentials] = useState({ username: '', password: '' });
    const [viewState, setViewState] = useState('menu'); // 'menu', 'login', 'create', 'game'
    const [error, setError] = useState('');

    const handleInputChange = (e) => {
        const { id, value } = e.target;
        setUserCredentials(prev => ({ ...prev, [id]: value }));
    };

    const handleLogin = async () => {
        try {
            // You might have a different API for checking the credentials
            const response = await axios.post('http://localhost:8080/login', userCredentials);
            setViewState('game');
        } catch (error) {
            console.error('Login error:', error);
            setError('Failed to log in. Please check your username and password.');
        }
    };

    const handleCreateAccount = async () => {
        try {
            const response = await axios.post('http://localhost:8080/createNewAccount', userCredentials);
            setViewState('game');
        } catch (error) {
            console.error('Create account error:', error);
            setError('This username is already taken. Please try again.');
        }
    };

    const displayMenu = () => (
        <div className="center" style={{ flexDirection: "column" }}>
            <button style={{margin: "auto auto", backgroundColor: "#7e5ef4", padding: "1vh 5.4vh"}} onClick={() => setViewState('login')} className="button">Log In</button>
            <button style={{margin: "1vh auto", padding: "1vh 4.5vh", color:"black"}} onClick={() => setViewState('create')} className="button">Sign Up</button>
        </div>
    );

    const displayLoginForm = () => (
        <form>
            <h1>Log In :)</h1>
            <input id="username" value={userCredentials.username} onChange={handleInputChange} type="text" placeholder="Enter username..." />
            <input id="password" value={userCredentials.password} onChange={handleInputChange} type="password" placeholder="Enter password..." />
            <button type="button" onClick={handleLogin} className="button">Log In</button>
            <button type="button" onClick={() => setViewState('menu')} className="button">Go Back</button>
            {error && <p>{error}</p>}
        </form>
    );

    const displayCreateForm = () => (
        <form>
            <h1>Create Username :)</h1>
            <input id="username" value={userCredentials.username} onChange={handleInputChange} type="text" placeholder="Enter username..." />
            <input id="password" value={userCredentials.password} onChange={handleInputChange} type="password" placeholder="Enter password..." />
            <button type="button" onClick={handleCreateAccount} className="button">Create Account</button>
            <button type="button" onClick={() => setViewState('menu')} className="button">Go Back</button>
            {error && <p>{error}</p>}
        </form>
    );

    const displayGameScreen = () => (
        <div className="center">
            <h1>Welcome, {userCredentials.username}!</h1>
            <button className="button">Create Game</button>
            <button className="button">Join Game</button>
        </div>
    );

    return (
        <div className="login-bg">
            {viewState === 'menu' && displayMenu()}
            {viewState === 'login' && displayLoginForm()}
            {viewState === 'create' && displayCreateForm()}
            {viewState === 'game' && displayGameScreen()}
        </div>
    );
}

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
  function App() {

    const { token, setToken } = useToken();
  
    if(!token) {
      return <LogIn setToken={setToken} />
    }
    return (
        <Router>
                <Routes>
                    <Route path="/" element={<LogIn />} />
                    <Route path="/game" element={<Game />} /> {/* change to /game/{gamecode} */}
                </Routes>
            </Router>
    );
  }
  
export default App;