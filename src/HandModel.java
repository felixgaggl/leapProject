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
        //TODO write what certain gestures do
    }

    public Gestures getLastGesture(){
        return lastGesture;
    }

    public HandState getPosition(){
        return position;
    }

}
