package jo2seo.aomd.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtils {
    public static LocalDateTime milliToDateTime(long milli) {
        return LocalDateTime.ofEpochSecond(milli, 0, ZoneOffset.of("+09:00"));
    }
}
