package examples.bikeman;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Biker {
    private int x = 75;
    private int y = 75;

    private final int screenWidth;
    private final int screenHeight;
    private final int imgWidth;
    private final int imgHeight;
    private final Image image;
    
    public Biker(int w, int h) throws SlickException {
        screenWidth = w;
        screenHeight = h;
        image = new Image("res/biker.png");
        imgWidth = image.getWidth();
        imgHeight = image.getHeight();
    }
    
    public void render(Graphics g) {
//        g.fillRect(x, y, imgWidth, imgHeight);
        g.drawImage(image, x, y);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public void fence() {
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x >= screenWidth - imgWidth)
            x = screenWidth - imgWidth;
        if (y >= screenHeight - imgHeight)
            y = screenHeight - imgHeight;
    }

    public boolean left(int delta) {
        if (x > 0) {
            x -= 1 * delta;
            return true;
        } else
            return false;
    }

    public boolean right(int delta) {
        if (x < screenWidth - imgWidth - 1) {
            x += 1 * delta;
            return true;
        } else
            return false;
    }

    public boolean up(int delta) {
        if (y > 0) {
            y -= 1 * delta;
            return true;
        } else
            return false;
    }

    public boolean down(int delta) {
        if (y < screenHeight - imgHeight - 1) {
            y += 1 * delta;
            return true;
        } else
            return false;
    }
    
    public boolean touchNorth() {
        return y <= 0;
    }
    
    public boolean touchSouth() {
        return y >= screenHeight - imgHeight;
    }
    
    public boolean touchWest() {
        return x <= 0;
    }
    
    public boolean touchEast() {
        return x >= screenWidth - imgWidth;
    }
}
