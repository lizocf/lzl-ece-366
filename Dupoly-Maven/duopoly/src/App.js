import "./App.css";
import "./style.css";
import {Component, useInsertionEffect } from "react";
import {useNavigate, useParams, BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PlayerTable from "./components/playertable";
import UpdateDirection from "./components/direction";
import Roll from "./components/update_position";
import Chat from "./components/chat"

import Tiles from "./components/tiles";
import Login from './components/Login/Login';
import axios from "axios";
import React, { useState, useEffect } from 'react';
import useToken from './components/useToken';


var username = "";
var r = "";
 


const JoinGame = ({userToken}) => {
    const navigate = useNavigate();

    const join = async () => {
        var code = document.getElementById("code").value;
        console.log("Joining game with code: " + code);
        var join_h1 = document.getElementById("join_h1");
        
        // check if code exists in database
        try {
            const response = await axios.get('http://localhost:8080/getGameInfo/' + code);
            console.log(response.data);
            if (response.data.gameCode == null) {
                console.log("Game does not exist. Please try again.")
                join_h1.innerHTML = "Game does not exist! Try again :(";
            } else {
                // if code exists, navigate to game
                console.log("Game exists. Joining game...")
                const gameResponse = await axios.get(`http://localhost:8080/getGameInfo/${code}`);

                // Create player in game
                const userResponse = await axios.get(`http://localhost:8080/getUserToken/${userToken}`);

                // check if player in game
                const checkUserInGameResponse = await axios.get(`http://localhost:8080/getPlayerInGame/${gameResponse.data.gameId}/${userResponse.data.userId}`);

                // if (checkUserInGameResponse.data.userName != null) {
                //     navigate(`/game/${code}`);
                // } else {
                    console.log("Creating player in game...");
                    axios.post("http://localhost:8080/createPlayerInGame", {
                        user_id: String(userResponse.data.userId),
                        game_id: String(gameResponse.data.gameId),
                    });
                    axios.post("http://localhost:8080/addUserToTurnOrder", {
                        user_id: String(userResponse.data.userId),
                        game_id: String(gameResponse.data.gameId),
                    });
                // }
                
                // navigate(`/game/${code}`);
            }
        } catch (error) {
            console.error('Error fetching game:', error);
        }}

    
    return (
        <div className="login-bg">
            <div className="container_middle">
            <div className="center" style={{margin: "10vh auto"}}>
            <form>
                <h1 id="join_h1" style={{margin: "2vh auto"}}>Join Game! :)</h1>
                <input className="nickname" id="code" type="text" placeholder="Enter code..." />
                <button className="button" type="button" onClick={() => navigate('/')} style={{margin: "-10vh auto", top: "30px", left:"-5px", color: "black"}}> Go Back</button>
                <button className="button" type="button" onClick={() => join()} style={{margin: "-10vh auto", top: "30px", left:"5px", backgroundColor: "#7e5ef4"}}> Join Game</button>
            </form>
            </div>
            </div>
        </div>
    );
};
const Lobby = ({ userToken }) => {
    const navigate = useNavigate();
    
    useEffect(() => {
        welcomeUser();
    }, [userToken]);

    const welcomeUser = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getUserToken/${userToken}`);
            console.log(`(welcomeUser) ${response.data.userName}.`);
            const welcome_h1 = document.getElementById("welcome_h1");
            if (welcome_h1) {
                welcome_h1.innerHTML = `Welcome ${response.data.userName}!`;
            }
        } catch (error) {
            console.error('Error fetching user account:', error);
        }
    };

    const createGame = async () => {
        const gameCode = (Math.random() + 1).toString(36).substring(4, 10);
        try {
            // Create new game
            await axios.post("http://localhost:8080/createNewGame", {
                game_code: gameCode // make this randomized
            });

            console.log('Game has been created. Game Code: ' + gameCode);

            // Fetch game information (presumably to get game_id)
            const gameResponse = await axios.get(`http://localhost:8080/getGameInfo/${gameCode}`);

            // Create player in game
            const userResponse = await axios.get(`http://localhost:8080/getUserToken/${userToken}`);

            await axios.post("http://localhost:8080/createPlayerInGame", {
                user_id: String(userResponse.data.userId),
                game_id: String(gameResponse.data.gameId),
            });
        
            axios.post("http://localhost:8080/addUserToTurnOrder", {
                user_id: String(userResponse.data.userId),
                game_id: String(gameResponse.data.gameId),
            });

            navigate(`/game/${gameCode}`);

        } catch (error) {
            console.error('Error in creating game:', error);
            const welcome_h1 = document.getElementById("welcome_h1");
            welcome_h1.innerHTML = "You're in a game already! Join that one >:(";

            // delete game that user tried to create
            await axios.post("http://localhost:8080/deleteGame", {game_code: gameCode})
            
        }
    };

    return (
        <div className="login-bg">
            <div className="container_middle">
            <div id="join_screen" className="center" style={{margin: "14vh auto", display: "flex", flexDirection: "column"}}>
                    <h1 id="welcome_h1">Welcome!</h1>
                    <button className="button" id="create_game" onClick={() => createGame()} style={{margin: "auto auto", top: "1vh", backgroundColor: "#7e5ef4"}}> Create Game</button>
                    <button className="button" id="join_game"onClick={() => navigate(`/joinGame`)} style={{margin: "1.5vh auto", top: "1vh", color: "black"}}> Join Game</button>
            </div>
            </div>
        </div>
    );
};


const Game = ({ userToken }) => {
    const [userId, setUserId] = useState(null); // State to hold userId
    const [getGameId, setGameId] = useState(null); // State to hold gameId
    const [turns, setTurns] = useState(null); // State to hold turn
    const { gameCode } = useParams();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const userResponse = await axios.get(`http://localhost:8080/getUserToken/${userToken}`);
                setUserId(userResponse.data.userId); // Update state with userId
                console.log(`(getUser) ${userResponse.data.userId}`);
                
                const gameResponse = await axios.get(`http://localhost:8080/getGameInfo/${gameCode}`);
                setGameId(gameResponse.data.gameId); // Update state with gameId
                console.log(`(gameResponse) ${gameResponse.data.gameId}`);
            } catch (error) {
                console.error('Error fetching data:', error);
            }            
        };
        
        const fetchTurn = async () => {
            try {
                const gameResponse = await axios.get(`http://localhost:8080/getGameInfo/${gameCode}`);
                const turnResponse = await axios.get(`http://localhost:8080/getGameTurnOrder/${gameResponse.data.gameId}`);
                const filteredTurns = turnResponse.data.filter(turn => turn !== null).map(turns => ({ userId: turns.userId, turn: turns.turnNumber}));
                // console.log(`(fetchTurn) ${filteredTurns[0].userId}`);
                setTurns(filteredTurns[0].userId); // Update state with turn
            } catch (error) {
                console.error('Error fetching turn:', error);
        }}

        fetchData();
        fetchTurn();
        const intervalId = setInterval(fetchTurn, 5000); // Fetch turn every 5 seconds
        return () => clearInterval(intervalId); // Cleanup on unmount
    }, [userToken, gameCode]); // useEffect will run when userToken or gameCode changes

    console.log('userId:', userId);
    console.log('turns:', turns);

    if (userId === null | getGameId === null | turns === null) {
        return <div>Loading...</div>;
    }

// check if player is first in turn order -> add userId to which_turn column in game_meta table
// return "Ready to play?" button

    return (
        <div>
            <div className="container_right" style={{margin: "-20vh auto"}}>
                <PlayerTable gameCode={gameCode} userId={userId} gameId={getGameId}/>
            </div>
            <div className="container_middle">
                <div className="center" id="direction_div">
                    <h1>Choose a direction!</h1>
                </div>
                <Roll gameCode={gameCode} userId={userId} gameId={getGameId}/>
            </div>
            <UpdateDirection gameCode={gameCode} userId={userId} gameId={getGameId}/>
            <div className="container_left" style={{bottom:"0", margin: "-22vh auto", backgroundColor:"rebeccapurple"}}>
                <div className="logs-table">
                    <Chat/>
                </div>

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
                    <Route path="/game/:gameCode" element={<Game userToken={token}/>} />
                    <Route path="/joinGame" element={<JoinGame userToken={token}/>} />
                </Routes>
            </Router>
    );
  }
  
export default App;