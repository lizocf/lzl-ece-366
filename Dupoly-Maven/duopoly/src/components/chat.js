// import React, { useState, useRef, useEffect } from 'react';
// import { FaPaperPlane,FaHeart } from 'react-icons/fa';
// import SockJsClient from 'react-stomp';
//
// const SOCKET_URL = 'http://localhost:8080/ws-message';
// const SEND_ENDPOINT = 'http://localhost:8080/send';
//
//
// const Chat = () => {
//     const [message, setMessage] = useState('You server message here.');
//     const [inputMessage, setInputMessage] = useState('');
//     const [connected, setConnected] = useState(false);
//     const conversationRef = useRef(null);
//     const [greetings, setGreetings] = useState('');
//     const clientRef = useRef(null);
//
//
//     let onConnected = () => {
//         console.log("Connected!!")
//         setConnected(true);
//     }
//
//     let onMessageReceived = (msg) => {
//         setMessage(msg.message);
//         showGreeting(msg.message);
//     }
//
//     const disconnect = () => {
//         // Your code to disconnect from WebSocket server
//         console.log("Disconnected!");
//         setConnected(false); // Update connection status to false
//     };
//
//     const showGreeting = (message) => {
//         setGreetings(prevGreetings => prevGreetings + '<br>' + message);
//     }
//
//     const sendMessage = () => {
//         if (connected) {
//             fetch(SEND_ENDPOINT, {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify({message: inputMessage}),
//             })
//                 .then(response => {
//                     if (response.ok) {
//                         console.log('Message sent successfully');
//                         setInputMessage('');
//                     } else {
//                         console.error('Failed to send message');
//                     }
//                 })
//                 .catch(error => {
//                     console.error('Error sending message:', error);
//                 });
//         } else {
//             console.error('WebSocket connection is not established.');
//         }
//     };
//
//     const setConnectedUI = (connected) => {
//         if (connected) {
//             conversationRef.current.style.display = 'block';
//         } else {
//             conversationRef.current.style.display = 'none';
//         }
//         document.getElementById('greetings').innerHTML = "";
//     };
//
//     return (
//
//         <div>
//             <SockJsClient
//                 url={SOCKET_URL}
//                 topics={['/topic/message']}
//                 onConnect={onConnected}
//                 onDisconnect={disconnect}
//                 onMessage={msg => onMessageReceived(msg)}
//                 debug={false}
//             />
//
//
//             <div id="greetings" className="logs-table" dangerouslySetInnerHTML={{__html: greetings}}></div>
//             <div>
//                 <button style={{margin: "-6vh  190px" ,backgroundColor: 'black', padding: '1px 50px'}}  onClick={sendMessage} className="send-button">
//                     {/*<FaPaperPlane size={20} color="blue"/>*/}
//                     <h1><FaPaperPlane size={26} style={{color: 'white'}}/></h1>
//                 </button>
//             </div>
//
//             <div className="input-container" style={{margin: "1.5vh  10px"}}>
//
//                 <input
//                     type="text"
//                     value={inputMessage}
//                     onChange={(e) => setInputMessage(e.target.value)}
//                     onKeyDown={(e) => {
//                         if (e.key === 'Enter') {
//
//                             if(connected)
//                             {
//                                 sendMessage();
//                             }
//                         }
//                     }}
//                     className="chat-input" style={{margin: "1.8vh auto", height: "50px"}}
//                 />
//
//             </div>
//
//
//             <div className="chat-container" style={{margin: "6vh auto"}}></div>
//         </div>);
// }
//
// export default Chat;
//
// // so I need to do a /send from js

import React, { useState, useEffect, useRef } from 'react';
import { FaPaperPlane } from 'react-icons/fa';
import SockJsClient from 'react-stomp';
import axios from 'axios';

const SOCKET_URL = 'http://localhost:8080/ws-message';
const SEND_ENDPOINT = 'http://localhost:8080/send';

const Chat = () => {
    const [inputMessage, setInputMessage] = useState('');
    const [messages, setMessages] = useState([]);
    const messagesEndRef = useRef(null);
    const [connected, setConnected] = useState(false);

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    const sendMessage = () => {
        if (inputMessage.trim() !== '') {
            axios.post(SEND_ENDPOINT, { message: inputMessage })
                .then(response => {
                    if (response.ok) {
                        setInputMessage('');
                    } else {
                        console.error('Failed to send message');
                    }
                })
                .catch(error => {
                    console.error('Error sending message:', error);
                });
        }
    };

    const onMessageReceived = (msg) => {
        setMessages(prevMessages => [...prevMessages, msg.message]);
    };

    return (
        <>
        <div className="chat-container" id="chat-container" style={{margin:"2vh auto"}} >
            <SockJsClient
                url={SOCKET_URL}
                topics={['/topic/message']}
                onMessage={onMessageReceived}
                debug={false}
            />

            <div className="messages-container">
                {messages.map((message, index) => (
                    <div key={index} className="message">{message}</div>
                ))}
                <div ref={messagesEndRef} />
            </div>
        </div>

        {/* <div className="chat-container" id="input-cont" style={{margin:"2vh auto"}} >     */}
        <div className="input-container">
            <input className='input' 
                type="text"
                value={inputMessage}
                onChange={(e) => setInputMessage(e.target.value)}
                onKeyDown={(e) => {
                    if (e.key === 'Enter') {
                        sendMessage();
                    }
                }}
                placeholder="Type your message here..."
            />
            <button1 onClick={sendMessage} style={{top:"0vh"}}>
                <FaPaperPlane size={15} color="blue" />
            </button1>
            </div>
        {/* </div> */}
        </>
    );
};

export default Chat;
