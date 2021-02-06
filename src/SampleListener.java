import com.leapmotion.leap.*;
import com.leapmotion.leap.Frame;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

class SampleListener extends Listener {
    private boolean gestureTimer;


    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.setPolicy(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD);
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
    }


    // Starts a countdown of 1 second before the next gesture can be detected
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
}
