import {Component, Input} from "@angular/core";
import {Player} from "./dto/player";

@Component({
  selector: 'game-end',
  template: `
    <h1 *ngIf="status === 'WIN'">You Win!</h1>
    <h1 *ngIf="status === 'LOSS'">You Lost!</h1>
    <h1 *ngIf="status === 'TIE'">Tie Game!</h1>
    <h3>{{player.name}} vs. {{player.opponentName}}</h3>
    <h4>{{player.score}} - {{player.opponentScore}}</h4>
  `,
  styleUrls: [ './gameend.component.css' ]
})

export class GameEndComponent {
  private _status: string;
  private _player: Player;

  @Input()
  set status(status: string) {
    this._status = status;
  }

  get status(): string {
    return this._status;
  }

  @Input()
  get player(): Player {
    return this._player;
  }

  set player(value: Player) {
    this._player = value;
  }
}
