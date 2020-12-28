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
                        Sample.pubtextLabel.setText("HANDON");
                        Sample.pubpanel.setBackground(Color.PINK);
                        Sample.canWrite = false;
                        break;

                    case "HANDOFF":
                        Sample.pubtextLabel.setText("HANDOFF");
                        Sample.pubpanel.setBackground(Color.PINK);
                        Sample.canWrite = false;
                        break;

                    case "HANDABOVE":
                        Sample.pubtextLabel.setText("HANDABOVE");
                        Sample.pubpanel.setBackground(Color.PINK);
                        Sample.canWrite = false;
                        break;

                    case "AWAY":
                        Sample.canWrite = true;
                        break;

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
