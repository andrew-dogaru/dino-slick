package dino;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Game start menu and instructions.
 */
public class StartGame extends BasicGameState {

	private Image backGround;
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
    	backGround = new Image("res/dino-background.jpg");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        backGround.draw();
    	Printer p = new Printer(g, Color.black, 160, 140);
        p.println("Press Space to start.");
        p.println("Press Ctrl+C at any time to exit the game.");
        p.println("Press Up Arrow to kick Dino up so he doesn't fall in a hole.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        Input input = gc.getInput();
        Control.checkForExit(input);

        if (Control.isKeySpace(input)) {
            ((Main) game).playGame(gc);
        }
    }

    @Override
    public int getID() {
        return Main.START_GAME;
    }
}
