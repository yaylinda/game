import {Player} from "./player";
import {GameBoard} from "./gameboard";
import {Card} from "./card";

export class GameSession {
  player: Player;
  gameBoard: GameBoard;
  myTurn: boolean;
}
