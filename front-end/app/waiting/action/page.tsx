'use client'

import gateway from "@/app/core/adapters/SingletonGameWebSocket";
import { Action } from "@/app/core/models/Game";
import { useEffect, useState } from "react";

export default function Page() {
  const [playerOne, setIsPlayerOne] = useState(gateway.getIsPlayerOne());
  const [tSummary, setTurnSummary] = useState<{
    playerOneAction: Action | null,
    playerTwoAction: Action | null
  }>({
    playerOneAction: null,
    playerTwoAction: null
  });
  const [opponentPlayed, setOpponentPlayed] = useState(false);
  const [opponentChoice, setOpponentChoice] = useState<Action | null>(null);
  
  // Ensure WebSocket connection
  useEffect(() => {
    if (!gateway.isSocketOpen()) {
      gateway.connect();
    }
  }, []);

  // Poll for turn summary
  useEffect(() => {
    setIsPlayerOne(gateway.getIsPlayerOne());    
    const checkTurnSummary = () => {
      const turnSummary = gateway.getTurnSummary();      
      // Only update if there's an actual change
      if (JSON.stringify(turnSummary) !== JSON.stringify(tSummary)) {
        setTurnSummary(turnSummary);
        
        // Update opponent played status and choice
        const hasPlayed = playerOne ? turnSummary.playerTwoAction !== null : turnSummary.playerOneAction !== null;
        const choice = playerOne ? turnSummary.playerTwoAction : turnSummary.playerOneAction;
        
        setOpponentPlayed(hasPlayed);
        setOpponentChoice(choice);
        console.log('Turn summary:', turnSummary);
        console.log('Current player:', playerOne ? 'Player 1' : 'Player 2');
        console.log('Opponent choice:', choice);
      }
    };

    // Check immediately
    checkTurnSummary();

    // Then set up polling
    const interval = setInterval(checkTurnSummary, 500);

    return () => clearInterval(interval);
  }, [ playerOne, tSummary ]);

  console.log('Render state:', {
    playerOne,
    opponentPlayed,
    opponentChoice,
    tSummary
  });

  return (
    <div className="text-center p-4">
      <div className="mb-4 text-sm text-gray-600">
        You are: {playerOne ? 'Player 1' : 'Player 2'}
      </div>
      
      <h1 className="text-xl font-bold mb-4">
        {!opponentPlayed ? "Waiting for opponent action" : "Opponent has played"}
      </h1>
      
      {opponentPlayed ? (
        <div>
          <p className="mb-2">Opponent has played</p>
          <p>Opponent choice: {opponentChoice}</p>
        </div>
      ) : (
        <p>Opponent has not played yet</p>
      )}

      <div className="mt-4 text-sm text-gray-500">
        Game ID: {gateway.getGameId()}
      </div>
    </div>
  );
}