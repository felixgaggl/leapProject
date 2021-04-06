import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


/**
 * Class that provides the logger functionality, which is writing an entry into a csv-file, containing timestamp and gesture-/ position- change
 */
public class Logger {
    private File data;
    private PrintWriter pw;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");


    /**
     * constructor of the logger which opens the file, or creates it if it doesnt exist
     * with the PrintWriter pw we can always write to the file then
     */
    public Logger(){
       data  = new File("./logging/data.csv");
        //System.out.println(Arrays.toString(data.list()));
        try {
            data.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            pw = new PrintWriter(new FileOutputStream(data, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * method that allows us to write to the opened file
     * @param a String that gets written into the logfile, which contains either gesture or position of hand
     */
    public void writeLog(String a){
       StringBuilder sb = new StringBuilder();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
       sb.append(sdf.format(timestamp));
       sb.append(';');
       sb.append(a);
        sb.append(';');
       sb.append("VALID");
       sb.append('\n');
       pw.write(sb.toString());

       pw.flush();

    }


}
