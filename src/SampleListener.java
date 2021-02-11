import com.leapmotion.leap.*;
import com.leapmotion.leap.Frame;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

class SampleListener extends Listener {
    private boolean gestureTimer;


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

    public void onFrame(Controller controller) {
        Frame frame = controller.frame();

        if(Sample.handModel.getPosition() == HandState.NOSTATE){
            if (frame.hands().count() != 0) {
                if ((frame.hands().rightmost().palmPosition().getX() < 30)) {
                    Sample.handModel.setPosition(HandState.BETWEEN);
                } else {
                    Sample.handModel.setPosition(HandState.KEYBOARD);
                }
                //Sample.pubdistanceLabel.setText("X Position: " + (frame.hands().rightmost().palmPosition().getX()) / 10 + " cm");
            } else {
                Sample.handModel.setPosition(HandState.NOSTATE);
            }
        }



        if(frame.gestures().count() != 0 && gestureTimer && Sample.handModel.getPosition() == HandState.KEYBOARD) {
            String gestureString = null;
            for (Gesture s : frame.gestures()) {
                // Swipe Gesture detection and all its forms
                if(s.type() == Gesture.Type.TYPE_SWIPE){
                    SwipeGesture swipeGesture = new SwipeGesture(s);
                    System.out.println(swipeGesture.direction());
                    if(swipeGesture.direction().getX() > 0.65 && Math.abs(swipeGesture.direction().getZ()) < 0.50){
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardSwipeRight);
                    }else if(swipeGesture.direction().getX() < -0.65 && Math.abs(swipeGesture.direction().getZ()) < 0.50){
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardSwipeLeft);
                    }else if(swipeGesture.direction().getZ() > 0.65 && Math.abs(swipeGesture.direction().getX()) < 0.50){
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardSwipeDown);
                    }else if(swipeGesture.direction().getZ() < -0.65 && Math.abs(swipeGesture.direction().getX()) < 0.50){
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardSwipeUp);
                    }

                    // Circle Gesture detection
                }else if(s.type() == Gesture.Type.TYPE_CIRCLE){
                    CircleGesture circleGesture = new CircleGesture(s);
                    System.out.println(circleGesture.radius());
                    if(circleGesture.radius() > 30){
                        gestureDetectedTimer();
                        Sample.handModel.setLastGesture(Gestures.KeyboardCircle);
                    }

                    // KeyTap Gesture detection
                }else if(s.type() == Gesture.Type.TYPE_KEY_TAP){
                    gestureDetectedTimer();
                    KeyTapGesture keyTapGesture = new KeyTapGesture(s);
                    Sample.handModel.setLastGesture(Gestures.KeyboardCircle);
                }

            }

            //Mouse.pubgestureLabel.setText(gestureString);

        }else if(!gestureTimer){
            Mouse.pubpanel.setBackground(Color.CYAN);
            Mouse.pubtextLabel.setText("Timer running");
        }



    }


    // Starts a countdown of 1 second before the next gesture can be detected
    private void gestureDetectedTimer() {
        gestureTimer = false;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                gestureTimer = true;
                //Mouse.pubtextLabel.setText("Timer is up, i can receive more gestures");
            }
        };
        long delay = TimeUnit.SECONDS.toMillis(1);
        Timer t = new Timer();
        t.schedule(task, delay);

    }
}
