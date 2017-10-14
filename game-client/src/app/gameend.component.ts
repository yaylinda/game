import {Component, Input} from "@angular/core";

@Component({
  selector: 'game-end',
  template: `
    <h1 *ngIf="status === 'WIN'">You Win!</h1>
    <h1 *ngIf="status === 'LOSS'">You Lost!</h1>
    <h1 *ngIf="status === 'TIE'">Tie Game!</h1>
    <h3>{{name}} vs. {{opponentName}}</h3>
    <h4>{{score}} - {{opponentScore}}</h4>
  `,
  styleUrls: [ './gameend.component.css' ]
})

export class GameEndComponent {
  // private _status: string;
  // private _score: number;
  // private _opponentScore: number;
  //
  // @Input()
  // set status(status: string) {
  //   this._status = status;
  // }
  //
  // get status(): string {
  //   return this._status;
  // }
}
