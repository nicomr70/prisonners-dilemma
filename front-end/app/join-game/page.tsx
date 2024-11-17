"use client";

import { useState } from "react";
import gateway from "../core/adapters/SingletonGameWebSocket";
import Link from "next/link";

export default function JoinGame(){
    const gameService = gateway ;
    const [gameId, setGameId] = useState<string>("");
    return (
        <div>
            <input type="text" value={gameId} onChange={e => setGameId(e.target.value)} />
            <div className="w-full" onClick={()=>gameService.joinGame(gameId)}>
                <Link href="/multi">JOIN</Link>
            </div>
        </div>
    );
}