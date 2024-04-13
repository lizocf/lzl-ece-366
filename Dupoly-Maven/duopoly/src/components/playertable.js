import React, { useState, useEffect } from 'react';
import axios from 'axios';

const PlayerTable = () => {
    const [players, setPlayers] = useState([]);
    const [properties, setProperties] = useState([]);

    const loadPlayers = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/1`);
            const filteredPlayers = response.data.filter(player => player !== null).map(player => ({ userName: player.userName, cash: player.cash }));
            setPlayers(filteredPlayers);
            console.log('loadPlayers: Players have been loaded:', filteredPlayers);
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
            // document.getElementById("player_body").remove();
            document.getElementById("buy_yes").onclick = function() {
                handleButtonClick();
                handleButtonClickProp();
            }
        
        } catch (error) {
            console.error('Error fetching players:', error);
        }
    };
    const loadProperties = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getAllOwnedProperties/1/1`); // gameId and userId are hardcoded
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
            const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/1`);
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
        try {
            const fake_response = await axios.get(`http://localhost:8080/getAllOwnedProperties/1/1`); 
            // console.log('Before', fake_response.data);
            const response = await axios.get(`http://localhost:8080/getAllOwnedProperties/1/1`);
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
                        <tbody id="player_body">
                            {/* Table rows will be inserted here */}
                        </tbody>
                    </table>
                </section>
            </main>
            <main className="trans-table" id="transactions">
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
        </main>
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