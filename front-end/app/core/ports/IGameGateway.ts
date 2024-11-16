import { Action } from "../models/Game";


export interface IGameGateway{
    
    createGame(turnsNumber: number): void;    

    joinGame(gameId: string): void;

    sendAction(action: Action): void;

}