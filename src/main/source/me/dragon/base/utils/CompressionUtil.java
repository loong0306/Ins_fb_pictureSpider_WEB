package me.dragon.base.utils;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import java.io.File;

/**
 * Created by dragon on 4/1/2017.
 */
public class CompressionUtil {
    public static void compress(String desPath, String srcPath){
        File desFile = new File(desPath);// 目标文件
        File srcFile = new File(srcPath);// 源文件
        if (!srcFile.exists())
            throw new RuntimeException("需要压缩的文件不存在");

        Project project = new Project();
        Zip zip = new Zip();
        zip.setProject(project);
        zip.setDestFile(desFile);
        FileSet fileSet = new FileSet();
        fileSet.setProject(project);
        fileSet.setDir(srcFile);
        zip.addFileset(fileSet);
        zip.execute();
    }

}