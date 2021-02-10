import java.awt.*;
import java.awt.event.KeyEvent;


public class LoggerTest {
    public static void main(String[] args) throws AWTException {


        Robot r = new Robot();
        int keyCode = KeyEvent.VK_F;
        int keyCode1 = KeyEvent.VK_SHIFT;
        int keyCode2 = KeyEvent.VK_S;
        r.keyPress(keyCode);
        //r.keyPress(keyCode1);
        //r.keyPress(keyCode2);
        r.keyRelease(keyCode);
        //r.keyRelease(keyCode1);
        //r.keyRelease(keyCode2);

    }
}
