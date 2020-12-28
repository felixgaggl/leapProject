import com.leapmotion.leap.*;
import com.leapmotion.leap.Frame;

import java.awt.*;

class SampleListener extends Listener {
    private Logger logger;
    private String state;
    private boolean stateChanged;

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.setPolicy(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD);
        logger = new Logger();

        System.out.println(controller.devices().get(0));

        controller.setPaused(true);

        System.out.println(controller.devices().count());
        //System.out.println(controller.devices().get(1));
    }

    public void onFrame(Controller controller) {
        Frame frame = controller.frame();
        //palmPosition(device set up with cable to the back): (back/forth, up/down, left/right)
    /*
        if(frame.hands().count() != 0){
            if((frame.hands().rightmost().palmVelocity().getX() > 0 && frame.hands().rightmost().palmPosition().getX() < 0) || (frame.hands().rightmost().palmVelocity().getX() < 0 && frame.hands().rightmost().palmPosition().getX() > 0) ){
                Sample.pubtextLabel.setText("Hand is approaching");
            }else{
                Sample.pubtextLabel.setText("Hand is going away");
            }

            //System.out.println(frame.hands().rightmost().confidence());



            Sample.pubdistanceLabel.setText("Distance to mouse: " + (frame.hands().rightmost().palmPosition().getX())/10 + " cm");
            Sample.pubpanel.setBackground(Color.GREEN);
            Sample.pubgestureLabel.setText("Gesture: ");
            if(!frame.gestures().isEmpty()) {
                Sample.pubpanel.setBackground(Color.ORANGE);
                Sample.pubgestureLabel.setText("Gesture: " + frame.gestures().count());
            }

        }else{
            Sample.pubtextLabel.setText("NO Hand is approaching");
            Sample.pubdistanceLabel.setText("");
            Sample.pubpanel.setBackground(Color.RED);
        }
        */
        if(Sample.canWrite) {
            if (frame.hands().count() != 0) {
                if ((frame.hands().rightmost().palmPosition().getX() < 30)) {
                    Sample.pubtextLabel.setText("Hand is approaching mouse");
                    Sample.pubpanel.setBackground(Color.GREEN);
                    stateChanged = (state == "MOUSE" ? false : true);
                    state = "MOUSE";
                } else {
                    Sample.pubtextLabel.setText("Hand is on keyboard");
                    Sample.pubpanel.setBackground(Color.RED);
                    stateChanged = (state == "KEYBOARD" ? false : true);
                    state = "KEYBOARD";
                }
                Sample.pubdistanceLabel.setText("X Position: " + (frame.hands().rightmost().palmPosition().getX()) / 10 + " cm");
            } else {
                Sample.pubtextLabel.setText("NO Hand detected");
                Sample.pubdistanceLabel.setText("");
                Sample.pubpanel.setBackground(Color.BLACK);
            }
            if(stateChanged && state != null) logger.writeLog(state);
        }




        /*System.out.println("Frame id: " + frame.id()
                + ", timestamp: " + frame.timestamp()
                + ", hands: " + frame.hands().count()
                + ", handPosition: " + frame.hands().rightmost().palmPosition()
                + ", fingers: " + frame.fingers().count()
                + ", tools: " + frame.tools().count()
                + ", gestures " + frame.gestures().count());
               */
    }
}
