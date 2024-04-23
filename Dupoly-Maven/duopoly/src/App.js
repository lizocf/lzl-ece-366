import "./App.css";
import "./style.css";
import {Component } from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PlayerTable from "./components/playertable";
import UpdateDirection from "./components/direction";
import Roll from "./components/update_position";
import Tiles from "./components/tiles";
import LeftTables from "./components/lefttables";

const LogIn = () => {
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

const createGame = () => {
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
                    <Route path="/game" element={<Game />} />
                </Routes>
            </Router>
        );
    }
}

export default App;