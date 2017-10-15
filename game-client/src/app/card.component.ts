import {Component, Input} from "@angular/core";
import {Card} from "./dto/card";

@Component({
  selector: 'card',
  template: `
    <div id="card" [ngClass]="[card.clicked ? 'highlight-card' : '' , card.justDrew ? 'just-drew' : '']">
      <p *ngIf="card.cardType !== 'BLANK'">{{card.cardType}}</p>
      <p *ngIf="card.cardType !== 'BLANK'">Cost: {{card.cost}}</p>
      <p *ngIf="card.cardType !== 'BLANK'">Might: {{card.might}}</p>
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
