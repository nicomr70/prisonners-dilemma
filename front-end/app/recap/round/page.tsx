'use client'

/*
  in this page we call the API every 5 seconds to check if the player 2 has joined or not

  when the player 2 join we redirect to the /multi route
*/
export default function Page() {

  return (
    <div className="flex flex-col text-center">
      <p className="text-lg text-center my-10">GameId : #####</p>
      <p className="text-xl text-center">You choosed to BETRAY</p>

      <p className="text-xl text-center">You Gained 5 points</p>

      <p className="text-xl text-center">Current Game Score is :</p>

      <p className="text-xl text-center">12-16</p>

      {/* some sort of conditional redirection */}
    </div>
  );
}
