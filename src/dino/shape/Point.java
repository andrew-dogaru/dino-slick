package dino.shape;

/**
 * A 2D point.
 */
public abstract class Point {
    // The point coordinates
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x location of the point.
     * 
     * @return the x location of the point.
     */
    public float getX() {
        return x;
    }

    /**
     * Set the x position of the point.
     * @param x the new x position of the point.
     */
    public void setX(float x) {
        this.x = x;

    }

    /**
     * Set the y position of the point.
     * @param y the new y position of the point.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Get the y position of the point.
     * 
     * @return the y position of the point.
     */
    public float getY() {
        return y;
    }
}
