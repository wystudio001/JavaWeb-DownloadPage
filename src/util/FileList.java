package xyz.wystudio.jsp.util;

import xyz.wystudio.jsp.util.FileUtils;
import xyz.wystudio.jsp.bean.FileBean;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

/*
* auther: WYstudio
* 2023-6-15 20:23
*
* 次类为文件列表实现，包括 
*/
public class FileList {
    private File[] files = new File[0];;
    private String path;
    private String[] extensionsToExclude = new String[0];
    private String[] directoriesToExclude = new String[0];
    private String[] filesToExclude = new String[0];

    public void load(String path2, String[] exfolder, String[] exexten, String[] exfile) {
        this.path = path2;
        directoriesToExclude = exfolder;
        extensionsToExclude = exexten;
        filesToExclude = exfile;
    }

    public FileBean[] getFileBeans(String sort, boolean isasc) {
        List<FileBean> listFileBeans = new ArrayList<>();

        switch (sort) {
        case "name":
            files = FileUtils.getFilesListByNmae(path, isasc);
            break;
        case "size":
            files = FileUtils.getFilesListBySize(path, isasc);
            break;
        case "time":
            files = FileUtils.getFilesListByTime(path, isasc);
            break;
        default:
            files = FileUtils.getFilesListByNmae(path, isasc);
            break;
        }

        for (File file : files) {
            String size = "-";
            String name = file.getName();
            String time = FileUtils.getFileEditTime(file.getPath());
            boolean shouldShow = true;
            if (file.isDirectory()) {
                for (String dir : directoriesToExclude) {
                    if (name.equals(dir)) {
                        shouldShow = false;
                        break;
                    }
                }

                if (shouldShow) {
                    listFileBeans.add(new FileBean(file.getPath(), file.getName(), size, time, file.isDirectory()));
                }

            } else {
                size = FileUtils.getFileSize(file.getPath());
                String last = FileUtils.getFileLastNmae(file.getName());

                for (String ext : extensionsToExclude) {
                    if (last.equals(ext)) {
                        shouldShow = false;
                        break;
                    }
                }

                for (String fn : filesToExclude) {
                    if (name.equals(fn)) {
                        shouldShow = false;
                        break;
                    }
                }

                if (shouldShow) {
                    listFileBeans.add(new FileBean(file.getPath(), file.getName(), size, time, file.isDirectory()));
                }
            }
        }
        return listFileBeans.toArray(new FileBean[0]);
    }
}