"use client";

export type MessageInputType = {
    message: string,
    setMessage: React.Dispatch<React.SetStateAction<string>>;
    sendMessage: () => void
};

// MessageInput Component for sending messages
export const MessageInput = ({ message, setMessage, sendMessage }: MessageInputType) => (
    <div className="flex flex-col items-center">
        <input
            type="text"
            className="p-2 border border-gray-300 rounded mb-2"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            placeholder="Type a message"
        />
        <button onClick={sendMessage} className="px-4 py-2 bg-blue-500 text-white rounded">
            Send
        </button>
    </div>
);