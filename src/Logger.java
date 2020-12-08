import java.io.File;
import java.io.IOException;

public class Logger {
    private File data;
    public Logger(){
       data  = new File("../logging/data.csv");
        try {
            data.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
