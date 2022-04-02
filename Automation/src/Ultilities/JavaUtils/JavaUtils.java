package Ultilities.JavaUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JavaUtils {
    public static String getCurrentDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("M-d_HHmmss");
        Date date = new Date();
        return formatter.format(date);
    }
}
