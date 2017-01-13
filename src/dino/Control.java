package dino;

import org.newdawn.slick.Input;

/**
 * Just some functions for checking the user input.
 */
public class Control {
    
    /**
     * Check if the user issued the exit command.
     * @param input
     */
    public static void checkForExit(Input input) {
        if (input.isKeyDown(Input.KEY_LCONTROL) && input.isKeyDown(Input.KEY_C)) {
            System.exit(0);
        }
    }

    /**
     * Check if the user pressed any mouse button.
     * @param input
     * @return true if the user pressed any mouse button
     */
    public static boolean anyMouseButton(Input input) {
        return (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) ||
                input.isMousePressed(Input.MOUSE_MIDDLE_BUTTON) ||
                input.isMousePressed(Input.MOUSE_RIGHT_BUTTON));
    }

    /**
     * Check if the user pressed the space key.
     * @param input
     * @return true if the user pressed the space bar
     */
    public static boolean isKeySpace(Input input) {
        return input.isKeyDown(Input.KEY_SPACE);
    }
}
