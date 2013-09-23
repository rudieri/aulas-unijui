package com.crepz.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author c90
 */
public class Log {

    private static final File filoLog;
    private static NivelLog nivelLog = NivelLog.DEBUG;

    public static void setNivelLog(NivelLog nivelLog) {
        Log.nivelLog = nivelLog;
    }

    static {
        File mk;
        SimpleDateFormat formatAnoMes = new SimpleDateFormat("yyyy-MM");
        mk = new File("log/" + formatAnoMes.format(new Date()));
        if (!mk.exists()) {
            mk.mkdirs();
        }


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        filoLog = new File(mk, format.format(new Date().getTime()) + ".txt");
        if (!filoLog.exists()) {
            try {

                filoLog.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void info(String info) {
        if (nivelLog.getNivel() > NivelLog.INFO.getNivel()) {
            return;
        }
        try {
            try (FileWriter fileWriter = new FileWriter(filoLog, true)) {
                write(info, fileWriter);
            }
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void debug(String debug) {
        if (nivelLog.getNivel() > NivelLog.DEBUG.getNivel()) {
            return;
        }
        try {
            try (FileWriter fileWriter = new FileWriter(filoLog, true)) {
                write(debug, fileWriter);
            }
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void error(String error, Throwable tw) {
        if (nivelLog.getNivel() > NivelLog.ERROR.getNivel()) {
            return;
        }
        try {
            try (FileWriter fileWriter = new FileWriter(filoLog, true)) {
                write(error, fileWriter);
                fileWriter.append(tw.getMessage()).append('\n');
                StackTraceElement[] stackTrace = tw.getStackTrace();
                for (int i = 0; i < stackTrace.length; i++) {
                    write(stackTrace[i].toString(), fileWriter);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void write(String txt, FileWriter fileWriter) throws IOException {
        fileWriter.append(txt).append('\n');
    }
}
