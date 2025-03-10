package views;

import controllers.TetrisController;
import models.Tetronimo;
import wheelsunh.users.*;
import wheelsunh.users.Frame;
import wheelsunh.users.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * TetrisBoard.java:
 * Class to model the tetris board
 *
 * @author Beatriz Ceschin Antunes
 * @version 1.0 Dec 13, 2024
 *
 * @see java.awt.Color
 * @see java.awt.event.KeyListener
 * @see java.awt.event.KeyEvent
 * @see wheelsunh.users
 */
public class TetrisBoard implements KeyListener
{
    /**
     * Constant to represent the width of the board
     */
    public static final int WIDTH = 10;

    /**
     * Constant to represent the height of the board
     */
    public static final int HEIGHT = 24;

    /**
     * Variable to keep true or false value for game over
     */
    private boolean isGameOver;

    private final TetrisController CONTROLLER; // controller instance
    private Tetronimo tetronimo; // tetronimo instance
    private Tetronimo nextTetronimo; // holds value for next tetronimo

    /**
     * hold values and shapes for game board
     */
    private Rectangle[][] playingField;
    Rectangle[][] blankRows = new Rectangle[ WIDTH ][ HEIGHT ];
    Rectangle[][] shiftedRows = new Rectangle[ WIDTH ][ HEIGHT ];

    /**
     * Test boxes to display info on screen for user
     */
    private TextBox scoreText;
    private TextBox nextTetronimoText;


    /**
     * Constructor to initialize the board
     *
     * @param frame The wheelsunh frame
     */
    public TetrisBoard( Frame frame )
    {
        frame.addKeyListener( this );
        this.CONTROLLER = new TetrisController( this );

        this.buildBoard();

        this.run();
    }

    /**
     * Builds the playing field for tetris
     */
    private void buildBoard()
    {
        this.playingField = new Rectangle[ WIDTH ][ HEIGHT ]; // Rectangle [10][24]

        for ( int i = 0; i < TetrisBoard.WIDTH; i++ )
        {
            for ( int j = 0; j < TetrisBoard.HEIGHT; j++ )
            {
                this.playingField[ i ][ j ] = new Rectangle();
                this.playingField[ i ][ j ].setLocation( i * 20 + 40, j * 20 );
                this.playingField[ i ][ j ].setSize( Tetronimo.SIZE, Tetronimo.SIZE );
                this.playingField[ i ][ j ].setColor( Color.WHITE );
                this.playingField[ i ][ j ].setFrameColor( Color.BLACK );

            }
        }
    }

    /**
     * Starts gameplay and is responsible for keeping the game going
     */
    public void run()
    {
        /*
         * display text boxes on screen
         */
        displayScoreBoard();
        displayNextTetronimoText();

        // get tetronimo
        this.tetronimo = this.CONTROLLER.getNextTetromino();
        tetronimo.setLocation(40 + (5 * Tetronimo.SIZE), 0);

        isGameOver = false; // initiate game over to false

        // keep game running until game over
        while(!isGameOver){
            // display next tetronimo
            this.nextTetronimo = this.CONTROLLER.getNextTetromino();
            nextTetronimo.setLocation(320, 100);
            Utilities.sleep(500);

            // check if keep running
            isGameOver =  this.CONTROLLER.checkIfGameIsOver(tetronimo);

            if (isGameOver){
                break;
            }

        while(this.CONTROLLER.tetronimoLanded(this.tetronimo)) // while still moving
        {
            this.tetronimo.setLocation( this.tetronimo.getXLocation(), this.tetronimo.getYLocation() + Tetronimo.SIZE );
            Utilities.sleep( 500 );
        }

        // save play and check if scored
        this.CONTROLLER.scoreBoard.savePlay(this.tetronimo);
        if (this.CONTROLLER.scoreBoard.checkIfScored()){
            // if yes, update score board and delete row
            deleteRow(this.CONTROLLER.scoreBoard.getRowScore());

            String updatedGameScore = "Score: " + this.CONTROLLER.scoreBoard.gameScore;
            this.scoreText.setText(updatedGameScore);
        }
        tetronimo = nextTetronimo;
        tetronimo.setLocation(40 + (5 * Tetronimo.SIZE), 0);
        }

        this.tetronimo = null;
    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyTyped( KeyEvent e )
    {
        //not in use
    }

    /**
     * Handles the key events by the user
     *
     * @param e The key event
     */
    @Override
    public void keyPressed( KeyEvent e )
    {
        int key = e.getKeyCode();

        if( this.tetronimo == null || isGameOver)
        {
            return;
        }
        switch( key )
        {
            case 38:
                this.tetronimo.rotate();
                if(!this.CONTROLLER.tetronimoCanMove(tetronimo, 0, 0)) {
                    this.tetronimo.rotate();}
                break;
            case 37:
                if( this.tetronimo.getXLocation() - Tetronimo.SIZE >= 40 )
                {
                    if(this.CONTROLLER.tetronimoCanMove(tetronimo, 0, -1)) {
                        this.tetronimo.shiftLeft();
                    }
                }
                break;
            case 39:
                if( (this.tetronimo.getXLocation() + this.tetronimo.getWidth()) <
                        ((TetrisBoard.WIDTH * Tetronimo.SIZE) + 40))
                {
                    if(this.CONTROLLER.tetronimoCanMove(tetronimo, 0, 1)) {
                        this.tetronimo.shiftRight();
                    }
                }
                break;
        }

    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyReleased( KeyEvent e )
    {
        //not in use
    }


    /**
     * Draws a white row to visually delete the row after scoring
     * @param row row that needs to be deleted
     */
    private void deleteRow(int row) {

        // loop through columns of that row and draw white
            for (int col = 0; col < WIDTH; col++) {
                blankRows[ col ][ row ] = new Rectangle();
                blankRows[ col ][ row ].setLocation(col * 20 + 40, row * 20 );
                blankRows[ col ][ row ].setSize( Tetronimo.SIZE, Tetronimo.SIZE );
                blankRows[ col ][ row ].setColor( Color.WHITE );
                blankRows[ col ][ row ].setFrameColor( Color.BLACK );
            }

        shiftRowsDown(row); // shift the pieces down
    }

    /**
     * Shifts pieces down by redrawing them
     * @param startingRow row that recently was deleted
     */
    private void shiftRowsDown(int startingRow) {

        for ( int i = TetrisBoard.WIDTH - 1; i > 0; i-- )
        {
            for ( int j = startingRow; j > 0 ; j-- )
            {
                this.shiftedRows[ i ][ j ] = new Rectangle();
                this.shiftedRows[ i ][ j ].setLocation( i * 20 + 40, j * 20 );
                this.shiftedRows[ i ][ j ].setSize( Tetronimo.SIZE, Tetronimo.SIZE );
                if (this.CONTROLLER.scoreBoard.occupied[ i ][ j ]){
                    this.shiftedRows[ i ][ j ].setColor( Color.CYAN );
                }
                else {
                    this.shiftedRows[i][j].setColor(Color.WHITE);
                }
                this.shiftedRows[ i ][ j ].setFrameColor( Color.BLACK );
            }
        }
    }

    /**
     * Display score to user
     */
    public void displayScoreBoard(){
        scoreText = new TextBox();
        scoreText.setText("Score : " + this.CONTROLLER.scoreBoard.gameScore);
        scoreText.setSize(80, 40);
        scoreText.setLocation( 550, 30 );
    }

    /**
     * Display text "next tetronimo" to user
     */
    private void displayNextTetronimoText(){
        nextTetronimoText = new TextBox();
        nextTetronimoText.setText("Next tetronimo:");
        nextTetronimoText.setSize(100, 40);
        nextTetronimoText.setLocation( 320, 30 );
    }

}