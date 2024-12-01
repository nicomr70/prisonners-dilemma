import { GameWebSocketsGateway } from "./GameWebSocketsGateway";

// Singleton instance
const gateway = new GameWebSocketsGateway("ws://localhost:8080/ws");

// Connect immediately
gateway.connect();

export default gateway;