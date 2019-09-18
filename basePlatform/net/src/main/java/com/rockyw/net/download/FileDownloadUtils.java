package com.rockyw.net.download;

import java.io.File;
import java.io.IOException;

/**
 *
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public class FileDownloadUtils {

    /**
     * 创建目录
     * @param directory
     * @return
     */
    public static boolean mkdirs(File directory) {
        try {
            forceMkdir(directory);
            return true;
        } catch (IOException e){
        }
        return false;
    }

    private static void forceMkdir(File directory) throws IOException {
        if (directory.exists()) {

            if (!directory.isDirectory()) {
                String message =
                        "File "
                                + directory
                                + " exists and is "
                                + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else {
            if (!directory.mkdirs()) {
                if (!directory.isDirectory()) {
                    String message =
                            "Unable to create directory " + directory;
                    throw new IOException(message);
                }
            }
        }
    }
}
