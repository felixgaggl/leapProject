import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Logger {
    private File data;
    private PrintWriter pw;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");



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
