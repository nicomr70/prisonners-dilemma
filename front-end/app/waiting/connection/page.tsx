"use client";

import gateway from "@/app/core/adapters/SingletonGameWebSocket";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

/*
  in this page we call the API every 5 seconds to check if the player 2 has joined or not

  when the player 2 join we redirect to the /multi route
*/
export default function Page() {
  const gameService = gateway;
  const router = useRouter();
  const [gameId, setGameId] = useState<string | null>(null);
  const [isGameFull, setIsGameFull] = useState<boolean>(false);

  useEffect(() => {
    const gameId = gameService.getGameId();
    setGameId(gameId);
  });

  useEffect(() => {
    const interval = setInterval(() => {
      const gameFull = gameService.getGameFull();
      setIsGameFull(gameFull);

      if (isGameFull) {
        clearInterval(interval); // Stop polling
        router.push("/multi"); // Redirect to /multi route
      }
    }, 1000);

    return () => clearInterval(interval); // Cleanup on unmount
  });
  return (
    <div className="flex flex-col text-center">
      <p className="text-lg text-center my-10">GameId : {gameId}</p>
      <p className="text-xl text-center">Waiting for player 2 to join</p>


    </div>
  );
}
