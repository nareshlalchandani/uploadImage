package com.marsplay.demo.utils;

import android.os.Environment;
import android.util.Log;

import com.marsplay.demo.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by naresh on 10/12/18.
 */

public class LogUtility {

    private static String TAG = LogUtility.class.getSimpleName();

    private static boolean IS_SHOW_LOG = BuildConfig.DEBUG;

    private final static int DEBUG = 1;
    private final static int VERBOSE = 2;
    private final static int ERROR = 3;
    private final static int WARNING = 4;
    private final static int INFO = 5;

    /**
     * Print error log message
     *
     * @param message, message that we have to print on log screen
     */
    public static void printErrorMsg(String message) {

        if (IS_SHOW_LOG) {
            Log.e(TAG, attachThreadId(message));
            //writeLogInFile(appendTagWithMsg(ERROR, message));
        }
    }

    /**
     * To print error log message
     *
     * @param TAG,     to identify from where log printed
     * @param message, message that we have to print on log screen
     */
    public static void printErrorMsg(String TAG, String message) {
        if (IS_SHOW_LOG) {
            Log.e(TAG, attachThreadId(message));
            //writeLogInFile(appendTagWithMsg(ERROR,TAG, message));
        }
    }

    /**
     * Print debug log message
     *
     * @param message, message that we have to print on log screen
     */
    public static void printDebugMsg(String message) {
        if (IS_SHOW_LOG) {
            Log.d(TAG, attachThreadId(message));
            //writeLogInFile(appendTagWithMsg(DEBUG,TAG, message));
        }
    }

    /**
     * To print debug log message
     *
     * @param TAG,     to identify from where log printed
     * @param message, message that we have to print on log screen
     */
    public static void printDebugMsg(String TAG, String message) {
        if (IS_SHOW_LOG) {
            Log.d(TAG, attachThreadId(message));
            //writeLogInFile(appendTagWithMsg(DEBUG,TAG, message));
        }
    }

    /**
     * Print info log message
     *
     * @param message, message that we have to print on log screen
     */
    public static void printInfoMsg(String message) {
        if (IS_SHOW_LOG) {
            Log.e(TAG, attachThreadId(message));
            //writeLogInFile(appendTagWithMsg(INFO,TAG, message));
        }
    }

    /**
     * Print info log message
     *
     * @param TAG,     to identify from where log printed
     * @param message, message that we have to print on log screen
     */
    public static void printInfoMsg(String TAG, String message) {
        if (IS_SHOW_LOG) {
            Log.i(TAG, attachThreadId(message));
            //writeLogInFile(appendTagWithMsg(INFO,TAG, message));
        }
    }

    /**
     * Print info log message
     *
     * @param str, message that we have to print on log screen
     * @return String , return string message with current thread id
     */
    private static String attachThreadId(String str) {
        return "" + Thread.currentThread().getId() + " " + str;
    }

    /**
     * To write log message into external storage
     *
     * @param logstr, write log on external storage
     */
    private static void writeLogInFile(StringBuilder logstr) {

        //final long MAX_FILE_LIMIT = 20480; //20971520 B//20 MB
        final long MAX_FILE_LIMIT = 4096; //4194304 B//04 MB

        try {
            String filePath = Environment.getExternalStorageDirectory() + File.separator + AppConstant.APP_NAME;

            File dirFile = new File(filePath);
            dirFile.mkdirs();

            File newFile = new File(filePath, AppConstant.APP_NAME + "_log.txt");
            long filebytes = newFile.length();
            long kbytes = (filebytes / 1024);

            if (kbytes > MAX_FILE_LIMIT) {
                newFile.delete();
            }

            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            FileWriter fw = new FileWriter(newFile, true);
            BufferedWriter bw = new BufferedWriter(fw);

            //append data into file
            bw.append(logstr.toString());
            //bw.write(String.format("%1s [%2s]:%3s\r\n", getDateTimeStamp(), logstr.toString()));
            bw.newLine();
            bw.flush();

            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To write log message into external storage
     *
     * @param mode, write log on external storage
     * @param tag,  write log on external storage
     * @param msg,  write log on external storage
     */
    private static StringBuilder appendTagWithMsg(int mode, String tag, String msg) {

        StringBuilder strBuilder = new StringBuilder();
        switch (mode) {
            case DEBUG:
                strBuilder.append("D/");
                break;
            case VERBOSE:
                strBuilder.append("V/");
                break;
            case ERROR:
                strBuilder.append("E/");
                break;
            case WARNING:
                strBuilder.append("W/");
                break;
            case INFO:
                strBuilder.append("I/");
                break;
            default:
                break;
        }

        strBuilder.append(tag);
        strBuilder.append(" ");
        strBuilder.append(":");
        strBuilder.append(" ");
        strBuilder.append(msg);
        strBuilder.append(" \n ");

        return strBuilder;
    }
}
