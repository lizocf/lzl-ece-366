import React, { useState, useEffect, useRef } from 'react';
import { FaPaperPlane } from 'react-icons/fa';
import SockJsClient from 'react-stomp';
import axios from 'axios';

const SOCKET_URL = 'http://localhost:8080/ws-message';
const SEND_ENDPOINT = 'http://localhost:8080/send';

const Chat = ({userToken}) => {
    const [inputMessage, setInputMessage] = useState('');
    const [messages, setMessages] = useState([]);
    const messagesEndRef = useRef(null);
    const [connected, setConnected] = useState(false);

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    useEffect(() => {
        // Reset the connection state when component unmounts
        return () => {

            console.log("SO SOMETHING T");
            setConnected(false);
        };
    }, []);

    const scrollToBottom = () => {
        console.log("SO FrF");
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    const sendMessage = async () => {
        try {
            const userResponse = await axios.get(`http://localhost:8080/getUserToken/${userToken}`);
            console.log("USER NAME: " + userResponse.data.userName);
            const d = userResponse.data.userName + ": ";


            if (inputMessage.trim() !== '' && connected) {

                axios.post(SEND_ENDPOINT, { message: d + inputMessage})
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
        } catch (error) {
            console.error('Error sending message:', error);
        }
    };

    const onMessageReceived = (msg) => {
        console.log("MSG REV");
        setMessages(prevMessages => [...prevMessages, msg.message]);
    };

    const handleOnConnect = () => {
        console.log("SO FFF");
        setConnected(true);
    };

    return (
        <>
            <div className="chat-container" id="chat-container" style={{margin:"2vh auto"}} >
                <SockJsClient
                    url={SOCKET_URL}
                    topics={['/topic/message']}
                    onMessage={onMessageReceived}
                    onConnect={handleOnConnect}
                    // debug={false}
                />




                <div className="messages-container">
                    {messages.map((message, index) => (
                        <div key={index} className="message">{message}</div>
                    ))}
                    <div ref={messagesEndRef} />
                </div>
            </div>

            <div className="input-container">
                <input
                    className='input'
                    type="text"
                    value={inputMessage}
                    onChange={(e) => setInputMessage(e.target.value)}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter') {
                            try {
                                sendMessage();

                            }
                            catch (error) {
                                console.error('InvalidStateError:', error);
                            }

                        }
                    }}
                    placeholder="Type your message here..."
                />
                <button1 onClick={sendMessage} style={{top:"0vh"}}>
                    <FaPaperPlane size={15} color="white" />

                </button1>
            </div>
        </>
    );
};

export default Chat;
