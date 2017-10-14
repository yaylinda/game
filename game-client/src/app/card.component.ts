import {Component, Input} from "@angular/core";
import {Card} from "./dto/card";

@Component({
  selector: 'card',
  template: `
    <div id="card" [ngClass]="[card.clicked ? 'highlight-card' : '' , card.justDrew ? 'just-drew' : '']">
      <p>{{card.cardType}}</p>
      <p>Cost: {{card.cost}}</p>
      <p>Might: {{card.might}}</p>
      <!--<p>Movement: {{card.movement}}</p>-->
    </div>
  `,
  styleUrls: [ './card.component.css' ]
})

export class CardComponent {
  private _card: Card;

  @Input()
  set card(card: Card) {
    this._card = card;
  }

  get card(): Card {
    return this._card;
  }
}
