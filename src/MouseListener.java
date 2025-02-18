import com.leapmotion.leap.*;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;

import java.awt.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Class extends the leapmotion listener, which is used to enable the connection to a leap motion controller and reading out the data
 * provides methods that get called on connection to the device and on every frame that is detected
 * detected gestures/ states are written to the main part of the program (started with Sample.java) via sockets by calling Mouse.ps.println()
 */
public class MouseListener extends Listener {
    private String state;
    private String newState;
    private boolean stateChanged;
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
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
        controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);

        controller.config().setFloat("Gesture.KeyTap.MinDownVelocity", 40.0f);
        controller.config().setFloat("Gesture.KeyTap.HistorySeconds", .2f);
        controller.config().setFloat("Gesture.KeyTap.MinDistance", 1.0f);
        controller.config().save();

        state = "START";
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

        if(frame.isValid()) {

            ImageList images = frame.images();
            boolean handOn = false;
            //check image data for light levels (indicates whether a hand is directly on the sensor or not)
            for (Image image : images) {
                if(image.data()[1] > 50 || handOn == true){
                    handOn = true;
                    newState = "HANDON";
                }else{
                    newState = "HANDOFF";
                }
            }

            if(frame.hands().count() != 0 && !Objects.equals(newState, "HANDON")){
                //System.out.println("HAND DETECTED AND NOT ON DEVICE");
                if(Math.abs(frame.hands().rightmost().palmPosition().getX()) < 100 && Math.abs(frame.hands().rightmost().palmPosition().getZ()) < 100){
                    newState = "HANDABOVE";
                    //System.out.println("HAND ABOVE MOUSE");
                }

            }else if(frame.hands().count() == 0 && Objects.equals(newState, "HANDOFF")){
                newState = "AWAY";

            }

            if(frame.gestures().count() != 0 && gestureTimer) {
                String gestureString = null;
                for (Gesture s : frame.gestures()) {
                    // Swipe Gesture detection and all its forms
                    if(s.type() == Gesture.Type.TYPE_SWIPE){
                        SwipeGesture swipeGesture = new SwipeGesture(s);
                        System.out.println(swipeGesture.direction());
                        if(swipeGesture.direction().getX() > 0.65 && Math.abs(swipeGesture.direction().getZ()) < 0.50){
                            gestureDetectedTimer();
                            swipeRight();
                        }else if(swipeGesture.direction().getX() < -0.65 && Math.abs(swipeGesture.direction().getZ()) < 0.50){
                            gestureDetectedTimer();
                            swipeLeft();
                        }else if(swipeGesture.direction().getZ() > 0.65 && Math.abs(swipeGesture.direction().getX()) < 0.50){
                            gestureDetectedTimer();
                            swipeDown();
                        }else if(swipeGesture.direction().getZ() < -0.65 && Math.abs(swipeGesture.direction().getX()) < 0.50){
                            gestureDetectedTimer();
                            swipeUp();
                        }

                    // Circle Gesture detection
                    }else if(s.type() == Gesture.Type.TYPE_CIRCLE){
                        CircleGesture circleGesture = new CircleGesture(s);
                        System.out.println(circleGesture.radius());
                        if(circleGesture.radius() > 30){
                            gestureDetectedTimer();
                            drawCircle();
                        }

                    // KeyTap Gesture detection
                    }else if(s.type() == Gesture.Type.TYPE_KEY_TAP){
                        gestureDetectedTimer();
                        KeyTapGesture keyTapGesture = new KeyTapGesture(s);
                        keyTap();
                    }

                }

                //Mouse.pubgestureLabel.setText(gestureString);

            }else if(!gestureTimer){

            }else{
                //Mouse.pubgestureLabel.setText("");
            }





            //only if the recognised state differs from the old one we write to the main program to avoid overcommunication
            if(!Objects.equals(state, newState)){
                state = newState;
                System.out.println("HandState changed to: " + state);
                Mouse.ps.println(state);
                //Mouse.pubtextLabel.setText((state));
            }
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
                System.out.println("Times up");
                //Mouse.pubtextLabel.setText("Timer is up, i can receive more gestures");
            }
        };
        long delay = TimeUnit.SECONDS.toMillis(1);
        Timer t = new Timer();
        t.schedule(task, delay);

    }

    private void swipeDown() {
        System.out.println("Swipe down");
        //Mouse.pubgestureLabel.setText("Swipe Down detected");
        Mouse.ps.println("SWIPEDOWN");

    }

    private void swipeUp() {
        System.out.println("Swipe up");
        //Mouse.pubgestureLabel.setText("Swipe Up detected");
        Mouse.ps.println("SWIPEUP");
    }

    private void swipeRight() {
        System.out.println("Swipe right");
        //Mouse.pubgestureLabel.setText("Swipe Right detected");
        Mouse.ps.println("SWIPERIGHT");
    }

    private void swipeLeft() {
        System.out.println("Swipe left");
        //Mouse.pubgestureLabel.setText("Swipe Left detected");
        Mouse.ps.println("SWIPELEFT");
    }

    private void drawCircle() {
        System.out.println("Circle Drawn");
        //Mouse.pubgestureLabel.setText("Circle detected");
        Mouse.ps.println("CIRCLE");
    }
    private void keyTap() {
        //Mouse.pubgestureLabel.setText("KEY TAP detected");
        Mouse.ps.println("KEYTAP");
    }

}
