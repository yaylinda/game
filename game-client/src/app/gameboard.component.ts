import {Component, Input} from "@angular/core";
import {GameBoard} from "./gameboard";
import {HeroService} from "./hero.service";

@Component({
  selector: 'game-board',
  template: `
    <table id="gameboard">
      <tr *ngFor="let rowNum of numRows">
        <td *ngFor="let colNum of numCols" (click)="processClickedCell(rowNum, colNum)">
          <p>{{gameboard[rowNum][colNum].type}}</p>
          <p>{{gameboard[rowNum][colNum].might}}</p>
          <p>{{gameboard[rowNum][colNum].move}}</p>
        </td>
      </tr>
    </table>
  `,
  styleUrls: [ './gameboard.component.css' ]
})

export class GameboardComponent {
  private _gameboard: {};
  private _rowIndexes: number[];
  private _colndexes: number[];

  constructor(private heroService: HeroService) { }

  processClickedCell(row: number, col: number): void {
    console.log(`(${row}, ${col})`);
    let card = this.heroService.getClickedCard();
    if (card) {
      this._gameboard[row][col].type = card.cardType;
      this._gameboard[row][col].might = card.might;
      this._gameboard[row][col].move = card.movement;
      this.heroService.setClickedCard(null);
      this.heroService.drawCard(card.owningPlayer)
        .then(newCard => {
          this.heroService.updateHand(newCard);
        });
    }
  }

  @Input()
  set gameboard(gameboard: {}) {
    this._gameboard = gameboard;
  }

  get gameboard(): {} {
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
}
