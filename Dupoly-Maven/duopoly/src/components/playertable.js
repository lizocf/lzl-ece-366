import React from 'react'
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";

const PlayerTable = () => {
    const [players, setPlayers] = useState([]);
    useEffect(() => {
        const loadPlayers = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/getAllPlayersInGame/1`);
                setPlayers(response.data.filter(player => player !== null).map(player => ({ userName: player.userName, cash: player.cash })));
            } catch (error) {
                console.error('Error fetching players:', error);
            }
        };
        loadPlayers();
    }, []);

// insert getUserName/{userId} function to find usernames

// add user icons into table

    return (
      <>
        <main className="player-table" id="player_table">
            <section className="table__body">
            <h1>Players</h1>
                <table>
                    <tbody>
                        {players.map((player, index) => (
                            <tr key={index}>
                            <td>{player.userName}</td>
                            <td>{player.cash}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>
        </main>
    </>
    )
  }


  export default PlayerTable