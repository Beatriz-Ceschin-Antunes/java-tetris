package controllers;

import models.*;
import views.TetrisBoard;

import java.awt.*;

/**
 * TetrisScoreBoard.java:
 * Class to hold all the different calculations related to a user's score during the tetris game
 *
 * @author Beatriz Ceschin Antunes
 * @version 1.0 Dec 13, 2024
 * @see java.awt
 */
public class TetrisScoreBoard {

    public boolean[][] occupied; // will hold boolean values to indicate previous moves
    public int gameScore = 0; // starts at 0
    private int rowScored; // indicates row for which player scored

    /**
     * Constructor that sets up a clean slate for occupied spaces, setting all to false
     * @param w width of game board
     * @param h height of game board
     */
    public TetrisScoreBoard(int w, int h) {
        this.occupied = new boolean[h][w];

        for (int i = 0; i < h; i++ ) {
            for (int j = 0; j < w; j++) {
                this.occupied[i][j] = false; // Initially unoccupied
            }
        }
    }

    /**
     * Updates occupied to save player's play
     * @param tetronimo Tetronimo to evaluate
     */
    public void savePlay(Tetronimo tetronimo){

        Point[] blocks = tetronimo.getBoundaryLocations(); // gets the indiviual locations of each block within a tetronimo

        /*
         * set all pieces that are part of the given tetronimo to true in occupied
         */
        for(int i = 0; i < blocks.length; i++) {
            int row = ((blocks[i].y)/20); // offset value for row
            int col = (((blocks[i].x) - 40)/20); // offset value for column

            this.occupied[col][row] = true;
        }
    }

    /**
     * Checks if the player completed a row and scored
     * @return true if teh player scored
     */
    public boolean checkIfScored() {
        // check if rows have been filled
        for(int row = TetrisBoard.HEIGHT - 1; row >= 0; row--) {
            boolean rowIsFilled = true;
            for(int col = 0; col < TetrisBoard.WIDTH; col++) {
                rowIsFilled = rowIsFilled && this.occupied[col][row];
            }
            if(rowIsFilled) { // if row is filled, player scored
                resetRow(row); // delete the row
                gameScore = gameScore + 100; // increase the score
                return true;
            }
        }
        // else, player did not score
        return false;
    }

    /**
     * Resets the row for which player scored
     * @param row row previously flagged as the row player completed/scored with
     */
    private void resetRow(int row) {

        // loops through rows to offset the previous occupied value, so the row is reset
        for(int rowCounter = row; rowCounter > 0; rowCounter--) {
            for(int col = 0; col < TetrisBoard.WIDTH; col++) {
                this.occupied[col][rowCounter] = this.occupied[col][rowCounter - 1];
            }
        }
        for(int col = 0; col < TetrisBoard.WIDTH; col++) {
            this.occupied[col][0] = false;
        }
    }

    /**
     * Returns the row that the player scored on
     * @return row number
     */
    public int getRowScore(){
        return this.rowScored;
    }

}
