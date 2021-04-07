import com.leapmotion.leap.*;
import com.leapmotion.leap.Frame;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


/**
 * Class extends the leapmotion listener, which is used to enable the connection to a leap motion controller and reading out the data
 * provides methods that get called on connection to the device and on every frame that is detected
 * detected gestures/ states are written to the handModel-instance, which is responsible for showing the current status
 */
public class SampleListener extends Listener {
    private boolean gestureTimer;

    /**
     * Method that gets called to connect to the leap motion controller and defines certain configurations
     * This includes which gestures are actually detected by the device and which parameters the gestures have to fulfill
     * gestureTimer is set to true, which indicates that gestures can be detected and there is no need to wait (gestures can only be detected once every second to avoid double recognition)
     *
     * @param controller
     */
    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.setPolicy(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);

        controller.config().setFloat("Gesture.KeyTap.MinDownVelocity", 40.0f);
        controller.config().setFloat("Gesture.KeyTap.HistorySeconds", .2f);
        controller.config().setFloat("Gesture.KeyTap.MinDistance", 1.0f);
        controller.config().save();


        gestureTimer = true;
    }

    /**
     * Method that gets called on every incoming frame from the leap motion controller
     * In this method it is defined which actions are taken when certain inputs by the controller are detected
     * This includes all the different Gestures and to every gesture there are some unique characteristics that need to be filtered in this section (velocity, radius, etc.)
     * GestureTimer is checked as well to ensure there are no multi-recognitions
     *
     * @param controller
     */
    public void onFrame(Controller controller) {
        Frame frame = controller.frame();

        //System.out.println("X: " + frame.hands().rightmost().palmPosition().getX() + "Y: " + frame.hands().rightmost().palmPosition().getY() + "Z: " + frame.hands().rightmost().palmPosition().getZ());

        if (Sample.handModel.getPosition() == HandState.NOSTATE || Sample.handModel.getPosition() == HandState.BETWEEN || Sample.handModel.getPosition() == HandState.KEYBOARD) {
            if (frame.hands().count() != 0) {
                if ((frame.hands().rightmost().palmPosition().getX() < 30)) {
                    Sample.handModel.setPosition(HandState.BETWEEN);
                } else {
                    Sample.handModel.setPosition(HandState.KEYBOARD);
                }

            } else {
                Sample.handModel.setPosition(HandState.NOSTATE);
            }
        }


        if (frame.gestures().count() != 0 && gestureTimer && Sample.handModel.getPosition() == HandState.KEYBOARD) {
            String gestureString = null;
            for (Gesture s : frame.gestures()) {
                // Swipe Gesture detection and all its forms
                if (s.type() == Gesture.Type.TYPE_SWIPE) {
                    SwipeGesture swipeGesture = new SwipeGesture(s);
                    System.out.println(swipeGesture.direction());
                    if (swipeGesture.direction().getX() > 0.65 && Math.abs(swipeGesture.direction().getZ()) < 0.50) {
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardSwipeRight);
                    } else if (swipeGesture.direction().getX() < -0.65 && Math.abs(swipeGesture.direction().getZ()) < 0.50) {
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardSwipeLeft);
                    } else if (swipeGesture.direction().getZ() > 0.65 && Math.abs(swipeGesture.direction().getX()) < 0.50) {
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardSwipeDown);
                    } else if (swipeGesture.direction().getZ() < -0.65 && Math.abs(swipeGesture.direction().getX()) < 0.50) {
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardSwipeUp);
                    }

                    // Circle Gesture detection
                } else if (s.type() == Gesture.Type.TYPE_CIRCLE) {
                    CircleGesture circleGesture = new CircleGesture(s);
                    //System.out.println(circleGesture.radius());
                    if (circleGesture.radius() > 30) {
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardCircle);
                    }

                    // KeyTap Gesture detection
                } else if (s.type() == Gesture.Type.TYPE_KEY_TAP) {
                    gestureDetectedTimer();
                    KeyTapGesture keyTapGesture = new KeyTapGesture(s);
                    Sample.handModel.setLastGesture(Gestures.KeyboardCircle);
                }

            }


        } else if (!gestureTimer) {

        }


    }

    /**
     * Starts a countdown of 1 second before the next gesture can be detected
     * this is realised with the timer class provided by java
     */
    private void gestureDetectedTimer() {
        gestureTimer = false;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                gestureTimer = true;
            }
        };
        long delay = TimeUnit.SECONDS.toMillis(1);
        Timer t = new Timer();
        t.schedule(task, delay);

    }
}
