package xyz.wystudio.jsp;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.BufferedReader;

/*
* auther: WYstudio
* 2023-6-13 14:03 
*
* 次类为站点相关配置的实现类，包括 读取站点名称，描述以及限制文件
*/
public class SiteConfig {
    private String siteName;
    private String siteDescription;
    private String[] extensionsToExclude = new String[0];
    private String[] directoriesToExclude = new String[0];
    private String[] filesToExclude = new String[0];
    
    /*
    * @param: String path web应用在服务器中的真实路径
    * 
    * 用于加载相关配置
    */
    public void load(String path) throws IOException {
        this.siteName = readFile(path + "config/site-name.txt");
        this.siteDescription = readFile(path + "config/site-description.txt");
        getExclude(path);
    }
    
    // 读取文件中的一行内容
    private static String readFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.readLine();
        }
    }
    
    /*
    * @param: String path web应用在服务器中的真实路径
    * 
    * 用于加载限制目录相关配置
    */
    private void getExclude(String path) {
        String excludeFilePath = path + "config/exclude.txt";

        List<String> excludedDirectories = new ArrayList<>();
        List<String> excludedExtensions = new ArrayList<>();
        List<String> excludedFiles = new ArrayList<>();
        
        
        /*
        * 此处判断这一行内容是限制的 文件 还是 目录 还是 拓展名
        */
        try (Scanner scanner = new Scanner(new File(excludeFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) { // 忽略空行
                    if (line.endsWith("/")) {
                        String line2 = line.substring(0, line.length() - 1); //去掉开头斜杠
                        excludedDirectories.add(line2);
                    } else if (line.startsWith(".")) {
                        excludedExtensions.add(line.substring(1)); // 去掉点
                    } else {
                        excludedFiles.add(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + excludeFilePath);
            e.printStackTrace();
        }

        this.directoriesToExclude = excludedDirectories.toArray(new String[0]);
        this.extensionsToExclude = excludedExtensions.toArray(new String[0]);
        this.filesToExclude = excludedFiles.toArray(new String[0]);
    }

    public String getSiteName() {
        return this.siteName;
    }

    public String getSiteDescription() {
        return this.siteDescription;
    }

    public String[] getExcludeByFile() {
        return this.filesToExclude;
    }

    public String[] getExcludeByDirector() {
        return this.directoriesToExclude;
    }

    public String[] getExcludeByExtension() {
        return this.extensionsToExclude;
    }
}