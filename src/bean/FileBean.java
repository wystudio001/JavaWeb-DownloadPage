package xyz.wystudio.jsp.bean;

/*
* auther: WYstudio
* 2023-6-15 20:10
*
* 次类为文件Bean，包括 读取文件名称，读取文件大小，修改日期等
*/
public class FileBean {
    private String FilePath;
    private String FileName;
    private String FileSize;
    private String FileLastTime;
    private boolean IsFolder;

    public FileBean(String path, String name, String size, String time, boolean isFolder) {
        this.FilePath = path;
        this.FileName = name;
        this.FileSize = size;
        this.FileLastTime = time;
        this.IsFolder = isFolder;
    }
    
    public String getPath() {
        return this.FilePath;
    }

    public String getName() {
        return this.FileName;
    }
    
    public String getSize() {
        return this.FileSize;
    }
    
    public String getTime() {
        return this.FileLastTime;
    }
    
    public boolean isFolder() {
        return this.IsFolder;
    }
}