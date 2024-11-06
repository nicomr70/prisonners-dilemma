"use client";

import React, { useState, useEffect } from 'react';

// WebSocketDemo Component
const WebSocketDemo = () => {
    const [socket, setSocket] = useState<WebSocket | null>(null);
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState<string[]>([]);
    const [roomId, setRoomId] = useState<string | null>(null);
    const [inputRoomId, setInputRoomId] = useState('');

    useEffect(() => {
        initializeWebSocket();
        return () => closeWebSocket();
    }, []);

    // Initialize WebSocket connection
    const initializeWebSocket = () => {
        const ws = new WebSocket('ws://localhost:8080/ws');
        setSocket(ws);
        ws.onmessage = handleMessage;
    };

    // Close WebSocket connection
    const closeWebSocket = () => {
        socket?.close();
    };

    // Handle incoming WebSocket messages
    const handleMessage = (event: MessageEvent) => {
        const data = event.data;

        if (data.startsWith("ROOM_CREATED:")) {
            handleRoomCreated(data);
        } else if (data.startsWith("JOINED_ROOM:")) {
            handleRoomJoined(data);
        } else if (data.startsWith("MESSAGE:")) {
            handleNewMessage(data);
        } else if (data.startsWith("ERROR:")) {
            handleError(data);
        }
    };

    // Room creation logic
    const createRoom = () => {
        if (isSocketOpen()) {
            socket?.send("CREATE_ROOM");
        }
    };

    // Join room logic
    const joinRoom = () => {
        if (isSocketOpen() && inputRoomId) {
            socket?.send(`JOIN_ROOM:${inputRoomId}`);
        }
    };

    // Send message to current room
    const sendMessage = () => {
        if (isSocketOpen() && roomId) {
            socket?.send(`MESSAGE:${roomId}:${message}`);
            setMessage('');
        } else {
            console.error('WebSocket is not open or room ID is missing');
        }
    };

    // Check if WebSocket is open
    const isSocketOpen = () => {
        return socket && socket.readyState === WebSocket.OPEN;
    };

    // Handle specific message types
    const handleRoomCreated = (data: string) => {
        const id = data.split(":")[1];
        setRoomId(id);
    };

    const handleRoomJoined = (data: string) => {
        const id = data.split(":")[1];
        setRoomId(id);
    };

    const handleNewMessage = (data: string) => {
        const roomMessage = data.split(":")[1];
        setMessages((prevMessages) => [...prevMessages, roomMessage]);
    };

    const handleError = (data: string) => {
        alert(data.split(":")[1]);
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen p-4 bg-gray-100">
            <h1 className="text-2xl font-bold mb-4">WebSocket Room Demo</h1>

            <RoomControls
                createRoom={createRoom}
                joinRoom={joinRoom}
                inputRoomId={inputRoomId}
                setInputRoomId={setInputRoomId}
                roomId={roomId}
            />

            <MessageInput
                message={message}
                setMessage={setMessage}
                sendMessage={sendMessage}
            />

            <MessageList messages={messages} />
        </div>
    );
};

// RoomControls Component for creating/joining rooms
const RoomControls = ({ createRoom, joinRoom, inputRoomId, setInputRoomId, roomId }: any) => (
    <div className="mb-4">
        <button onClick={createRoom} className="px-4 py-2 bg-green-500 text-white rounded mr-2">
            Create Room
        </button>
        <input
            type="text"
            placeholder="Enter Room ID"
            value={inputRoomId}
            onChange={(e) => setInputRoomId(e.target.value)}
            className="p-2 border border-gray-300 rounded mr-2"
        />
        <button onClick={joinRoom} className="px-4 py-2 bg-blue-500 text-white rounded">
            Join Room
        </button>

        {roomId && (
            <p className="text-lg font-semibold mt-2">
                Connected to Room ID: {roomId}
            </p>
        )}
    </div>
);

// MessageInput Component for sending messages
const MessageInput = ({ message, setMessage, sendMessage }: any) => (
    <div className="flex flex-col items-center">
        <input
            type="text"
            className="p-2 border border-gray-300 rounded mb-2"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            placeholder="Type a message"
        />
        <button onClick={sendMessage} className="px-4 py-2 bg-blue-500 text-white rounded">
            Send
        </button>
    </div>
);

// MessageList Component for displaying messages
const MessageList = ({ messages }: any) => (
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
);

export default WebSocketDemo;
