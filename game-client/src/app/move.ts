import {Cell} from "./cell";
import {Card} from "./card";

export class Move {
  playerId: string;
  row: number;
  col: number;
  cell: Cell;
  hand: Card[];
}
