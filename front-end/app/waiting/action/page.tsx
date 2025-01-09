'use client'

import gateway from "@/app/core/adapters/SingletonGameWebSocket";
import { Action } from "@/app/core/models/Game";
import { useEffect, useState } from "react";

/*
  check if we are the first player 
    then check if the player 2 has played or not
  else 
    then check if player 1 has played or not
*/
export default function Page() {
  const [playerOne] = useState(gateway.getIsPlayerOne());
  const [tSummary, setTurnSummary] = useState<{
    playerOneAction: Action | null,
    playerTwoAction: Action | null
  }>({
    playerOneAction: null,
    playerTwoAction: null
  });
  
  useEffect(() => {
    const interval = setInterval(() => {
      const turnSummary = gateway.getTurnSummary();
      setTurnSummary(turnSummary);
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  const hasOpponentPlayed = playerOne ? tSummary.playerTwoAction != null : tSummary.playerOneAction != null;
  const opponentChoice = playerOne ? tSummary.playerTwoAction : tSummary.playerOneAction;

  return (
    <div className="text-center p-4">{
      !hasOpponentPlayed ? (
      <h1 className="text-xl font-bold mb-4">Waiting for opponent action</h1>

    ) : ( 
      <h1 className="text-xl font-bold mb-4">Opponent has played</h1>
      )
    }
      {hasOpponentPlayed ? (
        <div>
          <p className="mb-2">Opponent has played</p>
          <p>Opponent choice: {opponentChoice}</p>
        </div>
      ) : (
        <p>Opponent has not played yet</p>
      )}
    </div>
  );
}