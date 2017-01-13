package examples.simple;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {

	int ID = 0;
	public Image Cory = null;

	public Play() {

	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Cory = new Image("res/cory.png");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.drawString("Cory's Adventure", 300, 50);
		Cory.draw(300, 300);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}

	public int getID() {
		return Main.play;
	}
}
