import com.leapmotion.leap.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class Mouse {
    public static JLabel pubtextLabel;
    public static JLabel pubdistanceLabel;
    public static JLabel pubgestureLabel;
    public static JPanel pubpanel;
    public static PrintStream ps;

    public static void main(String[] args) {

        // Create a sample listener and controller
        MouseListener listener = new MouseListener();
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        //createGUI();




        try {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, 3141);
            OutputStream raus = null;
            raus = s.getOutputStream();
            ps = new PrintStream(raus, true);
        } catch (IOException e) {
            e.printStackTrace();
        }




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


    private static void createGUI(){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);


        JLabel textLabel = new JLabel("NO Hand is approaching",SwingConstants.CENTER);
        //JLabel distanceLabel = new JLabel("Distance to mouse:",SwingConstants.CENTER);
        JLabel gestureLabel = new JLabel("Gesture: ",SwingConstants.CENTER);
        pubtextLabel = textLabel;
        //pubdistanceLabel = distanceLabel;
        pubgestureLabel = gestureLabel;

        //textLabel.setPreferredSize(new Dimension(300, 100));

        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(3,1);
        panel.setLayout(gl);
        panel.add(textLabel);
        //panel.add(distanceLabel);
        panel.add(gestureLabel);

        window.getContentPane().add(panel, BorderLayout.CENTER);
        pubpanel = panel;

        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
    }
}
