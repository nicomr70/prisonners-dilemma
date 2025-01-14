'use client';

import Link from "next/link";
import { useRouter } from "next/navigation";
import gateway from "../core/adapters/SingletonGameWebSocket";

export default function Page() {
  const router = useRouter();
  const score = 0;

  const handleAction = (action: 'betray' | 'coop') => {
    if (action === 'betray') {
      gateway.betray();
    } else {
      gateway.coop();
    }
    router.push('/waiting/action');
  };

  return (
    <div className="flex flex-col text-center">
      <p className="text-lg text-center my-10">GameId: {gateway.getGameId()}</p>
      <p className="text-md">Partie Multi</p>
      <p className="text-2xl">Score: {score}</p>
      <div className="flex gap-3 mx-auto my-6">
        <button 
          onClick={() => handleAction('betray')}
          className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
        >
          BETRAY
        </button>
        <button 
          onClick={() => handleAction('coop')}
          className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
        >
          COOP
        </button>
      </div>
    </div>
  );
}