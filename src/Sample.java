import com.leapmotion.leap.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


class Sample {
    public static JLabel pubtextLabel;
    public static JLabel pubdistanceLabel;
    public static JLabel pubgestureLabel;
    public static JPanel pubpanel;
    public static boolean canWrite = true;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {


        createGUI();


        try {
            setupInputSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();


        // Have the sample listener receive events from the controller
        controller.addListener(listener);






        //
        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);

    }

    private static void setupInputSocket() throws IOException {
        Socket s = null;
        serverSocket = new ServerSocket(3141);
        while (s == null)
        {
            s = null;

            try
            {
                // socket object to receive incoming client requests
                s = serverSocket.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                InputStream is = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ServerThread(s, is, dos);

                // Invoking the start() method
                t.start();

            }catch (Exception e){
            }
        }

    }



    private static void createGUI(){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);


        JLabel textLabel = new JLabel("NO Hand is approaching",SwingConstants.CENTER);
        JLabel distanceLabel = new JLabel("Distance to mouse:",SwingConstants.CENTER);
        JLabel gestureLabel = new JLabel("Gesture: ",SwingConstants.CENTER);
        pubtextLabel = textLabel;
        pubdistanceLabel = distanceLabel;
        pubgestureLabel = gestureLabel;

        //textLabel.setPreferredSize(new Dimension(300, 100));

        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(3,1);
        panel.setLayout(gl);
        panel.add(textLabel);
        panel.add(distanceLabel);
        panel.add(gestureLabel);

        window.getContentPane().add(panel, BorderLayout.CENTER);
        pubpanel = panel;

        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
    }
}