import {Component, Input} from "@angular/core";
import {GameBoard} from "./gameboard";
import {HeroService} from "./hero.service";
import {Cell} from "./cell";

@Component({
  selector: 'game-board',
  template: `
    <table id="gameboard">
      <tr *ngFor="let rowNum of numRows">
        <td *ngFor="let colNum of numCols" 
            (click)="processClickedCell(rowNum, colNum)" 
            [ngClass]="[(myTurn && rowNum === 4) ? 'playable' : '', (gameboard[rowNum][colNum].team === 'TEAM1') ? 'team1' : 'team2']">
          <p *ngIf="gameboard[rowNum][colNum].state === 'OCCUPIED'">{{gameboard[rowNum][colNum].type}}</p>
          <p *ngIf="gameboard[rowNum][colNum].state === 'OCCUPIED'">{{gameboard[rowNum][colNum].might}}</p>
          <p *ngIf="gameboard[rowNum][colNum].state === 'OCCUPIED'">{{gameboard[rowNum][colNum].move}}</p>
        </td>
      </tr>
    </table>
  `,
  styleUrls: [ './gameboard.component.css' ]
})

export class GameboardComponent {
  private _gameboard: Cell[][];
  private _rowIndexes: number[];
  private _colndexes: number[];
  private _myTurn: boolean;
  private _power: number;

  constructor(private heroService: HeroService) { }

  processClickedCell(row: number, col: number): void {
    console.log(`clicked on: (${row}, ${col})`);
    if (this.myTurn === true) { // only if it's my turn
      if (row === 4) { // only put card on first row
        let card = this.heroService.getClickedCard();
        if (card) {
          if (this._power >= card.cost) {
            this._gameboard[row][col].state = 'OCCUPIED';
            this._gameboard[row][col].type = card.cardType;
            this._gameboard[row][col].might = card.might;
            this._gameboard[row][col].move = card.movement;
            this._gameboard[row][col].team = card.owningTeam;
            this._power = this._power - card.cost;
            this.heroService.updatePowerEE.emit(this._power);
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

  @Input()
  set gameboard(gameboard: Cell[][]) {
    this._gameboard = gameboard;
  }

  get gameboard(): Cell[][] {
    return this._gameboard;
  }

  @Input()
  set numRows(numRows: number[]) {
    this._rowIndexes = numRows;
  }

  get numRows(): number[] {
    return this._rowIndexes;
  }

  @Input()
  set numCols(numCols: number[]) {
    this._colndexes = numCols;
  }

  get numCols(): number[] {
    return this._colndexes;
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
