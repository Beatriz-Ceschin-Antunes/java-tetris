package models;

import wheelsunh.users.Rectangle;
import wheelsunh.users.ShapeGroup;

import java.awt.*;

/**
 * Tetronimo.java:
 * An abstract class to model the base capabilities of a tetronimo
 *
 * @author Beatriz Ceschin Antunes
 * @version 1.0 Dec 13, 2024
 *
 * @see java.awt.Color,wheelsunh.users.Rectangle,wheelsunh.users.ShapeGroup
 */
public abstract class Tetronimo extends ShapeGroup
{
    /**
     * Constant to represent the size of the tetronimo
     */
    public static final int SIZE= 20;

    protected Rectangle r1;
    protected Rectangle r2;
    protected Rectangle r3;
    protected Rectangle r4;

    protected int curRotation = 1;

    /**
     * Generates the four rectangles for the tetronino and puts them on the screen, they are at the default coordinates
     * to start
     */
    public Tetronimo()
    {
        super();
        this.r1 = new Rectangle();
        this.r1.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r1.setFrameColor( Color.BLACK );

        this.r2 = new Rectangle();
        this.r2.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r2.setFrameColor( Color.BLACK );

        this.r3 = new Rectangle();
        this.r3.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r3.setFrameColor( Color.BLACK );

        this.r4 = new Rectangle();
        this.r4.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r4.setFrameColor( Color.BLACK );

        this.r1.setFillColor(Color.cyan);
        this.r2.setFillColor(Color.cyan);
        this.r3.setFillColor(Color.cyan);
        this.r4.setFillColor(Color.cyan);
    }

    /**
     * Increments the rotation of the tetronimo, other classes need to override this to provide the full functionality
     */
    public void rotate()
    {
        this.curRotation++;
    }

    /**
     * Shifts the tetronimo left one row
     */
    public void shiftLeft()
    {
        super.setLocation( super.getXLocation() - Tetronimo.SIZE, super.getYLocation() );
    }

    /**
     * Shifts the tetronimo right one row
     */
    public void shiftRight()
    {
        super.setLocation( super.getXLocation() + Tetronimo.SIZE, super.getYLocation() );
    }

    /**
     * Gives coordinates of blocks in tetronimos
     * @return array of coordinates
     */
    public Point[] getBoundaryLocations(){
        Point[] rectangles = new Point[4];
        rectangles[0] = r1.getLocation();
        rectangles[1] = r2.getLocation();
        rectangles[2] = r3.getLocation();
        rectangles[3] = r4.getLocation();

        return rectangles;
    }
}