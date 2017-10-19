package me.dragon.base.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by dragon on 4/26/2017.
 */
public class FileUtil {
    static FileUtils fileUtils = new FileUtils();

    public static void copyFile(String flag,String srcFileStr) throws IOException {
        if ("facebook".equals(flag)){
            String fileName = srcFileStr.substring(19,srcFileStr.length());
            File srcFile = new File(srcFileStr);
            File destFile = new File("src/main/webapp/img/facebook_gem_pics/" + fileName);
            fileUtils.copyFile(srcFile,destFile);
        }
    }
}
