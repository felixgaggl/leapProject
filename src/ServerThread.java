import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    final InputStream is;
    final DataOutputStream dos;
    final Socket s;

    public ServerThread(Socket s, InputStream is, DataOutputStream dos) {
        this.s = s;
        this.is = is;
        this.dos = dos;
    }

    @Override
    public void run() {
        System.out.println("Thread running");
        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(is));
            System.out.println("Message?");
            String line = null;
            while (line != "Exit") {
                line = buff.readLine();
                System.out.println(line);

                switch (line){
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
