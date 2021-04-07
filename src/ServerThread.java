import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * This class provides all the necessary function to receive input from the second leap motion controller, that is executed on a different machine (either virtual or actual)
 * Works by starting a new thread and waiting for incoming messages once the connection is established
 */
public class ServerThread extends Thread {
    final InputStream is;
    final DataOutputStream dos;
    final Socket s;

    /**
     * Method to start a new ServerThread which handles incoming data from the second leap implementation
     *
     * @param s   socket that was started in the Sample.java class, which is the start of the system
     * @param is  inputstream from the socket
     * @param dos
     */
    public ServerThread(Socket s, InputStream is, DataOutputStream dos) {
        this.s = s;
        this.is = is;
        this.dos = dos;
    }

    /**
     * Method that runs during the execution and actually implements what to do when certain incoming messages are detected
     * Mostly the incoming messages, which are strings get read and the forwarded to the handModel-instance, which represents the current state of the hand
     * The recognisable strings include states and gestures from the second leap motion controller
     * Writing something back is not necessary for this version of the system but can be realised
     */
    @Override
    public void run() {
        System.out.println("Thread running");
        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(is));
            System.out.println("Message?");
            String line = null;
            while (line != "Exit") {
                line = buff.readLine();
                //System.out.println(line);

                switch (line) {
                    case "HANDON":
                        Sample.handModel.setPosition(HandState.ONMOUSE);
                        break;

                    case "HANDOFF":
                        Sample.handModel.setPosition(HandState.NOSTATE);
                        break;

                    case "HANDABOVE":
                        Sample.handModel.setPosition(HandState.OVERMOUSE);
                        break;

                    case "AWAY":
                        Sample.handModel.setPosition(HandState.NOSTATE);
                        break;

                    case "SWIPELEFT":
                        Sample.handModel.setLastGesture(Gestures.MouseSwipeLeft);
                        break;

                    case "SWIPERIGHT":
                        Sample.handModel.setLastGesture(Gestures.MouseSwipeRight);
                        break;

                    case "SWIPEUP":
                        Sample.handModel.setLastGesture(Gestures.MouseSwipeUp);
                        break;

                    case "SWIPEDOWN":
                        Sample.handModel.setLastGesture(Gestures.MouseSwipeDown);
                        break;

                    case "CIRCLE":
                        Sample.handModel.setLastGesture(Gestures.MouseCircle);
                        break;

                    case "KEYTAP":
                        Sample.handModel.setLastGesture(Gestures.MouseKeytap);
                        break;

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
