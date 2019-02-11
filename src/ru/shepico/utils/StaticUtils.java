package ru.shepico.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class StaticUtils {

    //const
    public static final int WIDTH_PRG = 325;
    public static final int HEIGHT_PRG = 700;
    public static final String VERSION = "0.1.190211";
    public static final String BUG = "01"; // От пользователей
    public static final String NAME_PRG = "News RSS";
    //
    private static java.util.Locale locale = java.util.Locale.US;

    //из строки в дату
    public static LocalDateTime convertStringToDate(String pubDate) {
        if (pubDate.length() > 26) { //18 Dec 2018 05:25:00 +0000
            pubDate = pubDate.substring(5);
        }
        String pattern = "dd MMM yyyy HH:mm:ss xxxx";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        LocalDateTime dateLocal = LocalDateTime.parse(pubDate, formatter);
        return dateLocal;
    }
}
