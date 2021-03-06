package examples.simple;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

	public static final String gamename = "MyGameName";
	public static final int play = 0;
	public static final int xSize = 800;
	public static final int ySize = 600;

	public Main(String gamename) {
		super(gamename);
		this.addState(new Play());
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(play).init(gc, this);
		this.enterState(play);
	}

	public static void main(String[] args) {
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Main(gamename));
			appgc.setDisplayMode(xSize, ySize, false);
			appgc.setTargetFrameRate(30);
			appgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
