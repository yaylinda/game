package yay.linda.dto;

import yay.linda.dto.Cell;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Game Board object; contains Cell objects.
 */
public class GameBoard {

    private int numRows;
    private int numCols;
    private Map<Integer, Map<Integer, Cell>> board = new HashMap<>();

    public GameBoard() {}

    public GameBoard(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.board = new HashMap<>();

        this.initialize();
    }

    private void initialize() {
        for (int i = 0; i < numRows; i++) {
            Map<Integer, Cell> row = new HashMap<>();
            for (int j = 0; j < numCols; j++) {
                row.put(j, new Cell());
            }
            board.put(i, row);
        }
    }

    public Cell getCell(int row, int col) {
        return board.get(row).get(col);
    }

    public Map<Integer, Map<Integer, Cell>> putCell(int row, int col, Cell cell) {
        board.get(row).put(col, cell);
        return board;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public Map<Integer, Map<Integer, Cell>> getBoard() {
        return board;
    }

    public void setBoard(Map<Integer, Map<Integer, Cell>> board) {
        this.board = board;
    }

}
