import React from "react";
import { useState, useEffect} from "react";
import axios from "axios";
import "../style.css";
import "./update_position";
import PlayerTable from "./playertable";
import $ from "jquery";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const Chat = () => {
    var stompClient = null;
    //
    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        } else {
            $("#conversation").hide();
        }
        $("#greetings").html("");
    }

    function connect() {
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                showGreeting(JSON.parse(greeting.body).content);
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName() {
        stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    }

    function showGreeting(message) {
        $("#greetings").append("" + message + "");
    }

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $("#connect").click(function () {
            connect();
        });
        $("#disconnect").click(function () {
            disconnect();
        });
        $("#send").click(function () {
            sendName();
        });
    });

    return(
        <>


            {/*<div style={{*/}
            {/*    position: 'fixed',*/}
            {/*    bottom: '200px',*/}
            {/*    left: '0',*/}
            {/*    width: '300px',*/}
            {/*    background: '#f0f0f0',*/}
            {/*    padding: '20px',*/}
            {/*    borderTopRightRadius: '10px',*/}
            {/*    borderTopLeftRadius: '10px'*/}
            {/*}}>*/}
            {/*    <div style={{marginBottom: '10px'}}>*/}
            {/*        <h2 style={{color: "black", margin: '0'}}>Chat goes here</h2>*/}
            {/*        /!* Your chat messages display logic here *!/*/}
            {/*    </div>*/}
            {/*    <div style={{display: 'flex', alignItems: 'center'}}>*/}
            {/*        <input type="text" placeholder="Type your message..." style={{*/}
            {/*            marginRight: '10px',*/}
            {/*            padding: '8px',*/}
            {/*            borderRadius: '5px',*/}
            {/*            border: '1px solid #ccc',*/}
            {/*            flex: '1'*/}
            {/*        }}/>*/}
            {/*        <button style={{*/}
            {/*            padding: '8px 20px',*/}
            {/*            borderRadius: '5px',*/}
            {/*            background: '#007bff',*/}
            {/*            color: 'white',*/}
            {/*            border: 'none'*/}
            {/*        }}>Send*/}
            {/*        </button>*/}
            {/*    </div>*/}
            {/*</div>*/}

            <div style={{
                position: 'fixed',
                bottom: '200px',
                left: '0',
                width: '300px',
                background: 'rgba(255, 255, 255, 0.9)',
                padding: '20px',
                borderTopRightRadius: '10px',
                borderTopLeftRadius: '10px'
            }}>
                <div style={{marginBottom: '20px', minHeight: '200px', maxHeight: '300px', overflowY: 'auto'}}>
                    {/* Message history display */}
                </div>
                <div style={{display: 'flex', alignItems: 'center'}}>
                    <input type="text" placeholder="Type your message..." style={{
                        marginRight: '10px',
                        padding: '8px',
                        borderRadius: '5px',
                        border: '1px solid #ccc',
                        flex: '1'
                    }}/>
                    <button style={{
                        padding: '8px 20px',
                        borderRadius: '5px',
                        background: '#007bff',
                        color: 'white',
                        border: 'none'
                    }}>Send
                    </button>
                </div>
            </div>


        </>
    )

};

export default Chat;



