import {Player} from "./player";
import {Cell} from "./cell";

export class GameSession {
  player: Player;
  gameboard: Cell[][];
  myTurn: boolean;
  numRows: number;
  numCols: number;
  points: number;
}
