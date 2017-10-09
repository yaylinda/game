package yay.linda.dto;

import yay.linda.dto.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Game Board object; contains Cell objects.
 */
public class GameBoard {

    private int numRows;
    private int numCols;
    private List<List<Cell>> board = new ArrayList<>();

    public GameBoard() {}

    public GameBoard(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.board = new ArrayList<>();

        this.initialize();
    }

    private void initialize() {
        for (int i = 0; i < numRows; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < numCols; j++) {
                row.add(new Cell());
            }
            board.add(row);
        }
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

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }
}
