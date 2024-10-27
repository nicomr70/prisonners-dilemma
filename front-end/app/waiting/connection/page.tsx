'use client'

/*
  in this page we call the API every 5 seconds to check if the player 2 has joined or not

  when the player 2 join we redirect to the /multi route
*/
export default function Page() {

  return (
    <div className="flex flex-col text-center">
      <p className="text-lg text-center my-10">GameId : #####</p>
      <p className="text-xl text-center">Waiting for player 2 to join</p>

      {/* some sort of conditional redirection */}
    </div>
  );
}
