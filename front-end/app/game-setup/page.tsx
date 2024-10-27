import Link from "next/link";

export default function GameSetup(){
    return(
        <div>
            <h1>entrez le nombre de round :</h1>
            <input className="mb-10 bg-gray-300 ring-1" type="number" />

            <div className="flex flex-col">
                <Link href="/solo" className="bg-slate-400 mb-5 shadow-md">SOLO</Link>
                <Link href="/waiting-connection" className="bg-slate-400 shadow-md">MULTI</Link>
            </div>            
        </div>
    );
}