package com.samsung.muhammad.polisi.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by dody on 11/17/17.
 */

public class FileUtil {
    private static FileUtil ourInstance = new FileUtil();
    private final String APP_FOLDER = "/laporpolisi";
    private final String APP_FOLDER_TEMP = "/laporpolisi/temp";
    private final String APP_FOLDER_LOG = "/laporpolisi/log";
    private final String APP_SPLASH_FILE = "/laporpolisi/background";
    private final String APP_CACHE_FILE = "/laporpolisi/cache";

    private final String TEMP_PREFIX = "temp";
    private final String TEMP_SUFFIX = ".tmp";

    public String TEMP_FOLDER_PATH;
    public String APP_FOLDER_PATH;
    public String LOG_FOLDER_PATH;
    public String SPLASH_FILE_PATH;
    public String CACHE_FILE_PATH;

    private FileUtil() {
        APP_FOLDER_PATH = Environment.getExternalStorageDirectory().toString() + APP_FOLDER;
        TEMP_FOLDER_PATH = Environment.getExternalStorageDirectory().toString() + APP_FOLDER_TEMP;
        LOG_FOLDER_PATH = Environment.getExternalStorageDirectory().toString() + APP_FOLDER_LOG;
        SPLASH_FILE_PATH = Environment.getExternalStorageDirectory().toString() + APP_SPLASH_FILE;
        CACHE_FILE_PATH = Environment.getExternalStorageDirectory().toString() + APP_CACHE_FILE;

        File storageDir = new File(APP_FOLDER_PATH);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        storageDir = new File(TEMP_FOLDER_PATH);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        storageDir = new File(LOG_FOLDER_PATH);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        storageDir = new File(CACHE_FILE_PATH);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
    }

    public static FileUtil getInstance() {
        return ourInstance;
    }

    public boolean isSplashFileExist(Context context) {
        File splash = new File(SPLASH_FILE_PATH);
        return splash.exists();
    }

    public File createTempFile(Context context) {
        return createTempFile(context, TEMP_SUFFIX);
    }

    public File createTempFile(Context context, String suffix) {
        File storageDir = new File(TEMP_FOLDER_PATH);

        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File file = null;
        try {
            file = File.createTempFile(TEMP_PREFIX, suffix, storageDir);
        } catch (IOException e) {
            e.printStackTrace();
            File detro = new File(context.getCacheDir(), TEMP_FOLDER_PATH);
            if (!detro.exists()) {
                detro.mkdirs();
            }
            try {
                file = File.createTempFile(TEMP_PREFIX, suffix, detro);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return file;
    }

    public void deleteTempFiles() {
        File dir = new File(TEMP_FOLDER_PATH);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                File file = new File(dir, children[i]);
                String path = file.getAbsolutePath();
                String filename = path.substring(path.lastIndexOf("/") + 1);
                if (filename.startsWith(TEMP_PREFIX)) {
                    file.delete();
                }
            }
        }
    }
}
