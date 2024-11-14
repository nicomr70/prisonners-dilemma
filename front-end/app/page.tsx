'use client';

import Link from "next/link";

export default function Page() {

  return (
    <div className="flex flex-col my-[25%] text-center">
      <Link href="/" className="bg-gray-400 mb-5 shadow-md">rejoindre partie</Link>
      <Link href="/game-setup" className="bg-gray-400 shadow-md">Créer partie</Link>
    </div>
  );
}
