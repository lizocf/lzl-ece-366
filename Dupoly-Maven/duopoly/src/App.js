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
import UpdateBankrupt from "./components/bankrupt";
import QuitGame from "./components/quitGame";
import Endscreen from "./components/endscreen";


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
                
                console.log("Check player: ", checkUserInGameResponse.data)
                if (checkUserInGameResponse.data.userName !== null) {
                    navigate(`/game/${code}`);
                } else {
                    console.log("Creating player in game...");
                    await axios.post("http://localhost:8080/createPlayerInGame", {
                        user_id: String(userResponse.data.userId),
                        game_id: String(gameResponse.data.gameId),
                    });
                    await axios.post("http://localhost:8080/addUserToTurnOrder", {
                        user_id: String(userResponse.data.userId),
                        game_id: String(gameResponse.data.gameId),
                    });
                }
                
                navigate(`/game/${code}`);
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

            await axios.post("http://localhost:8080/updateHost", {
                host: String(userResponse.data.userId),
                game_code: gameCode
            });
            await axios.post("http://localhost:8080/createPlayerInGame", {
                user_id: String(userResponse.data.userId),
                game_id: String(gameResponse.data.gameId),
            });
<<<<<<< Updated upstream
        
=======

>>>>>>> Stashed changes
            await axios.post("http://localhost:8080/addUserToTurnOrder", {
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
    const [numTurns, setNumTurns] = useState(null); // State to hold number of turns
    

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
                const endGameDiv = document.getElementById("EndGame");
                const gameResponse = await axios.get(`http://localhost:8080/getGameInfo/${gameCode}`);
                const turnResponse = await axios.get(`http://localhost:8080/getGameTurnOrder/${gameResponse.data.gameId}`);
                const filteredTurns = turnResponse.data.filter(turn => turn !== null).map(turns => ({ userId: turns.userId, turn: turns.turnNumber}));
                // console.log(`(fetchTurn) ${filteredTurns[0].userId}`);
                setTurns(filteredTurns[0].userId); // Update state with turn

                setNumTurns(gameResponse.data.numTurns);
                if (filteredTurns[filteredTurns.length - 1].userId > 0) {
                    axios.post("http://localhost:8080/updateLastPlayer", {last_player: String(filteredTurns[filteredTurns.length - 1].userId), game_code: gameCode});
                }

                setNumTurns(gameResponse.data.recentRoll)

                // if((filteredTurns.length === 1))
                // {

                if((gameResponse.data.recentRoll > 0) && (filteredTurns.length === 1))
                {
                    endGameDiv.style.display = "block";
                }
                // console.log(filteredTurns.length)
                console.log("The number of filtered turns is: " + filteredTurns.length);


                // }
            } catch (error) {
                console.error('Error fetching turn:', error);
        }}

        fetchData();
        fetchTurn();
        const intervalId = setInterval(fetchTurn, 5000); // Fetch turn every 5 seconds
        return () => clearInterval(intervalId); // Cleanup on unmount
    }, [userToken, gameCode, turns]); // useEffect will run when userToken or gameCode changes

    console.log('userId:', userId);
    console.log('turns:', turns);
    console.log('numTurns:', numTurns);

    const beginGame = () => {
        var ready_button = document.getElementById("ready_button");
        let directionDiv = document.getElementById("direction_div");
        let updateDirDiv = document.getElementById("update_dir_div");
        let waitingDiv = document.getElementById("waiting_div");
        // if (directionDiv) {
        //     directionDiv.style.display = "block";
        // }
        // if (updateDirDiv) {
        //     updateDirDiv.style.display = "block";
        // }

        if (ready_button) {
            ready_button.style.display = "none";
        }

        if (waitingDiv) {
            waitingDiv.style.display = "none";
        }
        axios.post("http://localhost:8080/updateJoinable", {joinable: "false", game_code: gameCode});
        axios.post("http://localhost:8080/updateNumTurns", {num_turns: "1", game_code: gameCode});
        axios.post("http://localhost:8080/")

    };

    const fetchEverything = async () => {
        try {
            const gameResponse = await axios.get(`http://localhost:8080/getGameInfo/${gameCode}`);
            const readyButton = document.getElementById("ready_button");
            const ready_button = document.getElementById("ready_button");
            const directionDiv = document.getElementById("direction_div");
            const updateDirDiv = document.getElementById("update_dir_div");
            const waitingDiv = document.getElementById("waiting_div");
            const roll = document.getElementById("roll_button");

            console.log("(numTurns)",gameResponse.data.numTurns)

            if (turns === userId) { // its our user's turn!

                if (numTurns === 0 && userId === gameResponse.data.host) { // is it THE first turn?
                    axios.post("http://localhost:8080/updatePlayerTurn", { // updates which_player_turn NOT turn_order
                    user_id: String(userId),
                    game_code: gameCode
                });
                    readyButton.style.display = "block";
                    readyButton.onclick = function() {
                        axios.post("http://localhost:8080/updateJoinable", {joinable: "false", game_code: gameCode});
                        // directionDiv.style.display = "block";
                        // updateDirDiv.style.display = "block";
                        ready_button.style.display = "none";
                        waitingDiv.style.display = "none";
                    }
                } else if(numTurns === 1) { // first player's turn
                    axios.post("http://localhost:8080/updatePlayerTurn", { // updates which_player_turn NOT turn_order
                        user_id: String(userId),
                        game_code: gameCode
                    });
                    directionDiv.style.display = "block";
                    updateDirDiv.style.display = "block";
                    ready_button.style.display = "none";
                    waitingDiv.style.display = "none";
                } else if (numTurns > 1) { // no longer choose direction
                    axios.post("http://localhost:8080/updatePlayerTurn", { // updates which_player_turn NOT turn_order
                        user_id: String(userId),
                        game_code: gameCode
                    });
                    roll.style.display = "block";
                    waitingDiv.style.display = "none";
                    directionDiv.style.display = "none";
                    updateDirDiv.style.display = "none";
                    ready_button.style.display = "none";
                } else { // not our user's turn
                waitingDiv.style.display = "block";
                directionDiv.style.display = "none";
                updateDirDiv.style.display = "none";
                ready_button.style.display = "none";
                roll.style.display = "none";
                }
            }
         } catch (error) {
            console.error('Error fetching data:', error);
        }            
    };


fetchEverything();
// return "Ready to play?" button -> onClick: joinable=False. Do we need to update num_players? idk

// if not first in turn order -> return "Waiting for <user_name>... "


    // want to do something where we check the length of turns.
    // If its one or one player in the array then we know the game is over



    if (userId === null | getGameId === null | turns === null | numTurns === null) {

        console.log('Loading...')
        return <div>Loading...</div>;
    }


    return (
        <div>
            <div className="container_right" style={{margin: "-20vh auto"}}>
                <PlayerTable gameCode={gameCode} userId={userId} gameId={getGameId}/>
            </div>
            <div className="container_middle">
                <div className="center" id="direction_div" style={{display: "none"}}>
                    <h1>Choose a direction!</h1>
                </div>
                
                <Roll gameCode={gameCode} userId={userId} gameId={getGameId}/>


                <div className="center" id="ready_button" style={{display:"none"}}>
                    <button className="button" onClick={() => beginGame()} style={{ margin: " auto", backgroundColor:"maroon"}}>ready? :D</button>
                    {/* <button className="button" style={{margin: " auto", backgroundColor:"maroon"}}>ready? :D</button> */}
                </div>
                <div className="center" id="waiting_div" style={{display: "block"}}>
                    <h1>Waiting for players...</h1>
                </div>        
            </div>
            <div id="update_dir_div" style={{display:"none"}}>
                <UpdateDirection gameCode={gameCode} userId={userId} gameId={getGameId}/>
            </div>
            {/* <div className="container_left" style={{margin: "-10vh 58px", backgroundColor:"white"}}> */}
                {/* <div className="logs-table" style={{margin: "-9.3vh auto"}}> */}

                    <Chat/>
                {/* </div> */}
            {/* </div> */}


            {/* <div className="container_left" style={{margin: "-2.5vh 123px"}}> */}
                <UpdateBankrupt userId={userId} gameId={getGameId}/>
            {/* </div> */}

            <div>
                <QuitGame gameCode={gameCode} userId={userId} gameId={getGameId}/>
            </div>

            <div className="container_left" style={{margin: "auto auto"}}>   
                <main className="props-table" id="property_info" style={{background: "#fff5", borderRadius: "0.8rem"}}>
                    <section className="table__body">
                        <h1>Property Info</h1>
                        <table>
                            <thead>
                                <tr>
                                    <th>Property Name</th>
                                    <th>Set Name</th>
                                    <th>Number of Hotels</th>
                                </tr>
                            </thead>
                            <tbody id="updated_props">
                                {/* <tr>
                                    <td>Property Name</td>
                                    <td>Set Name</td>
                                    <td>Number of Hotels</td>
                                </tr> */}
                            </tbody>
                        </table>
                    </section>
                    <section className="table__body">
                        <h1>Rent Information</h1>
                        <table>
                            <tbody id="rent">
                            {/* RentTable */}
                            </tbody>
                        </table>
                    </section>
                </main>
            </div>
            <div id="EndGame" style={{display: "none"}}>
                <Endscreen/>;
            </div>


        </div>
    );
};


  function App() {
    const { token, setToken } = useToken();
  
    const response = axios.get(`http://localhost:8080/getUserToken/${token}`);

    if(!token || response.data === null) {
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