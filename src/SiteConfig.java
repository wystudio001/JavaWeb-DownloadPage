package xyz.wystudio.jsp;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.BufferedReader;

public class SiteConfig {
    private String siteName;
    private String siteDescription;
    private String[] extensionsToExclude = new String[0];
    private String[] directoriesToExclude = new String[0];
    private String[] filesToExclude = new String[0];

    public void load(String path) throws IOException {
        this.siteName = readFile(path + "config/site-name.txt");
        this.siteDescription = readFile(path + "config/site-description.txt");
        getExclude(path);
    }

    private static String readFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.readLine();
        }
    }

    private void getExclude(String path) {
        String excludeFilePath = path + "config/exclude.txt";

        List<String> excludedDirectories = new ArrayList<>();
        List<String> excludedExtensions = new ArrayList<>();
        List<String> excludedFiles = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(excludeFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) { // 忽略空行
                    if (line.endsWith("/")) {
                        String line2 = line.substring(0, line.length() - 1);
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