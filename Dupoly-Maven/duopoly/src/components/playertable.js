import React, { useState, useEffect } from 'react';
import axios from 'axios';

const PlayerTable = ({gameCode, userId, gameId}) => {
    const [players, setPlayers] = useState([]);
    const [properties, setProperties] = useState([]);

    console.log("(PlayerTable) Game Code: " + gameCode + " User ID: " + userId + " Game ID: " + gameId);

    useEffect(() => {
        const intervalId = setInterval(() => {
            loadPlayers();
            loadProperties(); // Call loadProperties along with loadPlayers
        }, 5000); // Fetch players and properties every 5 seconds
    
        return () => clearInterval(intervalId); // Cleanup on unmount
    }, [gameCode, userId, gameId]);
    const loadPlayers = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/${gameId}`);
            const filteredPlayers = response.data.filter(player => player !== null).map(player => ({ userName: player.userName, cash: player.cash }));
            setPlayers(filteredPlayers);
            console.log('loadPlayers: Players have been loaded:', filteredPlayers);
            const tableRows = filteredPlayers.map((player, index) => (
                `<tr key=${index}>
                    <td>${player.userName}</td>
                    <td>${player.cash}</td>
                </tr>`
            )).join(""); // Join the array of rows into a single string

            const turnResponse = await axios.get(`http://localhost:8080/getGameTurnOrder/${gameId}`);
            const gameResponse = await axios.get(`http://localhost:8080/getGameInfo/${gameCode}`);


            const tableBody = document.getElementById("player_body");
            const land_div = document.getElementById("land");
            const buy_div = document.getElementById("buy");
            const buttons = document.getElementById("buttons");
            const end_div = document.getElementById("end");
            const roll = document.getElementById("roll_button");
            const waitingDiv = document.getElementById("waiting_div");

            tableBody.innerHTML = tableRows;
            // document.getElementById("player_body").remove();

            const updateTurn = () => {
                axios.post("http://localhost:8080/updateTurnOrder", {game_id: String(gameId)})

                console.log("turnResponse!", gameResponse.data.lastPlayer);
                roll.style.display = "none";
                waitingDiv.style.display = "block";

                if (userId === gameResponse.data.lastPlayer) {
                    // console.log("turnResponse ",gameResponse.data.numTurns + 1)
                    axios.post("http://localhost:8080/updateNumTurns", {
                        num_turns: String(gameResponse.data.numTurns + 1), 
                        game_code: gameCode})
                }

            }

            document.getElementById("buy_yes").onclick = function() {
                handleButtonClick();
                handleButtonClickProp();
                handleButtonClickProp();

                end_div.style.display = "block";
                land_div.style.display = "none";
                buy_div.style.display = "none";
                buttons.style.display = "none";
            }
            document.getElementById("buy_no").onclick = function() {
                end_div.style.display = "block";
                land_div.style.display = "none";
                buy_div.style.display = "none";
                buttons.style.display = "none";
            }

            end_div.onclick = function() {
                handleButtonClick();
                handleButtonClickProp();
                updateTurn();
                end_div.style.display = "none";
                land_div.style.display = "none";
                document.getElementById("broke_h1").style.display = "none";
                document.getElementById("special_prop").style.display='none';
                document.getElementById("land_prop").style.display='block';
                // ADD UPDATE_TURN FUNCTION HERE
                

            }

        } catch (error) {
            console.error('Error fetching players:', error);
        }
    };
    const loadProperties = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getAllOwnedProperties/${gameId}/${userId}`); // gameId and userId are hardcoded
            // console.log('Here!!!', response.data);
            const filteredProperties = response.data.filter(property => property !== null).map(property => ({ 
                property_name: property.propertyName, 
                set_name: property.setName,
                num_hotels: property.numOfHotels
            }));
            
            setProperties(filteredProperties);
            console.log('loadProperties: Owned properties have been loaded:', filteredProperties);
            const tableRows = filteredProperties.map((property, index) => (
                `<tr key=${index}>
                    <td>${property.property_name}</td>
                    <td>${property.set_name}</td>
                    <td>${property.num_hotels}</td>
                </tr>`
            )).join(""); // Join the array of rows into a single string
            // console.log('loadPlayers: Table rows have been loaded:', tableRows)
            // Now you can use tableRows in your HTML content
            const tableBody = document.getElementById("owned_body");
            tableBody.innerHTML = tableRows;
        
        } catch (error) {
            console.error('Error fetching players:', error);
        }
    };

    const handleButtonClick = async () => {
        loadPlayers(); // need to load twice to make update
        try {
            const fake_response = await axios.get(`http://localhost:8080/getAllPlayersInGame/${gameId}`); 
            const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/${gameId}`);
            const filteredPlayers = response.data.filter(player => player !== null).map(player => ({ userName: player.userName, cash: player.cash }));
            setPlayers(filteredPlayers);
            // console.log('Button: Players have been loaded:', filteredPlayers);
            const tableRows = filteredPlayers.map((player, index) => (
                `<tr key=${index}>
                    <td>${player.userName}</td>
                    <td>${player.cash}</td>
                </tr>`
            )).join(""); // Join the array of rows into a single string
            // console.log('loadPlayers: Table rows have been loaded:', tableRows)
            // Now you can use tableRows in your HTML content
            const tableBody = document.getElementById("player_body");
            tableBody.innerHTML = tableRows;
        } catch (error) {
            console.error('Error fetching players:', error);
        }
    };

    const handleButtonClickProp = async () => {
        loadProperties(); // I needed to call getOwnedProperties three times to update the table ?? cant just do loadProperties() x3 either
        let tax_space = ["Tax_4", "Tax_26", "Tax_36", "Tax_56"];
        try {
            const fake_response = await axios.get(`http://localhost:8080/getAllOwnedProperties/${gameId}/${userId}`); 
            // console.log('Before', fake_response.data);
            const response = await axios.get(`http://localhost:8080/getAllOwnedProperties/${gameId}/${userId}`);
            const filteredProperties = response.data.filter(property => property !== null).map(property => ({ 
                property_name: property.propertyName, 
                set_name: property.setName,
                num_hotels: property.numOfHotels
            }));
            
            setProperties(filteredProperties);
            const tableRows = filteredProperties.map((property, index) => (
                `<tr key=${index}>
                    <td>${property.property_name}</td>
                    <td>${property.set_name}</td>
                    <td>${property.num_hotels}</td>
                </tr>`
            )).join("");
            console.log('Updated Owned Properties Table', filteredProperties);
            const tableBody = document.getElementById("owned_body");
            tableBody.innerHTML = tableRows;
        
        } catch (error) {
            console.error('Error fetching properties:', error);
        }
    };

    useEffect(() => {
        loadPlayers();
        loadProperties();
    }, []);

    return (
        <>
            <main className="player-table" id="player_table">
                <section className="table__body">
                    <h1>Players</h1>
                    <table>
                    <thead>
                        <tr>
                            <th>Player</th>
                            <th>Cash</th>
                        </tr>
                    </thead>
                        <tbody id="player_body">
                            {/* Table rows will be inserted here */}
                        </tbody>
                    </table>
                </section>
            </main>
            {/* <main className="trans-table" id="transactions">
            <section className="table__body">
            <h1>Transactions</h1>
                <table>
                    <tbody>
                    <tr>
                        <td>TO</td>
                        <td>BE</td>
                        <td>POPULATED   :)</td>
                    </tr>
                    </tbody>
                </table>
            </section>
        </main> */}
            <main className="owned-table" id="owned_table">
                <section className="table__body">
                    <h1>Owned Properties</h1>
                    <table>
                        <tbody id="owned_body">
                            {/* Table rows will be inserted here */}
                        </tbody>
                    </table>
                </section>
            </main>
        </>
    );
};

export default PlayerTable;