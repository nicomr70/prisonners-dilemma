"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import gateway from "../core/adapters/SingletonGameWebSocket";
import { useEffect, useState } from "react";

export default function Page() {
    const router = useRouter();
    const [playerOneScore, setPlayerOneScore] = useState<number>(0);
    const [opponentScore, setOpponentScore] = useState<number>(0);
    const isPlayerOne = gateway.getIsPlayerOne();
    useEffect(() => {
        // Reset turn summary when mounting the multi page
        gateway.resetTurnSummary();
    }, []);

    useEffect(() => {
      const gameGateway = gateway;
      const checkGameStatus = () => {
          if (gameGateway.isGameEnded()) {
              const scores = gameGateway.getFinalScores();
              const isPlayerOne = gameGateway.getIsPlayerOne();
              const yourScore = isPlayerOne ? scores!.playerOne : scores!.playerTwo;             
              const opponentScore = isPlayerOne ? scores!.playerTwo : scores!.playerOne;
              setPlayerOneScore(yourScore);
              setOpponentScore(opponentScore);
              console.log(`Game ended! Your score: ${yourScore}, Opponent score: ${opponentScore}`);
          }
      };

      if(gateway.isGameEnded()){
        router.push("/recap/game");
      }
      
      // Check status periodically
      const interval = setInterval(checkGameStatus, 1000);

      return () => clearInterval(interval);
    }, []);

    const handleAction = (action: 'betray' | 'coop') => {
        if (action === 'betray') {
            gateway.betray();
        } else {
            gateway.coop();
        }
        router.push('/waiting/action');
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-4">
            <div className="w-full max-w-md text-center">
                <p className="text-lg text-gray-700 mb-4">Game ID: {gateway.getGameId()}</p>
                <p className="text-md text-gray-600 mb-2">Partie Multi</p>
                <p className="text-2xl font-bold text-gray-800 mb-6">Score: {isPlayerOne ? playerOneScore : opponentScore}</p>
                <div className="flex justify-center gap-4 mb-8">
                    <button
                        onClick={() => handleAction('betray')}
                        className="px-6 py-3 bg-red-500 text-white rounded-lg shadow-lg hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-400 focus:ring-offset-2"
                    >
                        BETRAY
                    </button>
                    <button
                        onClick={() => handleAction('coop')}
                        className="px-6 py-3 bg-green-500 text-white rounded-lg shadow-lg hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-400 focus:ring-offset-2"
                    >
                        COOP
                    </button>
                </div>
                <Link
                    href="/"
                    className="inline-block px-4 py-2 text-white bg-blue-500 rounded-lg shadow-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-offset-2"
                >
                    Return to Home
                </Link>
            </div>
        </div>
    );
}
