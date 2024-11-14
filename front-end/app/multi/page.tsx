'use client'

import Link from "next/link";
import { useState } from "react";

export default function Page() {

  const score = 0 ;

  return (
    <div className="flex flex-col text-center">
            <p className="text-lg text-center my-10">GameId : #####</p>
            <p className="text-md">Partie Multi</p>
            <p className="text-2xl">Score: {score}</p>
      <div className="flex gap-3 mx-auto my-6">
        <button>BETRAY</button>
        <button>COOP</button>
      </div>
    </div>
  );
}
