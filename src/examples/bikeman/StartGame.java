package examples.bikeman;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartGame extends BasicGameState {
    private Image backGround;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        backGround = new Image("res/biker-1440x900.jpg");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        backGround.draw();
        g.setColor(Color.white);
        g.drawString("Click any mouse button to start", 200, 200);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        Input input = gc.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) ||
            input.isMousePressed(Input.MOUSE_MIDDLE_BUTTON) ||
            input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            ((Main) game).playGame(gc);
        }
    }

    @Override
    public int getID() {
        return Main.START_GAME;
    }
}
