import {Player} from "./player";
import {Cell} from "./cell";

export class GameSession {
  state: string;
  player: Player;
  gameboard: Cell[][];
  myTurn: boolean;
  numRows: number;
  numCols: number;
  numCardsInDeck: number;
}
