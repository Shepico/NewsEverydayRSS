package ru.shepico.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerMy {
    private static Logger log = Logger.getLogger(LoggerMy.class.getName()); //логер

    public static void settingLog() {
        try {
            Handler exHandlerFile = new FileHandler("logEx_rss.txt", 1000000, 3, true);
            exHandlerFile.setLevel(Level.SEVERE);
            log.addHandler(exHandlerFile);
            Handler infoHandlerFile = new FileHandler("logInfo_rss.txt", 1000000, 5, true);
            infoHandlerFile.setLevel(Level.INFO);
            log.addHandler(infoHandlerFile);
            log.setUseParentHandlers(false); //запрет передачи сообщений верхнему уровню
        } catch (IOException eio) {
            exLog(eio);
        }
    }

    public static void infoLog(String message){
        log.info(message);
    }

    public static void exLog(Exception e){
        log.log(Level.SEVERE, "Exception", e);
    }
}


