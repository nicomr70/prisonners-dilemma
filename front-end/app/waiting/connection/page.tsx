"use client";

import gateway from "@/app/core/adapters/SingletonGameWebSocket";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function Page() {
  const router = useRouter();
  const [gameId, setGameId] = useState<string>("");
  const [isGameFull, setIsGameFull] = useState<boolean>(false);

  // Initial setup and websocket connection check
  useEffect(() => {
    // Ensure connection is established
    if (!gateway.isSocketOpen()) {
      gateway.connect();
    }
    
    // Create game if not already created
    if (!gateway.getGameId()) {
      try {
        gateway.createGame(10);
      } catch (error) {
        console.error("Error creating game:", error);
      }
    }
  }, []);

  // Poll for game state
  useEffect(() => {
    const interval = setInterval(() => {
      const currentGameId = gateway.getGameId();
      const gameFull = gateway.getGameFull();
      
      setGameId(currentGameId);
      setIsGameFull(gameFull);

      // Check gameFull directly from gateway, not the state
      if (gameFull) {
        clearInterval(interval);
        router.push("/multi");
      }
    }, 1000);

    return () => clearInterval(interval);
  }, [router]);

  return (
    <div className="flex flex-col text-center">
      <p className="text-lg text-center my-10">
        GameId: {gameId || "Creating game..."}
      </p>
      <p className="text-xl text-center">Waiting for player 2 to join</p>
    </div>
  );
}