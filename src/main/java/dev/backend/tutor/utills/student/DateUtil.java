package dev.backend.tutor.utills.student;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtil {
    public static String currentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf1.format(timestamp);
    }
}
