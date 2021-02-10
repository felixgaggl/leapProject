import java.awt.*;

public class HandModel {
    private HandState position;
    private Gestures lastGesture;
    private Logger logger;




    public HandModel(){
        position = HandState.NOSTATE;
        lastGesture = Gestures.NONE;
        logger = new Logger();
    }

    public void setPosition(HandState position) {
        if(this.position != position){
            this.position = position;
            logger.writeLog(position.toString());
        }
    }

    public void setLastGesture(Gestures lastGesture) {
        this.lastGesture = lastGesture;
        logger.writeLog(lastGesture.toString());
        try {
            GestureActions.handleGesture(lastGesture);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public Gestures getLastGesture(){
        return lastGesture;
    }

    public HandState getPosition(){
        return position;
    }



}
