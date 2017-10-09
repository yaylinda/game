import {Component, Input, OnInit} from "@angular/core";
import {Card} from "./card";
import {HeroService} from "./hero.service";

@Component({
  selector: 'hand',
  template: `
    <div id="hand">
      <div id="game-stats">
        <p *ngIf="myTurn">My Turn</p>
        <p *ngIf="!myTurn">Opponent's Turn</p>
        <p>Power: {{power}}</p>
      </div>
      <card *ngFor="let card of cards;"
            (click)="processClickedCard(card)"
            [card]="card">
      </card>
      <button id="end-turn-btn" (click)="endTurn()">End Turn</button>
    </div>
  `,
  styleUrls: [ './hand.component.css' ]
})

export class HandComponent implements OnInit {
  private _cards: Card[];
  private _myTurn: boolean;
  private _power: number;
  private lastSelectedCard: Card;

  constructor(private heroService: HeroService) { }

  ngOnInit() {
    this.heroService.getUpdatedHand()
      .subscribe((newCard: Card) => {
        console.log('recieved new card which is......');
        console.log(newCard);
        const index = this._cards.indexOf(this.lastSelectedCard);
        this._cards.splice(index, 1, newCard);
      });
  }

  processClickedCard(card: Card): void {
    for (let otherCard of this._cards) {
      otherCard.clicked = false;
    }
    card.clicked = true;
    this.lastSelectedCard = card;
    this.heroService.setClickedCard(card);
  }

  endTurn(): void {
    this._myTurn = false;
    // this.heroService.endTurn();
    console.log('end turn');
  }

  @Input()
  set cards(cards: Card[]) {
    this._cards = cards;
  }

  get cards(): Card[] {
    return this._cards;
  }

  @Input()
  set myTurn(myTurn: boolean) {
    this._myTurn = myTurn;
  }

  get myTurn(): boolean {
    return this._myTurn;
  }

  @Input()
  get power(): number {
    return this._power;
  }

  set power(value: number) {
    this._power = value;
  }
}
