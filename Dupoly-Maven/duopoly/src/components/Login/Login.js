import React, { useState } from 'react';
import PropTypes from 'prop-types';
import './Login.css';
import axios from 'axios';
import {useNavigate, useParams, BrowserRouter as Router, Route, Routes } from 'react-router-dom';

async function loginUser(credentials) {
 return fetch('http://localhost:8090/login', {
   method: 'POST',
   headers: {
     'Content-Type': 'application/json'
   },
   body: JSON.stringify(credentials)
 })
   .then(data => data.json())
}

var r = "";
var username = "";

export default function Login({ setToken }) {
  const [username, setUserName] = useState();
  const [password, setPassword] = useState();
  // const navigate = useNavigate();

  const handleSubmit = async e => {
    console.log("Username: " + document.getElementById("username").value + " Password: " + document.getElementById("username").value);
    // e.preventDefault();
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    const token = await loginUser({
      username,
      password
    });
    setToken(token);
    console.log(token.token)
    try {
      const response = await axios.post("http://localhost:8080/createToken", {
        token : token.token,
        user_name : username
    })
    console.log("(Login) "+ response, username, token.token);
   } catch (error) {
      console.log(error);
    }

  }
  
  
  function handleLogIn() {
    var log_in_button = document.getElementById("log_in");
    var sign_up_button = document.getElementById("sign_up");
    var menu_screen = document.getElementById("menu_screen");
    var login_screen = document.getElementById("login_screen");
    var go_back = document.getElementById("go_back");
    var enter = document.getElementById("enter");
    var login_h1 = document.getElementById("login_h1");
    var create_h1 = document.getElementById("create_h1");
    var join_screen = document.getElementById("join_screen");
    var welcome_h1 = document.getElementById("welcome_h1");

    go_back.onclick = function() {
      login_screen.style.display = "none";
      menu_screen.style.display = "flex";
      menu_screen.style.flexDirection = "column";
    }

    log_in_button.onclick = function() {
        login_screen.style.display = "block";
        menu_screen.style.display = "none";
        login_h1.style.display = "block";
        create_h1.style.display = "none";
    }

    sign_up_button.onclick = function() {
        login_screen.style.display = "block";
        menu_screen.style.display = "none";
        login_h1.style.display = "none";
        create_h1.style.display = "block";

        enter.onclick = function() {
            // username = document.getElementById("username").value;
            console.log("username: " + document.getElementById("username").value);
            createUser().then((createCheck) => {
                if (createCheck) {
                    // create_user_screen.style.display = "none";
                    login_screen.style.display = "none";
                    join_screen.style.display = "flex";
                    welcome_h1.innerHTML = "Welcome " + document.getElementById("username").value + "!";
                } else {
                    console.log("Error creating account");
                }
            });
            handleSubmit();
            console.log("Created Account: " + document.getElementById("username").value + " Password: " + document.getElementById("password").value); // I still dont know how useState works :(
            
        }

    }
  }

  const createUser = async () => {
    try {
        const acc_response = await axios.post("http://localhost:8080/createNewAccount", {
        user_name : document.getElementById("username").value,
        user_pw : document.getElementById("password").value
    })
    console.log(acc_response.data);
    // setUserCredentials({ username: document.getElementById("username").value, password: document.getElementById("password").value });
    return true
    } catch (error) {
        if (error.response) {
            // console.log(error.response.data);
            // console.log(error.response.status);
            // console.log(error.response.headers);
            console.log("This username is already taken. Please try again.")
            let create_h1 = document.getElementById("create_h1");
            create_h1.innerHTML = "This username is already taken. Please try again :(";
        } else if (error.request) {
            /*
             * The request was made but no response was received, `error.request`
             * is an instance of XMLHttpRequest in the browser and an instance
             * of http.ClientRequest in Node.js
             */
            console.log(error.request);
        } else {
            // Something happened in setting up the request and triggered an Error
            console.log('Error', error.message);
        }
        console.log(error);
        return false;
    }
}

    const createGame = () => {
      var create_button = document.getElementById("create_game");

      create_button.onclick = function() {
          r = (Math.random() + 1).toString(36).substring(4,10);
          axios.post("http://localhost:8080/createNewGame", {
              game_code : r // make this randomized
          })
          console.log('Game has been created. Game Code: ' + r);
          // window.location.href = "/game";
          // navigate(`/game/${r}`);
      }
    }

  return(
    <div className="login-bg">
      <div className="container_middle">
      {/* <h1>Please Log In</h1> */}
            <div className="center" id="menu_screen" style={{margin: "auto auto", display: "flex", flexDirection: "column"}}>
                    <button type="button" className="button" id="log_in" onClick={() => handleLogIn()} style={{margin: "auto auto", backgroundColor: "#7e5ef4", padding: "1vh 5.4vh"}}> Log In!</button>
                    <button type="button" className="button" id="sign_up" onClick={() => handleLogIn()}  style={{margin: "1vh auto", padding: "1vh 4.5vh", color:"black"}}> Sign Up!</button>
            </div>
            <div className="center" style={{margin: "10vh auto"}}>
                <form id="login_screen" style={{display: 'none'}}>
                    <h1 id="login_h1" style={{margin: "1vh auto", fontStyle:"italic", display:"none"}}>Log In :)</h1>
                    <h1 id="create_h1" style={{margin: "1vh auto", fontStyle:"italic", display:"none"}}>Create Username :)</h1>
                    <input id="username" className="nickname" type="text" onChange={e => setUserName(e.target.value)} placeholder="Enter username..." />
                    <input  id="password" style={{margin: "1vh auto"}}  className="nickname" type="password" onChange={e => setPassword(e.target.value)} placeholder="Enter password..." />
                    <button type="button" className="button" id="go_back" style={{margin: "-2vh auto", top: "30px", left: "0px", padding: "15px 20px", backgroundColor: "#7e5ef4"}}> Go Back</button>
                    <button type="button" className="button" id="enter" style={{margin: "-2vh auto", top: "30px", padding: "15px 20px", backgroundColor: "#7e5ef4"}}> Enter</button>
                </form>
            </div>
            <div id="join_screen" className="center" style={{margin: "14vh auto", display: "none", flexDirection: "column"}}>
                    <h1 id="welcome_h1">Welcome!</h1>
                    <button className="button" id="create_game" onClick={() => createGame()} style={{margin: "auto auto", top: "1vh", backgroundColor: "#7e5ef4"}}> Create Game</button>
                    <button className="button" id="join_game" style={{margin: "1.5vh auto", top: "1vh"}}> Join Game</button>
            </div>
          </div>
      
      {/* <form onSubmit={handleSubmit}>
        <label>
          <p>Username</p>
          <input type="text" onChange={e => setUserName(e.target.value)} />
        </label>
        <label>
          <p>Password</p>
          <input type="password" onChange={e => setPassword(e.target.value)} />
        </label>
        <div>
          <button type="submit">Submit</button>
        </div>
      </form> */}
    </div>
  )
}

Login.propTypes = {
  setToken: PropTypes.func.isRequired
};