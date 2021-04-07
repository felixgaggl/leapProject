import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Class that defines the functions that get executed when certain gestures are detected
 */
public class GestureActions {
    /**
     * Method that gets called with a specific gesture, then executes a functionality
     * for now this is all demo functions but this can be expanded
     *
     * @param gesture Detected gesture from one of the two leap devices
     * @throws AWTException
     */
    public static void handleGesture(Gestures gesture) throws AWTException {
        Robot r = new Robot();
        int keyCode;
        int keyCode1;
        int keyCode2;
        switch (gesture) {
            case MouseCircle:
                // Demo copy selection
                /*keyCode = KeyEvent.VK_CONTROL;
                keyCode1 = KeyEvent.VK_C;

                r.keyPress(keyCode);
                r.keyPress(keyCode1);

                r.keyRelease(keyCode);
                r.keyRelease(keyCode1);*/
                break;
            case MouseKeytap:
                // Demo paste selection
                /*keyCode = KeyEvent.VK_CONTROL;
                keyCode1 = KeyEvent.VK_V;

                r.keyPress(keyCode);
                r.keyPress(keyCode1);

                r.keyRelease(keyCode);
                r.keyRelease(keyCode1);*/
                break;
            case MouseSwipeUp:
                // Demo screenshot action
                keyCode = KeyEvent.VK_WINDOWS;
                keyCode1 = KeyEvent.VK_SHIFT;
                keyCode2 = KeyEvent.VK_S;

                r.keyPress(keyCode);
                r.keyPress(keyCode1);
                r.keyPress(keyCode2);

                r.keyRelease(keyCode);
                r.keyRelease(keyCode1);
                r.keyRelease(keyCode2);
                break;
            case MouseSwipeDown:
                break;
            case MouseSwipeRight:
                break;
            case MouseSwipeLeft:
                break;
            case KeyboardCircle:
                // Demo to show how a program gets started when
                try {
                    Runtime.getRuntime().exec("Notepad");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case KeyboardKeytap:
                break;
            case KeyboardSwipeUp:
                break;
            case KeyboardSwipeDown:
                // Demo minimize window that is currently selected
                keyCode = KeyEvent.VK_ALT;
                keyCode1 = KeyEvent.VK_ESCAPE;
                r.keyPress(keyCode);
                r.keyPress(keyCode1);

                r.keyRelease(keyCode);
                r.keyRelease(keyCode1);
                break;
            case KeyboardSwipeLeft:
                break;
            case KeyboardSwipeRight:
                break;
            case NONE:
                break;
        }
    }
}
