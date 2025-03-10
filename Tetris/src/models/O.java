package models;

import java.awt.*;

/**
 * O.java:
 * Models the O tetronimo
 *
 * @author Beatriz Ceschin Antunes
 * @version 1.0 Dec 13, 2024
 *
 * @see java.awt
 */
public class O extends Tetronimo{
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    public O()
    {
        super.r1.setLocation( 0, Tetronimo.SIZE );
        super.r2.setLocation( 0, 0 );
        super.r3.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
        super.r4.setLocation( Tetronimo.SIZE, 0 );

        super.add( r1 );
        super.add( r2 );
        super.add( r3 );
        super.add( r4 );
    }

    /**
     * Rotates the tetronimo
     */
    @Override
    public void rotate()
    {
        super.rotate();

        Point curLoc = super.getLocation();
        super.setLocation( 0, 0 );

        super.r1.setLocation( 0, Tetronimo.SIZE );
        super.r2.setLocation( 0, 0 );
        super.r3.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
        super.r4.setLocation( Tetronimo.SIZE, 0 );

        super.setLocation( curLoc );
    }

    /**
     * Gets the height of the tetronimo based on the orientation
     *
     * @return The height of the tetronimo
     */
    @Override
    public int getHeight()
    {
            return Tetronimo.SIZE * 2;
    }

    /**
     * Gets the width of the tetronimo based on the orientation
     *
     * @return The width of the tetronimo
     */
    @Override
    public int getWidth()
    {
        return Tetronimo.SIZE * 2;
    }
}
