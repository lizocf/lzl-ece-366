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
    console.log("Username: " + document.getElementById("username").value + " Password: " + document.getElementById("password").value);
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
    console.log("(Login) "+ username, token.token);
   } catch (error) {
      console.log(error);
    }

  }

  const createUser = async () => {   
    try {
        var create_h1 = document.getElementById("create_h1");
        const acc_response = await axios.post("http://localhost:8080/createNewAccount", {
        user_name : document.getElementById("username").value,
        user_pw : document.getElementById("password").value
    })
    console.log('(createUser): ', acc_response.data);
    return true
    } catch (error) {
        if (error.response) {
            console.log("This username is already taken. Please try again.")
            create_h1.innerHTML = "This username is already taken. Please try again :(";
        } else if (error.request) {
            console.log(error.request);
        } else {
            console.log('Error', error.message);
        }
        console.log(error);
        return false;
    }
}

const logUser = async () => {   
  try {
    const username = document.getElementById("username").value;
    let password = document.getElementById("password").value; 
    const login_h1 = document.getElementById("login_h1"); 

    // Treat empty password as null
    // password = password === "" ? null : password;

    // Fetch user data from the server
    const log_response = await axios.get(`http://localhost:8080/getUserName/${username}`);
    const userData = log_response.data;

    // Check if the username exists
    if (!userData || !userData.userName) {
        login_h1.innerHTML = "This username does not exist. :(";
        console.log("This username does not exist. Please try again");
        return false;
    }

    // Check password correctness
    if (password === userData.userPW) {
        return true;
    } else {
        login_h1.innerHTML = "Incorrect password! Please try again :(";
        console.log("Incorrect password! Expected:", userData.userPW, "Got:", password);
        return false;
    }
} catch (error) {
    console.error('logUser: Error during login process:', error);
    return false;
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

        enter.onclick = function() {
          console.log("Login Account: " + document.getElementById("username").value + " Password: " + document.getElementById("password").value); // I still dont know how useState works :(
          logUser().then((createCheck) => {
            if (createCheck) {
                login_screen.style.display = "none";
                handleSubmit();
            } else {
                console.log("Error creating account");
            }
        });
      }


    }

    sign_up_button.onclick = function() {
        login_screen.style.display = "block";
        menu_screen.style.display = "none";
        login_h1.style.display = "none";
        create_h1.style.display = "block";

        enter.onclick = function() {
            console.log("Created Account: " + document.getElementById("username").value + " Password: " + document.getElementById("password").value); // I still dont know how useState works :(
            createUser().then((createCheck) => {
              if (createCheck) {
                  login_screen.style.display = "none";
                  handleSubmit();
              } else {
                  console.log("Error creating account");
              }
          });
        }

    }
  }


  return(
    <div className="login-bg">
      <div className="container_middle">
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
          </div>
    </div>
  )
}

Login.propTypes = {
  setToken: PropTypes.func.isRequired
};