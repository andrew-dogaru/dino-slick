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
        super(Math.min(point1.getX(), point2.getX()), Math.min(point1.getY(), point2.getY()));
        this.width = Math.abs(point2.getX() - point1.getX());
        this.height = Math.abs(point2.getY() - point1.getY());
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
     * Set the width of the box
     * @param value the new width
     */
    public void setWidth(float value) {
        this.width = value;
    }
    
    /**
     * Set the height of the box
     * @param value the new height
     */
    public void setHeight(float value) {
        this.height = value;
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

    /**
     * Check if this box is above (lower Y) the other box.
     * @param other the other box
     * @return true if this box is above the other 
     */
    public boolean isAbove(BoundingBox other) {
        return (getY() + getHeight() < other.getY());
    }

    /**
     * Set this box above the the other box and stop it from moving on Y.
     * @param other the other box
     */
    public void stopAbove(BoundingBox other) {
        setY(other.getY() - getHeight());
        setYSpeed(0.0f);        
    }
}
