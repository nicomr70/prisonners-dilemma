import {server_url} from "../models/Server";
import {GameWebSocketsGateway} from "./GameWebSocketsGateway";

// Singleton instance
const gateway = new GameWebSocketsGateway(server_url);

// Connect immediately
gateway.connect();

export default gateway;