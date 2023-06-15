package xyz.wystudio.jsp.util;

import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

/*
* auther: WYstudio
* 2023-6-13 14:12 
*
* 次类为文件相关操作，包括 获取文件列表，获取文件拓展名，获取文件大小，获取文件修改时间
*/

public class FileUtils {

    /*
    * @param: String path 要获取列表的路径
    * @return: File[] 文件列表
    *
    * 获取根据文件名称正序排列的文件列表
    */
    public static File[] getFilesListByNmae(String path) {
        File[] files = new File(path).listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1 == null || o2 == null) {
                    return -1;
                }
                if (o1.isDirectory() && o2.isFile()) {
                    return -1;
                }
                if (o1.isFile() && o2.isDirectory()) {
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
        return files;
    }

    /*
    * @param: String name 文件名
    * @return: String 文件拓展名
    *
    * 根据文件名获取拓展名(不包含点)
    */
    public static String getFileLastNmae(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }

        int dotIndex = name.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == name.length() - 1) {
            return name;
        }

        String ext = name.substring(dotIndex + 1);
        return ext.startsWith(".") ? ext.substring(1) : ext;
    }

    /*
    * @param: String name 文件路径
    * @return: String 文件修改时间 
    *
    * 根据文件路径获取最后修改时间 (格式：年-月-日 时:分:秒)
    */
    public static String getFileEditTime(String path) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new File(path).lastModified()));
    }

    /*
    * @param: String name 文件路径
    * @return: String 文件大小
    *
    * 根据文件路径获取文件大小，并格式化为 B，KB，MB，GB
    */
    public static final DecimalFormat DF = new DecimalFormat("#.00");
    public static final String[] UNIT = { "B", "KB", "MB", "GB" };

    public static String getFileSize(String path) {
        if (StringUtils.isEmpty(path)) {
            return "0B";
        }

        File file = new File(path);
        if (!file.exists()) {
            return "0B"; // 文件不存在
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            long fileSize = fis.available();
            int index = (int) (Math.log10(fileSize) / Math.log10(1024));
            return DF.format(fileSize / Math.pow(1024, index)) + UNIT[index];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0B";
    }
}