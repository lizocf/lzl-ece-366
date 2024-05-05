import React from 'react'
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";
import "./update_position";
import propData from "./locations";


const Tiles = ({gameId}) => { 
    const [player, setPlayer] = useState(null);
    const [property, setProperty] = useState(null);
    const [ownedProperty, setOwnedProperty] = useState(null);

    let not_purchaseable = [0,2,4,8,10,16,20,22,26,28,30,32,34,38,40,44,50,52,56,58];

    // get property name from space
    const loadProperty = async (clicked_id) => {
        try {
            console.log('current id: ', clicked_id);
            const name_response = await axios.get(`http://localhost:8080/getNamesByPos/${clicked_id}`);
            setProperty({
                set_name: name_response.data.setName,
                property_name: name_response.data.propertyName,
                num_hotels: name_response.data.numOfHotels
            });
            console.log(`loadProperty: This is ${name_response.data.propertyName} of the ${name_response.data.setName} set.`);
            console.log(propData.costPrice[clicked_id]);

            // check if already owned through /getOwnedProperty
            const prop_response = await axios.get(`http://localhost:8080/getOwnedProperty/${gameId}/${name_response.data.propertyName}`);
            setOwnedProperty({
                user_id: prop_response.data.userId,
                property_name: prop_response.data.propertyName
            });

            var owned_player = prop_response.data.userId;
            if (owned_player === 0) {
                var owned = "Not yet purchased."
            } else {
                const response = await axios.get(`http://localhost:8080/getPlayerInGame/${gameId}/${owned_player}`);
                var owned = response.data.userName;
            }

            
            const tableRows = (
                `<tr>
                    <td>${name_response.data.propertyName}</td>
                    <td>${name_response.data.setName}</td>
                    <td>${name_response.data.numOfHotels}</td>
                </tr>`
            );
            
            const rentRows = (
                `<tr style={{ fontWeight: 'bold' }}>
                    <td>Owner</td>
                    <td>${owned}</td>
                </tr>
                <tr style={{ fontWeight: 'bold' }}>
                    <td>Building Cost</td>
                    <td>${propData.buildingPrice[clicked_id]}</td>
                </tr>
                <tr style={{ fontWeight: 'bold' }}>
                    <td>Rent Cost</td>
                    <td>${propData.rentPrice[clicked_id]}</td>
                </tr>
                <tr style={{ fontWeight: 'bold' }}>
                    <td>Hotel Cost</td>
                    <td>${propData.hotelPrice[clicked_id]}</td>
                </tr>`
            );

            const tableBody = document.getElementById("updated_props");
            tableBody.innerHTML = tableRows;

            const rentBody = document.getElementById("rent");
            rentBody.innerHTML = rentRows;
        // }

        } catch (error) {
            console.error('Error fetching property:', error);
        }
    };

    return (
        <>
        <div className="container_board">
            <table className="styled-table" id="top-east" style={{ top: "26.9%", left: "54.2%", transform: "rotate(28.4deg)" }}>
                <tbody>
                    <tr>
                    <td id='1' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='2' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='3' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='4' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='5' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='6' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='7' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='8' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='9' onClick={(event) => loadProperty(event.target.id)}></td>
                    </tr>
                </tbody>
            </table>
            <table className="styled-table" id="east" style={{ top: "46.8%", left: "72.562%", transform: "rotate(90deg)" }}>
                <tbody>
                    <tr>
                        <td id='11' style={{ width: "3vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='12' style={{ width: "3vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='13' style={{ width: "3vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='14' style={{ width: "3vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='15' style={{ width: "3vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='16' style={{ width: "3vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='17' style={{ width: "3vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='18' style={{ width: "3vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='19' style={{ width: "3vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                    </tr>
                </tbody>
            </table>
            <table className="styled-table" id="bot-east" style={{ height: "9.85vh", top: "67%", left: "53.6%", transform: "rotate(151.6deg)" }}>
                <tbody>
                    <tr>
                        <td id='21' style={{ width: "3.34vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='22' style={{ width: "3.34vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='23' style={{ width: "3.34vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='24' style={{ width: "3.34vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='25' style={{ width: "3.34vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='26' style={{ width: "3.34vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='27' style={{ width: "3.34vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='28' style={{ width: "3.34vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='29' style={{ width: "3.34vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                    </tr>
                </tbody>
            </table>
            <table className="styled-table" id="bot-west" style={{ height: "9.95vh", top: "56.6%", left: "16.42%", transform: "rotate(-151.6deg)" }}>
                <tbody>
                    <tr>
                        <td id='31' style={{ width: "3.38vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='32' style={{ width: "3.38vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='33' style={{ width: "3.38vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='34' style={{ width: "3.38vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='35' style={{ width: "3.38vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='36' style={{ width: "3.38vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='37' style={{ width: "3.38vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='38' style={{ width: "3.38vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='39' style={{ width: "3.38vh" }} onClick={(event) => loadProperty(event.target.id)}></td>

                    </tr>
                </tbody>
            </table>
            <table className="styled-table" id="west" style={{ top: "16.9%", left: "-1.7%", transform: "rotate(-90deg)" }}>
                <tbody>
                    <tr>
                        <td id='41' style={{ width: "3.24vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='42' style={{ width: "3.24vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='43' style={{ width: "3.24vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='44' style={{ width: "3.24vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='45' style={{ width: "3.24vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='46' style={{ width: "3.24vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='47' style={{ width: "3.24vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='48' style={{ width: "3.24vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                        <td id='49' style={{ width: "3.24vh" }} onClick={(event) => loadProperty(event.target.id)}></td>
                    </tr>
                </tbody>
            </table>
            <table className="styled-table" id="top-west"style={{ top: "-23.7%", left: "17%"}}>
                <tbody>
                    <tr>
                    <td id='51' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='52' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='53' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='54' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='55' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='56' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='57' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='58' onClick={(event) => loadProperty(event.target.id)}></td>
                    <td id='59' onClick={(event) => loadProperty(event.target.id)}></td>
                    </tr>
                </tbody>
            </table>
        </div>
        </>
    );
  }


  export default Tiles
