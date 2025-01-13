'use client'

import gateway from "@/app/core/adapters/SingletonGameWebSocket";
import { Action } from "@/app/core/models/Game";
import { useEffect, useState } from "react";

export default function Page() {
  const [playerOne] = useState(gateway.getIsPlayerOne());
  const [tSummary, setTurnSummary] = useState<{
    playerOneAction: Action | null,
    playerTwoAction: Action | null
  }>({
    playerOneAction: null,
    playerTwoAction: null
  });
  
  // Ensure WebSocket connection
  useEffect(() => {
    if (!gateway.isSocketOpen()) {
      gateway.connect();
    }
    
    return () => {
      // No need to disconnect as it's a singleton
    };
  }, []);

  // Poll for turn summary
  useEffect(() => {
    const interval = setInterval(() => {
      const turnSummary = gateway.getTurnSummary();
      console.log('Current turn summary:', turnSummary);
      console.log('Is player one:', playerOne);
      setTurnSummary(turnSummary);
    }, 1000);

    return () => clearInterval(interval);
  }, [playerOne]);

  const hasOpponentPlayed = playerOne ? tSummary.playerTwoAction != null : tSummary.playerOneAction != null;
  const opponentChoice = playerOne ? tSummary.playerTwoAction : tSummary.playerOneAction;

  console.log('Render state:', {
    playerOne,
    hasOpponentPlayed,
    opponentChoice,
    tSummary
  });

  return (
    <div className="text-center p-4">
      <h1 className="text-xl font-bold mb-4">
        {!hasOpponentPlayed ? "Waiting for opponent action" : "Opponent has played"}
      </h1>
      
      {hasOpponentPlayed ? (
        <div>
          <p className="mb-2">Opponent has played</p>
          <p>Opponent choice: {opponentChoice}</p>
        </div>
      ) : (
        <div>
          <p>Opponent has not played yet</p>
          <p className="text-sm text-gray-500 mt-2">
            You are Player {playerOne ? "1" : "2"}
          </p>
        </div>
      )}
    </div>
  );
}