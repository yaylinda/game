import {Card} from "./card";

export class Player {
  name: string;
  power: number;
  hand: Card[] = [];
  team: string;
}
