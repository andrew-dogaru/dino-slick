package dino.shape;

/**
 * An object which moves.
 */
public interface Movable {
    int getTime();

    float getYSpeed();

    float getXSpeed();

    void setTime(int time);

    void setYSpeed(float speed);

    void setXSpeed(float speed);
}
