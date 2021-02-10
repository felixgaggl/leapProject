import java.awt.*;
import java.awt.event.KeyEvent;

public class GestureActions {
    public static void handleGesture(Gestures gesture) throws AWTException {
        Robot r = new Robot();
        int keyCode;
        int keyCode1;
        int keyCode2;
        switch (gesture) {
            case MouseCircle:
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
            case MouseKeytap:
                break;
            case MouseSwipeUp:
                break;
            case MouseSwipeDown:
                break;
            case MouseSwipeRight:
                break;
            case MouseSwipeLeft:
                break;
            case KeyboardCircle:
                break;
            case KeyboardKeytap:
                break;
            case KeyboardSwipeUp:
                break;
            case KeyboardSwipeDown:
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
