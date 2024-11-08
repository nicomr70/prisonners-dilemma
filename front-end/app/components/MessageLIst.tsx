"use client";

export type MessageListProps = {
    messages: string[];
};

// MessageList Component for displaying messages
export default function MessageList({ messages }: MessageListProps) {
    return (
        <div className="mt-4 w-full max-w-md">
            <h2 className="text-lg font-semibold mb-2">Messages:</h2>
            <ul className="space-y-2">
                {messages.map((msg, index) => (
                    <li key={index} className="p-2 bg-white rounded shadow">
                        {msg}
                    </li>
                ))}
            </ul>
        </div>
    );

}
