package dino.shape;

/**
 * A moving point.
 */
public class MovablePoint extends Point implements Movable {
    private float xSpeed, ySpeed;
    private int time;
    
    public static final float MIN_POSITIVE_Y_SPEED = 0.001f;

    public MovablePoint(float x, float y) {
        super(x, y);
        xSpeed = 0.0f;
        ySpeed = 0.0f;
        time = 0;
    }
    
    @Override
    public int getTime() {
        return time;
    }

    @Override
    public float getYSpeed() {
        return ySpeed;
    }

    @Override
    public float getXSpeed() {
        return xSpeed;
    }
    @Override
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void setYSpeed(float speed) {
        this.ySpeed = speed;
    }

    @Override
    public void setXSpeed(float speed) {
        this.xSpeed = speed;
    }
    
    public boolean isMovingOnY() {
        return !(Math.abs(getYSpeed() - 0.0f) < MIN_POSITIVE_Y_SPEED);
    }
    
    public void update(MovablePoint other) {
        setXSpeed(other.getXSpeed());
        setYSpeed(other.getYSpeed());
        setX(other.getX());
        setY(other.getY());
        setTime(other.getTime());
    }
}
