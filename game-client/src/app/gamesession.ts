import {Player} from "./player";
import {GameBoard} from "./gameboard";
import {Card} from "./card";

export class GameSession {
  player: Player;
  gameboard: GameBoard;
  myTurn: boolean;
}
