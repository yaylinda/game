import {Component, Input} from "@angular/core";
import {HeroService} from "./hero.service";
import {GameSession} from "./gamesession";

@Component({
  selector: 'game-board',
  template: `
    <table id="gameboard">
      <tr *ngFor="let rowNum of numRows">
        <td *ngFor="let colNum of numCols" 
            (click)="processClickedCell(rowNum, colNum)" 
            [ngClass]="[(gameSession.myTurn && rowNum === 4) ? 'playable' : '', (gameSession.gameboard[rowNum][colNum].team === 'TEAM1') ? 'team1' : 'team2']">
          <p *ngIf="gameSession.gameboard[rowNum][colNum].state === 'OCCUPIED'">{{gameSession.gameboard[rowNum][colNum].type}}</p>
          <p *ngIf="gameSession.gameboard[rowNum][colNum].state === 'OCCUPIED'">{{gameSession.gameboard[rowNum][colNum].might}}</p>
          <p *ngIf="gameSession.gameboard[rowNum][colNum].state === 'OCCUPIED'">{{gameSession.gameboard[rowNum][colNum].move}}</p>
        </td>
      </tr>
    </table>
  `,
  styleUrls: [ './gameboard.component.css' ]
})

export class GameboardComponent {
  private _gameSession: GameSession;
  private _numRows: number[];
  private _numCols: number[];

  constructor(private heroService: HeroService) { }

  processClickedCell(row: number, col: number): void {
    console.log(`clicked on: (${row}, ${col})`);
    if (this._gameSession.myTurn === true) { // only if it's my turn
      if (row === 4) { // only put card on first row
        if (this._gameSession.gameboard[row][col].state === 'EMPTY') {
          let card = this.heroService.getClickedCard();
          if (card) {
            if (this._gameSession.player.power >= card.cost) {
              this._gameSession.gameboard[row][col].state = 'OCCUPIED';
              this._gameSession.gameboard[row][col].type = card.cardType;
              this._gameSession.gameboard[row][col].might = card.might;
              this._gameSession.gameboard[row][col].move = card.movement;
              this._gameSession.gameboard[row][col].team = card.owningTeam;
              this._gameSession.player.power = this._gameSession.player.power - card.cost;
              this.heroService.updatePowerEE.emit(this._gameSession.player.power);
              this.heroService.updateBoardEE.emit(this._gameSession.gameboard);
              this.heroService.setClickedCard(null);
              this.heroService.drawCard(card.owningPlayer)
                .then(newCard => {
                  this.heroService.updateHand(newCard);
                });
            }
          }
        }
      }
    }
  }

  @Input()
  set gameSession(gameSession: GameSession) {
    this._gameSession = gameSession;
  }

  get gameSession(): GameSession {
    return this._gameSession;
  }

  @Input()
  get numRows(): number[] {
    return this._numRows;
  }

  set numRows(value: number[]) {
    this._numRows = value;
  }

  @Input()
  get numCols(): number[] {
    return this._numCols;
  }

  set numCols(value: number[]) {
    this._numCols = value;
  }
}
