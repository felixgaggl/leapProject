import java.awt.*;

/**
 * Class that represent the state of the interacting hand
 * this includes position and gestures
 * this class also includes the forwarding of detected changes to the logger and to the GestureAction.java-instance, which actually performs functions
 */
public class HandModel {
    private HandState position;
    private Gestures lastGesture;
    private Logger logger;


    /**
     * Constructor of the HandModel-Class, that gets only called once
     * sets the position and lastgesture to generic start-values and starts a new logger
     */
    public HandModel() {
        position = HandState.NOSTATE;
        lastGesture = Gestures.NONE;
        logger = new Logger();
    }

    /**
     * sets the current position to a new value if there was a change and then the logger is also called
     *
     * @param position
     */
    public void setPosition(HandState position) {
        if (this.position != position) {
            this.position = position;
            logger.writeLog(position.toString());
        }
    }

    /**
     * sets the lastGesture to a new value, and the GestureAction-method handleGesture is called to actually get a functionality out of the gesture
     * the logger gets also called
     *
     * @param lastGesture
     */
    public void setLastGesture(Gestures lastGesture) {
        this.lastGesture = lastGesture;
        logger.writeLog(lastGesture.toString());
        try {
            GestureActions.handleGesture(lastGesture);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return lastGesture, which represents the last detected Gesture
     */
    public Gestures getLastGesture() {
        return lastGesture;
    }

    /**
     * @return position, which represents the acutal position of the interacting hand
     */
    public HandState getPosition() {
        return position;
    }


}
