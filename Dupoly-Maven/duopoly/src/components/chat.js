import React, { useState, useRef, useEffect } from 'react';
import SockJsClient from 'react-stomp';

const SOCKET_URL = 'http://localhost:8080/ws-message';
const SEND_ENDPOINT = 'http://localhost:8080/send';


const Chat = () => {
    const [message, setMessage] = useState('You server message here.');
    const [inputMessage, setInputMessage] = useState('');
    const [connected, setConnected] = useState(false);
    const conversationRef = useRef(null);
    const [greetings, setGreetings] = useState('');
    const clientRef = useRef(null);


    let onConnected = () => {
        console.log("Connected!!")
        setConnected(true);
    }

    let onMessageReceived = (msg) => {
        setMessage(msg.message);
        showGreeting(msg.message);
    }

    const disconnect = () => {
        // Your code to disconnect from WebSocket server
        console.log("Disconnected!");
        setConnected(false); // Update connection status to false
    };

    const showGreeting = (message) => {
        setGreetings(prevGreetings => prevGreetings + '<br>' + message);
    }

    const sendMessage = () => {
        if (connected) {
            fetch(SEND_ENDPOINT, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ message: inputMessage }),
            })
                .then(response => {
                    if (response.ok) {
                        console.log('Message sent successfully');
                        setInputMessage('');
                    } else {
                        console.error('Failed to send message');
                    }
                })
                .catch(error => {
                    console.error('Error sending message:', error);
                });
        } else {
            console.error('WebSocket connection is not established.');
        }
    };

    const setConnectedUI = (connected) => {
        if (connected) {
            conversationRef.current.style.display = 'block';
        } else {
            conversationRef.current.style.display = 'none';
        }
        document.getElementById('greetings').innerHTML = "";
    };

    return (
        <div>
            <SockJsClient
                url={SOCKET_URL}
                topics={['/topic/message']}
                onConnect={onConnected}
                onDisconnect={disconnect}
                onMessage={msg => onMessageReceived(msg)}
                debug={false}
                // ref={clientRef}
            />
            <input
                type="text"
                value={inputMessage}
                onChange={(e) => setInputMessage(e.target.value)}
                onKeyDown={(e) => {
                    if (e.key === 'Enter') {
                        sendMessage();
                    }
                }}
            />
            <button onClick={sendMessage}>Send</button>

            {/*<div>{message}</div>*/}
            {/*<div id="greetings">{greetings}</div>*/}
            {/*<div id="greetings" dangerouslySetInnerHTML={{__html: greetings}}></div>*/}
            <div id="greetings" style={{color: 'white', overflow: "scroll"}} dangerouslySetInnerHTML={{__html: greetings}}></div>

        </div>
    );
}

export default Chat;

// so I need to do a /send from js




