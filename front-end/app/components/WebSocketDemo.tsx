"use client";
import React, { useState, useEffect } from 'react';
import MessageList from './MessageLIst';
import RoomControls from './RoomControls';
import { MessageInput } from './MessageInput';


export default function WebSocketDemo() {
    const [socket, setSocket] = useState<WebSocket | null>(null);
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState<string[]>([]);
    const [roomId, setRoomId] = useState<string>('');
    const [inputRoomId, setInputRoomId] = useState('');

    useEffect(() => {
        initializeWebSocket();
        return () => closeWebSocket();
    }, []);

    // Initialize WebSocket connection
    const initializeWebSocket = () => {
        const ws = new WebSocket('ws://localhost:8080/ws');
        setSocket(ws);
        ws.onmessage = handleIncomingPayload;
    };

    // Close WebSocket connection
    const closeWebSocket = () => {
        socket?.close();
    };

    // Handle incoming WebSocket messages
    const handleIncomingPayload = (event: MessageEvent) => {
        const data = event.data;

        if (data.startsWith("GAME_ID:")) {
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
            socket?.send("CREATE_GAME");
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
        if (isSocketOpen() && (roomId != '')) {
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
