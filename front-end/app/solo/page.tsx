"use client";

import React, { useState, useEffect } from 'react';

const WebSocketDemo = () => {
    const [socket, setSocket] = useState<WebSocket | null>(null);
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState<string[]>([]);

    useEffect(() => {
        const ws = new WebSocket('ws://localhost:8080/ws');
        setSocket(ws);

        // Append incoming messages to the messages array
        ws.onmessage = (event) => {
            setMessages((prevMessages) => [...prevMessages, event.data]);
        };

        return () => {
            ws.close();
        };
    }, []);

    const sendMessage = () => {
        if (socket && socket.readyState === WebSocket.OPEN) {
            socket.send(message);
            setMessage('');
        } else {
            console.error('WebSocket is not open');
        }
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen p-4 bg-gray-100">
            <h1 className="text-2xl font-bold mb-4">WebSocket Demo</h1>
            <input
                type="text"
                className="p-2 border border-gray-300 rounded mb-2"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Type a message"
            />
            <button
                onClick={sendMessage}
                className="px-4 py-2 bg-blue-500 text-white rounded"
            >
                Send
            </button>

            {/* Display message history */}
            <div className="mt-4 w-full max-w-md">
                <h2 className="text-lg font-semibold mb-2">Messages:</h2>
                <ul className="space-y-2">
                    {messages.map((msg, index) => (
                        <li key={index} className="p-2 bg-white rounded shadow">
                            {msg}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default WebSocketDemo;
