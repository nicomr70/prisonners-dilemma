'use client'

import gateway from "@/app/core/adapters/SingletonGameWebSocket";
import { Action } from "@/app/core/models/Game";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function Page() {
  const router = useRouter();
  const [playerOne] = useState(gateway.getIsPlayerOne());
  const [tSummary, setTurnSummary] = useState<{
    playerOneAction: Action | null,
    playerTwoAction: Action | null
  }>({
    playerOneAction: null,
    playerTwoAction: null
  });
  const [opponentPlayed, setOpponentPlayed] = useState(false);
  const [opponentChoice, setOpponentChoice] = useState<Action | null>(null);
  
  useEffect(() => {
    if (!gateway.isSocketOpen()) {
      gateway.connect();
    }
  }, []);

  useEffect(() => {
    const checkTurnSummary = () => {
      const summary = gateway.getTurnSummary();
      
      setTurnSummary(summary);
      
      const hasPlayed = playerOne ? summary.playerTwoAction !== null : summary.playerOneAction !== null;
      const choice = playerOne ? summary.playerTwoAction : summary.playerOneAction;
      
      setOpponentPlayed(hasPlayed);
      setOpponentChoice(choice);

      if(summary.playerOneAction !== null && summary.playerTwoAction !== null) {
        router.push('/multi');
      }
    };

    // Initial check
    checkTurnSummary();

    // Set up polling
    const interval = setInterval(checkTurnSummary, 1000);

    return () => clearInterval(interval);
  }, [playerOne, router]); // Remove tSummary from dependencies

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