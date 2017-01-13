package examples.bikeman;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

    public static final String gamename = "Bikeman";
    public static final int xSize = 800;
    public static final int ySize = 600;

    // State IDs
    public static final int START_GAME = 0;
    public static final int PLAY = 1;

    // Target frame rate
    public static final int TARGET_FRAME_RATE = 30;
    
    public Main(String gamename) {
        super(gamename);
    }

    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new StartGame());
        this.addState(new Play());
        startGame(gc);
    }

    public static void main(String[] args) {
        AppGameContainer appgc;
        try {
            appgc = new AppGameContainer(new Main(gamename));
            appgc.setDisplayMode(xSize, ySize, false);
            appgc.setTargetFrameRate(TARGET_FRAME_RATE);
            appgc.setShowFPS(false);
            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void startGame(GameContainer gc) throws SlickException {
        this.getState(START_GAME).init(gc, this);
        this.enterState(START_GAME);
    }

    public void playGame(GameContainer gc) throws SlickException {
        this.getState(PLAY).init(gc, this);
        this.enterState(PLAY);
    }
}
