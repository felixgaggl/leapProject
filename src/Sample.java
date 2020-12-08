import com.leapmotion.leap.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


class Sample {
    public static JLabel pubtextLabel;
    public static JLabel pubdistanceLabel;
    public static JLabel pubgestureLabel;
    public static JPanel pubpanel;
    public static void main(String[] args) {


        createGUI();


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