'use client'

import { useState } from "react";



export default function Home() {

  const [data,setData] = useState<number>(1);

  const incrementCounter = () => setData(data+1); 

  return (
    <div>

      <h1 className="text-2xl">Score</h1>
      <pre>
        Player 1 : {data}
        <br />
        Player 2 : {data}  
      </pre>
      <h1 className="text-xl">Player 1</h1>
      <button className="p-1 bg-red-400 m-1" onClick={incrementCounter}>Trahir</button>
      <button className="p-1 bg-slate-400">Coop</button>

      <br />

      <h1 className="text-xl">Player 2</h1>
      <button className="p-1 bg-red-400 m-1">Trahir</button>
      <button className="p-1 bg-slate-400">Coop</button>

    </div>
  );
}
