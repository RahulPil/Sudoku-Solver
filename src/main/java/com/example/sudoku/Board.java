package com.example.sudoku;

public class Board {

    Integer[][] board = new Integer[9][9];

    public Board(){ }

    public Board(Board copy){
        for(int column = 0; column < 9; column++){
            for(int row = 0; row < 9; row++){
                board[column][row] = copy.get(column, row);
            }
        }
    }

    public Integer get(int column, int row) {
        return board[column][row];
    }

    public void set(int column, int row, Integer userInput){
        board[column][row] = userInput;
    }
}
