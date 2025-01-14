import React, { useState } from "react";
import Link from "next/link";
import { GameController } from "lucide-react";
import gateway from "../core/adapters/SingletonGameWebSocket";

const JoinGame = () => {
  const [gameId, setGameId] = useState("");
  const gameService = gateway;

  const handleJoin = () => {
    if (gameId.trim()) {
      gameService.joinGame(gameId);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <div className="w-full max-w-md bg-white rounded-lg shadow-lg p-8">
        <div className="space-y-6">
          {/* Header */}
          <div className="text-center space-y-4">
            <div className="flex justify-center">
              <GameController className="h-12 w-12 text-blue-600" />
            </div>
            <h1 className="text-2xl font-bold text-gray-900">Join Game</h1>
          </div>

          {/* Input */}
          <div className="space-y-4">
            <input
              type="text"
              placeholder="Enter Game ID"
              value={gameId}
              onChange={(e) => setGameId(e.target.value)}
              className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all duration-200"
            />

            {/* Join Button */}
            <Link href="/multi" className="block w-full">
              <button
                onClick={handleJoin}
                disabled={!gameId.trim()}
                className={`w-full py-3 px-4 rounded-lg font-medium text-white transition-all duration-200
                  ${gameId.trim() 
                    ? 'bg-blue-600 hover:bg-blue-700 active:bg-blue-800' 
                    : 'bg-gray-400 cursor-not-allowed'}`}
              >
                Join Game
              </button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default JoinGame;