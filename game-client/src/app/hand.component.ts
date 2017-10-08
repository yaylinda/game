import {Component, Input, OnInit} from "@angular/core";
import {Card} from "./card";
import {HeroService} from "./hero.service";

@Component({
  selector: 'hand',
  template: `
    <div id="hand">
      <card *ngFor="let card of cards;"
            (click)="processClickedCard(card)"
            [card]="card">
      </card>
    </div>
  `,
  styleUrls: [ './hand.component.css' ]
})

export class HandComponent implements OnInit {
  private _cards: Card[];
  private lastSelectedCard: Card;

  constructor(private heroService: HeroService) { }

  ngOnInit() {
    this.heroService.getUpdatedHand()
      .subscribe(newCard => {
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

  @Input()
  set cards(cards: Card[]) {
    this._cards = cards;
  }

  get cards(): Card[] {
    return this._cards;
  }
}
