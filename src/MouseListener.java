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

        System.out.println(controller.devices().count());
        System.out.println(controller.failedDevices().count());
        //System.out.println(controller.devices().get(1));


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
            }

            if(frame.hands().count() != 0 && !Objects.equals(newState, "HANDON")){
                //System.out.println("HAND DETECTED AND NOT ON DEVICE");
                if(Math.abs(frame.hands().rightmost().palmPosition().getX()) < 50 && Math.abs(frame.hands().rightmost().palmPosition().getZ()) < 50){
                    newState = "HANDABOVE";
                    //System.out.println("HAND ABOVE MOUSE");
                }

            }else if(frame.hands().count() == 0 && Objects.equals(newState, "HANDOFF")){
                newState = "AWAY";

            }







            if(!Objects.equals(state, newState)){
                state = newState;
                System.out.println("State changed to: " + state);
                Mouse.ps.println(state);
            }
        }



    }


}
