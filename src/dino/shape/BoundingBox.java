package dino.shape;

/**
 * A rectangular bounding box.
 */
public class BoundingBox extends MovablePoint {
    /** The width of the box */
    protected float width;
    /** The height of the box */
    protected float height;
    
    /**
     * Create a new bounding box
     * 
     * @param x the x position of the box
     * @param y the y position of the box
     * @param width the width of the box
     * @param height the height of the box
     */
    public BoundingBox(float x, float y, float width, float height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    /**
     * Create a new bounding box by cloning another box.
     * 
     * @param other another box
     */
    public BoundingBox(BoundingBox other) {
        super(other.getX(), other.getY());
        this.width = other.getWidth();
        this.height = other.getHeight();
    }

    /**
     * Create a new bounding box having the two points as opposite corners.
     * 
     * @param point1 the first point
     * @param point2 the second point
     */
    public BoundingBox(Point point1, Point point2) {
        super(point1.getX(), point1.getY());
        this.width = point2.getX() - point1.getX();
        this.height = point2.getY() - point1.getY();
    }

    /**
     * Get the width of the box
     * 
     * @return The width of the box
     */
    public float getWidth() {
        return width;
    }
    
    /**
     * Get the height of the box
     * 
     * @return The height of the box
     */
    public float getHeight() {
        return height;
    }

    /**
     * Check if this BoundingBox touches another one.
     * 
     * @param other the other bounding box to check against
     * @return true if the boxes touch or overlap, otherwise false
     */
    public boolean intersects(BoundingBox other) {
        // if the x intervals don't intersect then the boxes don't intersect
        if ((getX() > (other.getX() + other.getWidth())) || 
           ((getX() + getWidth()) < other.getX())) {
            return false;
        }
        // if the y intervals don't intersect then the boxes don't intersect
        if ((getY() > (other.getY() + other.getHeight())) || 
           ((getY() + getHeight()) < other.getY())) {
            return false;
        }
        return true;
    }

    public void stopAbove(BoundingBox other) {
        setY(other.getY() - getHeight());
        setYSpeed(0.0f);        
    }
}
