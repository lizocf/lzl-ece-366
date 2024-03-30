import React from 'react'
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";
import "./update_position";


const UpdateDirection = () => {
    function UpdateLeft() {
        var left_button = document.getElementById("left");
        var right_button = document.getElementById("right");
        var roll_div = document.getElementById("roll_div");
        var roll_button = document.getElementById("roll_button");
        var direction_div = document.getElementById("direction_div");
        axios.post("http://localhost:8080/updateDir", {
            user_id : "1",
            game_id : "1",
            direction : "left"
        })
        left_button.style.display='none';
        right_button.style.display='none';
        roll_button.style.display='block';
        roll_div.style.display='block';
        direction_div.style.display='none';
        console.log(`Player has chosen to go left.`);

    }
    
    function UpdateRight() {
        var left_button = document.getElementById("left");
        var right_button = document.getElementById("right");
        var direction_div = document.getElementById("direction_div");
        var roll_div = document.getElementById("roll_div");
        var roll_button = document.getElementById("roll_button");
        axios.post("http://localhost:8080/updateDir", {
            user_id : "1",
            game_id : "1",
            direction : "right"
        })
        left_button.style.display='none';
        right_button.style.display='none';
        roll_button.style.display='block';
        roll_div.style.display='block';
        direction_div.style.display='none';
        console.log(`Player has chosen to go right.`);
    }
    
    return (
      <>
        <div className="center" style={{margin: "5vh auto"}}>
            <div className="arrow" id="left"
                 onClick={() => UpdateLeft()}></div>
            <div className="arrow" id="right" 
                 onClick={() => UpdateRight()} 
                 style={{transform: "rotate(180deg)"}}></div>
        </div>
        <script>
        </script>
    </>
    )
  }


  export default UpdateDirection