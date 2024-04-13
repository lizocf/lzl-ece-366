import React, { useState, useEffect } from 'react';
import axios from 'axios';

const PlayerTable = () => {
    const [players, setPlayers] = useState([]);

    const loadPlayers = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/1`);
            const filteredPlayers = response.data.filter(player => player !== null).map(player => ({ userName: player.userName, cash: player.cash }));
            setPlayers(filteredPlayers);
            console.log('loadPlayers: Players have been loaded:', response.data);
            const tableRows = filteredPlayers.map((player, index) => (
                `<tr key=${index}>
                    <td>${player.userName}</td>
                    <td>${player.cash}</td>
                </tr>`
            )).join(""); // Join the array of rows into a single string
            console.log('loadPlayers: Table rows have been loaded:', tableRows)
            // Now you can use tableRows in your HTML content
            const tableBody = document.getElementById("player_body");
            tableBody.innerHTML = tableRows;
            // document.getElementById("player_body").remove();
            document.getElementById("buy_yes").onclick = function() {
                handleButtonClick();
            }
        
        } catch (error) {
            console.error('Error fetching players:', error);
        }
    };

    const handleButtonClick = async () => {
        loadPlayers();
        try {
            const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/1`);
            const filteredPlayers = response.data.filter(player => player !== null).map(player => ({ userName: player.userName, cash: player.cash }));
            setPlayers(filteredPlayers);
            console.log('Button: Players have been loaded:', filteredPlayers);
            const tableRows = filteredPlayers.map((player, index) => (
                `<tr key=${index}>
                    <td>${player.userName}</td>
                    <td>${player.cash}</td>
                </tr>`
            )).join(""); // Join the array of rows into a single string
            console.log('loadPlayers: Table rows have been loaded:', tableRows)
            // Now you can use tableRows in your HTML content
            const tableBody = document.getElementById("player_body");
            tableBody.innerHTML = tableRows;
        } catch (error) {
            console.error('Error fetching players:', error);
        }
    };


    useEffect(() => {
        loadPlayers();
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
        </>
    );
};

export default PlayerTable;