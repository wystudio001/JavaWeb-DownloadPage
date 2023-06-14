package xyz.wystudio.jsp;

import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

public class FileUtils {
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

    public static String getFileEditTime(String path) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new File(path).lastModified()));
    }

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