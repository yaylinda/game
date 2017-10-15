import {Component, Input} from "@angular/core";
import {GameService} from "./game.service";
import {GameSession} from "./dto/gamesession";

@Component({
  selector: 'game-board',
  template: `
    <table id="gameboard">
      <tr *ngFor="let rowNum of numRows">
        <td *ngFor="let colNum of numCols" 
            (click)="processClickedCell(rowNum, colNum)" 
            [ngClass]="[(gameSession.myTurn && (rowNum >= gameSession.player.furthestRow)) ? 'playable' : '', (gameSession.gameboard[rowNum][colNum].team === gameSession.player.team) ? 'mine' : 'notmine']">
          <p *ngIf="gameSession.gameboard[rowNum][colNum].state === 'OCCUPIED'">{{gameSession.gameboard[rowNum][colNum].type}}</p>
          <p *ngIf="gameSession.gameboard[rowNum][colNum].state === 'OCCUPIED'">{{gameSession.gameboard[rowNum][colNum].might}}</p>
          <!--<p *ngIf="gameSession.gameboard[rowNum][colNum].state === 'OCCUPIED'">{{gameSession.gameboard[rowNum][colNum].move}}</p>-->
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

  constructor(private heroService: GameService) { }

  processClickedCell(row: number, col: number): void {
    console.log(`clicked on: (${row}, ${col})`);
    let card = this.heroService.getClickedCard();
    if (card.cardType !== 'BLANK') {
      if (this._gameSession.myTurn && (row >= this._gameSession.player.furthestRow) && (this._gameSession.gameboard[row][col].state === 'EMPTY' /*|| this._gameSession.gameboard[row][col].type === card.cardType*/) && this._gameSession.player.power >= card.cost) {
        this.heroService.sendCardPut(card, row, col).then(gameboard => {
          this._gameSession.gameboard = gameboard;
          this._gameSession.player.power = this._gameSession.player.power - card.cost;
          this.heroService.updatePowerEE.emit(this._gameSession.player.power);
          this.heroService.drawCard(this._gameSession.player.id).then(newCard => {
            newCard.justDrew = true;
            this.heroService.updateHandEE.emit(newCard);
          });
        });
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
