package Client_Java.utilities;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class UtilityMethods {


    public static boolean haveNullOrEmpty(String... strings){
        for (String str : strings) {
            if (str == null || str.trim().isEmpty()){
                return true;
            }
        }
        return false;
    }

    public static String generateRandomID() {
        return UUID.randomUUID().toString().substring(0, 18);
    }

    public static String getCurrentDateTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  dateFormat.format(date);
    }



}
