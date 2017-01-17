package dino;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;

import org.newdawn.slick.state.StateBasedGame;

import dino.shape.BoundingBox;
import dino.shape.Dino;
import dino.shape.Gravity;
import dino.shape.GroundLine;
import dino.shape.MovablePoint;

/**
 * The main game state.
 */
public class Play extends BasicGameState {
    
    private int elapsedTime; // since init() (milliseconds)
    private static final float initialXSpeed = 0.3f;   // pixels/millisecond
    private static final float initialYSpeed = - 1.5f; // pixels/millisecond
    
    // Dino stays at this x position and jumps up and down
    private static final float DINO_X_POSITION = 150.0f;  

    private GroundLine ground;
    private Dino dino;
    private BoundingBox canvas;

    private float speed;    // ground speed

    private int lives;      // Dino's lives
    private int points;     // game points
    private int transitionTimer;
 
    // If canKickDino is false then the user can't change Dino's speed.
    // This happens if the user kicked Dino up while he was already jumping
    // (can do it only once) or if Dino falls through a hole.
    private boolean canKickDino;
    
    // True if dino is falling through a hole, otherwise false
    private boolean dinoInHole;

    public Play() {
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        elapsedTime = 0;
        canvas = new BoundingBox(0, 0, gc.getWidth(), gc.getHeight());
        float groundBase = gc.getHeight() * 4 / 5.0f;
        ground = new GroundLine(0.0f, groundBase, (float)gc.getWidth() * 10, 10.0f, canvas.getWidth());
        dino = newDinoInstance();
        speed = initialXSpeed;
        lives = 3;
        transitionTimer = 0;
        canKickDino = true;
        dinoInHole = false;
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
        // erase the canvas
        g.drawRect(0, 0, gc.getWidth(), gc.getHeight());
        
        // Render main game objects
        ground.render(g);
        dino.render(g);
        
        // Game info
        Printer p = new Printer(g, Color.white, 20, 0);
// Use for debugging
//        p.println("Dino: " + dino.getX() + " " + dino.getY());
//        p.println("Dino Speed: " + dino.getXSpeed() + " " + dino.getYSpeed());
//        p.println("Line: " + ground.getX());
        p.println("Lives: " + lives);
        p.println("Points: " + points);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        elapsedTime += delta;
        
        // ground speed doubles after 20 secs
        speed = initialXSpeed * (1 + ((float)elapsedTime)/20000);

        if (lives == 0) {
            // Game over
            transitionToStartGame(gc, sbg);
            return;
        }
        else {
            points = elapsedTime;
        }

        // Move the dino
        Input input = gc.getInput();
        Control.checkForExit(input);

        if (input.isKeyPressed(Input.KEY_UP) && canKickDino) {
            if (dino.isMovingOnY())
                canKickDino = false; // can't kick a moving Dino more than once
            dino.kickOnY(initialYSpeed, elapsedTime);
        }
        
        // If Dino is moving, then update its position and 
        // check if it hits the ground, or if it falls through a hole
        if (dino.isMovingOnY()) {
            MovablePoint nextPosition = Gravity.update(dino, elapsedTime);
            BoundingBox trajectory = new BoundingBox(dino, nextPosition);

            if (!dinoInHole && ground.intersects(trajectory)) {
                dino.stopAbove(ground);
                canKickDino = true;
                dinoInHole = false;
            }
            else {
                // Dino continues falling
                dino.update(nextPosition);
            }
            
            if (!dino.intersects(canvas)) {
                lives--; // dino dies
                if (lives > 0) {
                    // Dino gets a new life 
                    dino = newDinoInstance();
                    canKickDino = true;
                    dinoInHole = false;
                }
            }
        }
        else {
            /*
             * Dino doesn't move but he may still fall through a hole.
             * 
             * Dino looks down, if his gaze does not intersect the ground
             * then he is above a hole and falls into it.
             */
            BoundingBox lookDown = new BoundingBox(dino.getX(), dino.getY(), dino.getWidth(), 100);
            if (!ground.intersects(lookDown)) {
                
                // Dino starts falling and the user can't save him
                dino.kickOnY(MovablePoint.MIN_POSITIVE_Y_SPEED * 3, elapsedTime);
                canKickDino = false;
                dinoInHole = true;
            }
        }

        // Ground line moves left
        ground.left(speed * delta);
    }

    public int getID() {
        return Main.PLAY;
    }
    
    // Initialize new Dino after he loses a life
    private Dino newDinoInstance() throws SlickException {
        return new Dino(DINO_X_POSITION, ground.getY() - 1);
    }

    // Move to other game state
    private void transitionToStartGame(GameContainer gc, StateBasedGame sbg) 
            throws SlickException {
        transitionTimer++;
        
        if (transitionTimer / Main.TARGET_FRAME_RATE > 2)
            ((Main) sbg).startGame(gc);
    }
}
