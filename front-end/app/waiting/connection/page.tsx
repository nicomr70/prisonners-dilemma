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
      // Assuming gameService has a method to get the gameId
      const id = gameService.getGameId();
      setGameId(id);
    const interval = setInterval(() => {
      const gameFull = gameService.getGameFull();
      setIsGameFull(gameFull);

      if (isGameFull) {
        clearInterval(interval); // Stop polling
        router.push("/multi"); // Redirect to /multi route
      }
    }, 5000); // Check every 5 seconds

    return () => clearInterval(interval); // Cleanup on unmount
  }, [gameService, router]);
  return (
    <div className="flex flex-col text-center">
      <p className="text-lg text-center my-10">GameId : {gameId}</p>
      <p className="text-xl text-center">Waiting for player 2 to join</p>


    </div>
  );
}
