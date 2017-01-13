package dino.shape;

/**
 * Calculates the position of an object falling in gravity. 
 */
public class Gravity {

    /** Gravitational acceleration (pixel / millisecond squared) */
    public static final float G = 9.8f / 2000;
    
    public static MovablePoint update(MovablePoint mo, int elapsedTime) {
        int delta = elapsedTime - mo.getTime();
        float v1 = mo.getYSpeed() + G * delta;
        float y1 = mo.getY() + mo.getYSpeed() * delta + G / 2 * delta * delta;
        MovablePoint result = new MovablePoint(mo.getX(), y1);
        result.setYSpeed(v1);
        result.setTime(elapsedTime);
        return result;
    }
}
