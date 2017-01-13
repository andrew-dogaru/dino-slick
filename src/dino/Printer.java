package dino;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Very simple text printer which prints a line of text on the screen.
 */
public class Printer {

    private final Graphics g;
    private final Color color;
    private final int left;
    private final int top;
    private final int lineHeight = 20;
    
    private int line;
    
    /**
     * Constructs a Printer.
     * @param g the Graphics context
     * @param c the text color
     * @param left the left margin for the printed text
     * @param top the top margin for the printed text
     */
    public Printer(Graphics g, Color c, int left, int top) {
        this.g = g;
        this.color = c;
        this.left = left;
        this.top = top;
    }
    
    /**
     * Prints the specified string on one line.
     * @param s the string to print
     */
    public void println(String s) {
        Color old = g.getColor();
        g.setColor(this.color);
        g.drawString(s, left, top + lineHeight * line++);
        g.setColor(old);
    }
}
