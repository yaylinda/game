import {Card} from "./card";

export class Player {
  id: string;
  opponentId: string;
  name: string;
  power: number;
  hand: Card[] = [];
  team: string;
}
