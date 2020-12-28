import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {
        try
        {
            Scanner scn = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket s = new Socket(ip, 3141);

            OutputStream raus = s.getOutputStream();
            PrintStream ps = new PrintStream(raus, true);


            // the following loop performs the exchange of
            // information between client and client handler
            while (true)
            {
                String tosend = scn.nextLine();
                ps.println(tosend);

                // If client sends exit,close this connection
                // and then break from the while loop
                if(tosend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing date or time as requested by client
            }

            // closing resources
            scn.close();
            raus.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
