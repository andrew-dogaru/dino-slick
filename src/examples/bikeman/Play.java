package examples.bikeman;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {

    private Cardinal currentCardinal = Cardinal.NORTH;
    // N = 0 , S = 1 , W = 2 , E = 3
    private String currentCardinalString = "North";

    public enum Cardinal {
        NORTH, SOUTH, WEST, EAST
    }

    private final int DELAY = 2000;
    private Image backGround;
    private Random rand;

    private Biker biker;
    private int points = 0;
    private int lives = 3;
    private boolean alive = true;
    private int elapsedTime;
    private int transitionTimer;

    public Play() {
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        biker = new Biker(gc.getWidth(), gc.getHeight());
        points = 0;
        lives = 3;
        alive = true;
        elapsedTime = 0;
        transitionTimer = 0;
        
        rand = new Random();
        backGround = new Image("res/biker-1440x900.jpg");
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
        backGround.draw();
        if (alive) {
            if (currentCardinal == Cardinal.NORTH)
                g.setColor(Color.green);
            else if (currentCardinal == Cardinal.SOUTH)
                g.setColor(Color.orange);
            else if (currentCardinal == Cardinal.WEST)
                g.setColor(Color.red);
            else
                g.setColor(Color.white);
            g.drawString("Goto:" + currentCardinalString, 200, 250);
            g.drawString("TimeLeft: " + (DELAY - elapsedTime) + " Points: " + points + " Lives: " + lives, 200, 300);
            biker.render(g);
        } else {
            g.setColor(Color.white);
            g.drawString("You Lose | Total Points: " + points, 200, 200);
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Cardinal currentLocation = null;
        if (biker.touchNorth())
            currentLocation = Cardinal.NORTH;
        else if (biker.touchSouth())
            currentLocation = Cardinal.SOUTH;
        else if (biker.touchWest())
            currentLocation = Cardinal.WEST;
        else if (biker.touchEast())
            currentLocation = Cardinal.EAST;

        if (elapsedTime >= DELAY || isOnTarget(currentLocation)) {
            if (isOnTarget(currentLocation) && alive)
                points++;
            else
                lives--;
            if (lives < 0)
                alive = false;
            elapsedTime = 0;
            setTarget();
        } else
            elapsedTime += delta;

        // Move the biker
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_LEFT)) {
            biker.left(delta);
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            biker.right(delta);
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            biker.up(delta);
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            biker.down(delta);
        }

        // Make sure the biker is within the screen
        biker.fence();
        
        if (!alive) {
            transitionToStartGame(gc, sbg);
        }
    }

    public int getID() {
        return Main.PLAY;
    }
    
    private void transitionToStartGame(GameContainer gc, StateBasedGame sbg) 
            throws SlickException {
        transitionTimer++;
        
        if (transitionTimer / Main.TARGET_FRAME_RATE > 2)
            ((Main) sbg).startGame(gc);
    }
    
    private void setTarget() {
        switch (rand.nextInt(4)) {
        case 0:
            currentCardinal = Cardinal.NORTH;
            currentCardinalString = "North";
            break;
        case 1:
            currentCardinal = Cardinal.SOUTH;
            currentCardinalString = "South";
            break;
        case 2:
            currentCardinal = Cardinal.WEST;
            currentCardinalString = "West";
            break;
        case 3:
            currentCardinal = Cardinal.EAST;
            currentCardinalString = "East";
            break;
        }
    }
    
    private boolean isOnTarget(Cardinal location) {
        return location == currentCardinal;
    }
}
