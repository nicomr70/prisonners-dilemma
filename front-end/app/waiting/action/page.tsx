'use client'

import gateway from "@/app/core/adapters/SingletonGameWebSocket";
import { Action, TurnSummary } from "@/app/core/models/Game";
import { useEffect, useState } from "react";

/*
  check if we are the first player 
    then check if the player 2 has played or not
  else 
    then check if player 1 has played or not
*/
export default function Page() {
  const gameService = gateway;
  const [playerOne] = useState(gateway.getIsPlayerOne());
  const [tSummary, setTurnSummary] = useState<TurnSummary>({playerOneAction: Action.BETRAY, playerTwoAction: Action.BETRAY});
  
  const display = () => {
    if (playerOne) {
      if (tSummary.playerTwoAction) {
        return (
          <><p>Opponent has played</p><br /><p>Opponent choice: {tSummary.playerTwoAction}</p></>
        );
      }else{
        return <p>Opponent has not played yet</p>;}
    } else {
      if (tSummary.playerOneAction) {
        return (
          <><p>Opponent has played</p><br /><p>Opponent choice: {tSummary.playerOneAction}</p></>
        );
      }else{
        return <p>Opponent has not played yet</p>;
      }
    }
  };

  useEffect(() => {
    const interval = setInterval(() => {
      const turnSummary = gameService.getTurnSummary();
      setTurnSummary(turnSummary);
    }, 5000);
    console.log(tSummary);
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="text-center">
      <h1>Waiting for opponent action</h1>
        {display()}
    </div>
  );
}
