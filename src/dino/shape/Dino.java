package dino.shape;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Dino extends BoundingBox {
	// TODO extract width and height from image
    private static float imgWidth;
    private static float imgHeight;
    private static Image image;
    
    // Load Dino's image, it's one for all Dino instances
    static {
    	try {
			image = new Image("res/dino-32.jpg");
			imgWidth = image.getWidth();
			imgHeight = image.getHeight();
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }

    /**
     * Constructs a Dino.
     * 
     * @param x the X coordinate of the top left corner
     * @param base the Y coordinate of the base
     * @throws SlickException 
     */
    public Dino(float x, float base) throws SlickException {
        super(x, base - imgHeight, imgWidth, imgHeight);
    }
    
    public void render(Graphics g) {
       /* Color old = g.getColor();
        * g.setColor(Color.green);
        * g.fillRect(getX(), getY(), getWidth(), getHeight());
        * g.setColor(old);
        */
       g.drawImage(image, getX(), getY());
    }
    
    public void kickOnY(float initialSpeed, int currentTime) {
        setYSpeed(initialSpeed);
        setTime(currentTime);
    }
    
    /**
     * Stop the object if it goes outside the specified boundaries.
     * @param minX minimum X boundary
     * @param maxX maximum X boundary
     * @param minY minimum Y boundary
     * @param maxY maximum Y boundary
     */
    public void stop(int minX, int maxX, int minY, int maxY) {
        if (getX() <= minX) {
            setX(minX);
            setXSpeed(0);
        }
        if ((getX() + getWidth() - 1) >= maxX) {
            setX(maxX - getWidth());
            setXSpeed(0);
        }
        if (getY() <= minY) {
            setY(minY);
            setYSpeed(0);
        }
        if ((getY() + getHeight() - 1) >= maxY) {
            setY(maxY - getHeight());
            setYSpeed(0);
        }
    }
}
