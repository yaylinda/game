import {Component, Input} from "@angular/core";
import {GameBoard} from "./gameboard";

@Component({
  selector: 'game-board',
  template: `
    <table id="gameboard">
      <tr *ngFor="let rowNum of numRows">
        <td *ngFor="let colNum of numCols">
          {{gameboard[rowNum][colNum].state}}
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
