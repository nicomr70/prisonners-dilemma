"use client";

import React, { useState, useEffect } from 'react';

const WebSocketDemo = () => {
    const [socket, setSocket] = useState<WebSocket | null>(null);
    const [message, setMessage] = useState('');
    const [receivedMessage, setReceivedMessage] = useState('');

    useEffect(() => {
        // Establish WebSocket connection on component mount
        const ws = new WebSocket('ws://localhost:8080/ws');

        // Set WebSocket instance in state
        setSocket(ws);

        // Handle incoming messages
        ws.onmessage = (event) => {
            setReceivedMessage(event.data);
        };

        // Clean up WebSocket connection on unmount
        return () => {
            ws.close();
        };
    }, []);

    const sendMessage = () => {
        if (socket && socket.readyState === WebSocket.OPEN) {
            socket.send(message);
            setMessage(''); // Clear input field after sending
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
            {receivedMessage && (
                <p className="mt-4 text-lg font-semibold">
                    Received: {receivedMessage}
                </p>
            )}
        </div>
    );
};

export default WebSocketDemo;
