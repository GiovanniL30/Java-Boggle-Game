package Client_Java.utilities;

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



}
