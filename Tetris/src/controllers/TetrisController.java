package controllers;

import models.*;
import views.TetrisBoard;

import java.awt.*;

/**
 * TetrisController.java:
 * Class to hold all the game logic for tetris
 *
 * @author Beatriz Ceschin Antunes
 * @version 1.0 Dec 13, 2024
 * @see java.awt
 */
public class TetrisController
{
    final TetrisBoard TETRIS_BOARD;
    public TetrisScoreBoard scoreBoard;

    /**
     * Constructor to take in a tetris board and a score board so the controller and the board can communicate
     *
     * @param tetrisBoard A tetris board instance
     */
    public TetrisController( TetrisBoard tetrisBoard )
    {
        this.TETRIS_BOARD = tetrisBoard;
        scoreBoard= new TetrisScoreBoard(TetrisBoard.HEIGHT,TetrisBoard.WIDTH); // score board instance
    }

    /**
     * Randomly chooses the next tetronimo and returns it
     *
     * @return The next tetronimo to be played
     */
    public Tetronimo getNextTetromino()
    {
        Tetronimo tetronimo;

        TetronimoPicker picker = new TetronimoPicker();
        picker.createTetronimo(); // randomly creates tetronimo
        tetronimo= picker.getTetronimo();

        return tetronimo; // return random choice
    }

    /**
     * Method to determine if the tetronimo has landed
     *
     * @param tetronimo The tetronimo to evaluate
     * @return True if the tetronimo has landed (on the bottom of the board or another tetronimo), false if it has not
     */
    public boolean tetronimoLanded( Tetronimo tetronimo )
    {
        // if bottom of board is reached return true
        int nextY = tetronimo.getYLocation() + tetronimo.getHeight() + Tetronimo.SIZE;

        if(nextY > 480) {
            return false; // tetronimo did not land yet
        }

        // or return if a tetronimo can move in reference to other tetronimos and board boundaries
        return tetronimoCanMove(tetronimo, 1, 0);
    }

    /**
     * Checks boundaries to determine if tetronimo can move in the board
     * @param tetronimo The tetronimo to evaluate
     * @param r value for the offset for row
     * @param c value for the offset of column
     * @return true if tetronimo can move, false if not
     */
    public boolean tetronimoCanMove(Tetronimo tetronimo, int r, int c) {

        /*
         * create point array with locations of individual blocks that make up the tetronimo
         */
        Point[] blocks = tetronimo.getBoundaryLocations();

        for(int i = 0; i < blocks.length; i++){

            // offset the location to fit within the bounds of the board
            int row = ((blocks[i].y)/20) + r;
            int col = (((blocks[i].x) - 40)/20) + c;

            /*
             * check if it has gone outside the perimeter of the board
             */
            if(col > 9) {
                return false;
            } else if(row > 23) {
                return false;
            }

            /*
             * check if there is already a block on that spot
             */
            if(scoreBoard.occupied[col][row]){
                return false; // cannot move if a block is already there
            }
        }

        return true; // else, tetronimo can move
    }

    /**
     * Checks if there is any move room to drop tetronimos
     * @param currentPiece The current tetronimo falling
     * @return return false if tetronimo can still move and game can still be played
     */
    public boolean checkIfGameIsOver(Tetronimo currentPiece) {

        return (!tetronimoCanMove(currentPiece, 0 ,0));

    }


}
