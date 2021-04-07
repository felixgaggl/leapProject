import com.leapmotion.leap.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class that starts the system by creating all necessary Communication-objects (input from second leap and input from actual primary leap)
 * To be executed at the beginning of every system start
 */
public class Sample {
    /*public static JLabel pubtextLabel;
    public static JLabel pubdistanceLabel;
    public static JLabel pubgestureLabel;
    public static JPanel pubpanel;
    */
    public static boolean canWrite = true;
    private static ServerSocket serverSocket;
    public static HandModel handModel;

    /**
     * Starting point of every system execution
     * Creates the handModel, which represents the current actual state of the interacting hand
     * setupInputSocket() gets called to initialise the communication to the second leap motion controller
     * other parts setup the Listener which handles the input of the first leap motion controller and when enter is pressed the execution will be stopped
     *
     * @param args
     */
    public static void main(String[] args) {

        handModel = new HandModel();


        try {
            setupInputSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("here");


        // create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();


        // have the sample listener receive events from the controller
        controller.addListener(listener);

        // keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // remove the sample listener when done
        controller.removeListener(listener);

    }

    /**
     * Starts a new socket on port 3141, which listens to incoming messages from the second leap motion controller, that is started elsewhere
     * incoming communication is handled on a different thread, so that it is not blocking other executions
     *
     * @throws IOException
     */
    private static void setupInputSocket() throws IOException {
        Socket s = null;
        serverSocket = new ServerSocket(3142);
        while (s == null) {
            s = null;

            try {
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

            } catch (Exception e) {
            }
        }

    }


    /**
     * Deprecated method, that was used to visualise the input/ status of the hand, but was replaced with actual functions that get executed in the handModel object
     * Can be adapted and changed and reactivated for development purposes or if system requirements change
     *
     * @deprecated
     */
    private static void createGUI() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);


        JLabel textLabel = new JLabel("NO Hand is approaching", SwingConstants.CENTER);
        JLabel distanceLabel = new JLabel("Distance to mouse:", SwingConstants.CENTER);
        JLabel gestureLabel = new JLabel("Gesture: ", SwingConstants.CENTER);
        //pubtextLabel = textLabel;
        //pubdistanceLabel = distanceLabel;
        //pubgestureLabel = gestureLabel;


        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(3, 1);
        panel.setLayout(gl);
        panel.add(textLabel);
        panel.add(distanceLabel);
        panel.add(gestureLabel);

        window.getContentPane().add(panel, BorderLayout.CENTER);
        //pubpanel = panel;

        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
    }
}