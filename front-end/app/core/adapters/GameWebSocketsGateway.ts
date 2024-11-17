import { Action, createGameStartingConvention } from "../models/Game";
import { IGameGateway } from "../ports/IGameGateway";

export class GameWebSocketsGateway implements IGameGateway {
    private socket: WebSocket | null = null;
    private gameId: string = "";
    private gameFull: boolean = false;
    
    constructor(private serverUrl: string) {}

    public getGameId(): string {
        return this.gameId;
    }

    public getGameFull(): boolean {
        return this.gameFull;
    }

    /**
     * Connects the player to the WebSocket server.
     */
    public connect(): void {
        if (this.socket) {
            console.warn("WebSocket is already connected.");
            return;
        }

        this.socket = new WebSocket(this.serverUrl);

        this.socket.onopen = () => {
            console.log("WebSocket connection established.");
        };

        this.socket.onmessage = (event) => {
            this.handleMessage(event.data);
        };

        this.socket.onerror = (error) => {
            console.error("WebSocket error:", error);
        };

        this.socket.onclose = () => {
            console.log("WebSocket connection closed.");
            this.socket = null;
        };
    }

    /**
     * Creates a new game for the current player.
     * @param turnsNumber The number of turns for the game.
     * @returns The game ID (empty if not received yet).
     */
    public createGame(turnsNumber: number): string {
        
        if (turnsNumber < 1) {
            throw new Error("turnsNumber should be greater than 0.");
        }

        if (!this.isSocketOpen()) {
            throw new Error("WebSocket is not open.");
        }

        this.socket!.send(`${createGameStartingConvention}:${turnsNumber}`);
        console.log("Game creation request sent.");
        return this.gameId; // Will be updated when the server responds
    }

    /**
     * Joins an existing game for the current player.
     * @param gameId The ID of the game to join.
     */
    public joinGame(gameId: string): void {
        if (!this.isSocketOpen()) {
            throw new Error("WebSocket is not open.");
        }
        this.gameId = gameId;
        this.socket!.send(`JOIN_GAME:${gameId}`);
        this.gameFull = true;
        console.log(`Join game request sent for game ID: ${gameId}`);
    }

    /**
     * Sends an action for the current player in the game.
     * @param action The action to perform.
     */
    public sendAction(action: Action): void {
        if (!this.isSocketOpen()) {
            throw new Error("WebSocket is not open.");
        }

        if (!this.gameId) {
            throw new Error("Player is not in a game.");
        }

        this.socket!.send(`ACTION:${this.gameId}:${action}`);
        console.log(`Action sent: ${action}`);
    }

    /**
     * Disconnects the player from the WebSocket server.
     */
    public disconnect(): void {
        if (this.socket) {
            this.socket.close();
            this.socket = null;
        }
    }

    /**
     * Checks if the WebSocket is open.
     */
    private isSocketOpen(): boolean {
        return this.socket !== null && this.socket.readyState === WebSocket.OPEN;
    }

    /**
     * Handles incoming WebSocket messages.
     * @param message The raw message data.
     */
    private handleMessage(message: string): void {
        console.log("Received message:", message);

        if (message.startsWith("GAME_ID:")) {
            this.gameId = message.split(":")[1];
            console.log(`Game created with ID: ${this.gameId}`);
        } else if (message.startsWith("PLAYER_TWO_JOINED")) {
            this.gameFull = true;
            console.log(`Joined game successfully: ${message.split(":")[1]}`);
        } else if (message.startsWith("JOINED_GAME:")) {
            console.log(`Joined game successfully: ${message.split(":")[1]}`);
        } else if (message.startsWith("ERROR:")) {
            console.error("Error from server:", message.split(":")[1]);
        } else {
            console.warn("Unhandled message type:", message);
        }
    }
}
