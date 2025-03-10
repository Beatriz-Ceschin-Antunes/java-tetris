package controllers;

import models.*;

import java.util.Random;

/**
 * TetronimoPicker.java:
 * Randomly picks next tetronimo
 *
 * @author Beatriz Ceschin Antunes
 * @version 1.0 Dec 13, 2024
 *
 * @see java.util.Random
 */
public class TetronimoPicker {

    Tetronimo tetronimo;

    /**
     * Uses random number to create specific tetronimo
     */
    public void createTetronimo() {
        Random rand = new Random();
        int randomNum = rand.nextInt(7);
        switch (randomNum) {
            case 0: createJ(); break;
            case 1: createL(); break;
            case 2: createO(); break;
            case 3: createS(); break;
            case 4: createStraightLine(); break;
            case 5: createT(); break;
            case 6: createZ(); break;
        }

        // set its location
        tetronimo.setLocation( 40 + (5 * Tetronimo.SIZE), 0 );
    }

    /**
     * Create J shaped tetronimo
     */
    private void createJ(){
        tetronimo = new J();
    }
    /**
     * Create L shaped tetronimo
     */
    private void createL(){
        tetronimo = new L();
    }
    /**
     * Create O shaped tetronimo
     */
    private void createO(){
        tetronimo = new O();
    }
    /**
     * Create S shaped tetronimo
     */
    private void createS(){
        tetronimo = new S();
    }
    /**
     * Create Straight Line shaped tetronimo
     */
    private void createStraightLine(){
        tetronimo = new StraightLine();
    }
    /**
     * Create T shaped tetronimo
     */
    private void createT(){
        tetronimo = new T();
    }
    /**
     * Create Z shaped tetronimo
     */
    private void createZ(){
        tetronimo = new Z();
    }
    /**
     * Returns the tetronimo created
     */
    public Tetronimo getTetronimo() {
        return tetronimo;
    }
}
