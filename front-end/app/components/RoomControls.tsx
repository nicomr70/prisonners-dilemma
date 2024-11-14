"use client";
export type RoomControls = {
    createRoom: () => void,
    joinRoom: () => void,
    inputRoomId: string,
    setInputRoomId: React.Dispatch<React.SetStateAction<string>>,
    roomId: string,
}

export default function RoomControls({ createRoom, joinRoom, inputRoomId, setInputRoomId, roomId }: RoomControls) {
    return (
        <div className="mb-4">
            <button onClick={createRoom} className="px-4 py-2 bg-green-500 text-white rounded mr-2">
                Create Room
            </button>
            <input
                type="text"
                placeholder="Enter Room ID"
                value={inputRoomId}
                onChange={(e) => setInputRoomId(e.target.value)}
                className="p-2 border border-gray-300 rounded mr-2"
            />
            <button onClick={joinRoom} className="px-4 py-2 bg-blue-500 text-white rounded">
                Join Room
            </button>

            {roomId && (
                <p className="text-lg font-semibold mt-2">
                    Connected to Room ID: {roomId}
                </p>
            )}
        </div>
    );
}
