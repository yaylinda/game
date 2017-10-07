import {Component, Input, OnInit} from "@angular/core";
import {Card} from "./card";

@Component({
  selector: 'card-front',
  template: `
    <div id="card">
      <p>{{card.cardType}}</p>
      <p>Cost: {{card.cost}}</p>
      <p>Might: {{card.might}}</p>
      <p>Movement: {{card.movement}}</p>
    </div>
  `,
  styleUrls: [ './card-front.component.css' ]
})

export class CardFrontComponent {
  private _card: Card;

  @Input()
  set card(card: Card) {
    this._card = card;
  }

  get card(): Card {
    return this._card;
  }
}
