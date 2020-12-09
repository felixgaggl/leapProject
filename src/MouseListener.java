import com.leapmotion.leap.*;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;

import java.awt.*;
import java.util.Objects;


public class MouseListener extends Listener {
    private String state;
    private String newState;
    private boolean stateChanged;

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
        state = "START";


    }

    public void onFrame(Controller controller) {
        Frame frame = controller.frame();

        if(frame.isValid()) {

            ImageList images = frame.images();
            boolean handOn = false;
            for (Image image : images) {
                if(image.data()[1] > 50 || handOn == true){
                    handOn = true;
                    newState = "HANDON";
                }else{
                    newState = "HANDOFF";
                }



                int[] brightness = {0, 0, 0}; //An array to hold the rgba color components

                //System.out.println(image.data()[1]);

            }
            if(!Objects.equals(state, newState)){
                state = newState;
                System.out.println("State changed to: " + state);
                Mouse.pubtextLabel.setText(state);
                Mouse.pubpanel.setBackground(Objects.equals(state, "HANDON") ? Color.GREEN: Color.RED);
            }
        }

        if(frame.hands().count() != 0 && !Objects.equals(state, "HANDON")){
            //System.out.println("HAND DETECTED AND NOT ON DEVICE");
            if(Math.abs(frame.hands().rightmost().palmPosition().getX()) < 50 && Math.abs(frame.hands().rightmost().palmPosition().getZ()) < 50){
                Mouse.pubtextLabel.setText("HAND ABOVE MOUSE");
                Mouse.pubpanel.setBackground(Color.ORANGE);
                //System.out.println("HAND ABOVE MOUSE");
            }

        }else if(frame.hands().count() == 0 && Objects.equals(state, "HANDOFF")){
            Mouse.pubtextLabel.setText("HANDOFF");
            Mouse.pubpanel.setBackground(Color.RED);
        }

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
