import {Player} from "./player";
import {GameBoard} from "./gameboard";
import {Card} from "./card";

export class GameSession {
  id: string;
  player1: Player;
  player2: Player;
  gameBoard: GameBoard;
  deck: Card[]
}
