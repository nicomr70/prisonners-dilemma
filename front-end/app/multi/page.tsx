'use client';

import gateway from "../core/adapters/SingletonGameWebSocket";

export default function Page() {
  const gameService = gateway;
  const score = 0 ;

  return (
    <div className="flex flex-col text-center">
            <p className="text-lg text-center my-10">GameId : {gameService.getGameId()}</p>
            <p className="text-md">Partie Multi</p>
            <p className="text-2xl">Score: {score}</p>
      <div className="flex gap-3 mx-auto my-6">
        <button>BETRAY</button>
        <button>COOP</button>
      </div>
    </div>
  );
}
