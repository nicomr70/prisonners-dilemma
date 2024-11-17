"use client";
import Link from "next/link";
import gateway from "../core/adapters/SingletonGameWebSocket";
import { useState } from "react";

export default function GameSetup() {
    const gameService = gateway;

    const [turnsNumber, setTurnsNumber]= useState<number>(1);
    const handleTurnsChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setTurnsNumber(Number(e.target.value)); // Convert the string to a number
    };

    const createGame = () =>  gameService.createGame(turnsNumber);

    return (
        <div>
            <h1>entrez le nombre de round :</h1>
            <input className="mb-10 bg-gray-300 ring-1" type="number" value={turnsNumber} onChange={handleTurnsChange}/>

            <div className="flex flex-col text-center">
                <Link href="/solo" className="bg-slate-400 mb-5 shadow-md">SOLO</Link>
                <div className="w-full" onClick={createGame}>
                    <Link href="/waiting/connection" className="bg-slate-400 mb-5 shadow-md">MULTI</Link>
                </div>
                 
                <br />
                <br />
                <Link href="/" className="bg-slate-400 shadow-md">HOME</Link>
            </div>
        </div>
    );

    
}