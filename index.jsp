<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.util.regex.*,java.text.*,org.apache.commons.lang3.*" %>
<%@ page import="xyz.wystudio.jsp.config.*" %>
<%@ page import="xyz.wystudio.jsp.bean.*" %>
<%@ page import="xyz.wystudio.jsp.util.*" %>
<%!
    public String[] extensionsToExclude = new String[0];
    public String[] directoriesToExclude = new String[0];
    public String[] filesToExclude = new String[0];
    public String folder;
    public String sort;
    public boolean isasc = true;
    public String asc;
%>
<%
    String path = application.getRealPath(request.getRequestURI());
    String repath = request.getRequestURI();
    
    // 创建一个站点配置实例，并加载站点配置信息
    SiteConfig siteConfig = new SiteConfig();
    siteConfig.load(path);
    
    FileList fileList = new FileList();
    
    directoriesToExclude = siteConfig.getExcludeByDirector();
    extensionsToExclude = siteConfig.getExcludeByExtension();
    filesToExclude = siteConfig.getExcludeByFile();
    
    sort = request.getParameter("sort");
    folder = request.getParameter("folder");
    asc = request.getParameter("isasc");
    
    if(sort != null){
        sort = new String(sort.getBytes("ISO-8859-1"), "UTF-8");
    }else{
        sort = "name";
    }
    
    if(asc != null){
        isasc = new String(asc.getBytes("ISO-8859-1"), "UTF-8").equals("1");
    }
    
    if (folder != null) {
        folder = new String(folder.getBytes("ISO-8859-1"), "UTF-8");
        File folderFile = new File(path + "files" + folder);
        if (!folderFile.exists()) {
            response.sendRedirect("404.jsp");
            return;
        }
        
        for(String str : directoriesToExclude){
            if (folder.equals("/" + str)){
                response.sendRedirect("404.jsp");
                return;
            }
        }
    }else{
        folder = "";
    }
%>
<!DOCTYPE html>
<html>
 <head> 
  <meta charset="utf-8"> 
  <title><%=siteConfig.getSiteName()%></title> 
  <link rel="stylesheet" href="https://unpkg.com/mdui@1.0.2/dist/css/mdui.min.css" />
  <%@include file="./include/header.html"%>
  <style>
.container {
  width: 80%;
  margin: 10% auto 0;
  background-color: #f0f0f0;
  padding: 2% 5%;
  border-radius: 10px;
}

.div-footer{
  height: 130px;
}

.p-footer{
  color: #888888;
  text-align: center;
  }

ul {
  padding-left: 20px;
}

ul li {
  line-height: 2.3;
}

a {
  color: #20a53a;
  text-decoration:none;
}
</style> 
  <script src="https://unpkg.com/mdui@1.0.2/dist/js/mdui.min.js"></script>
 </head> 
 <body id="v1"> 
  <div id="v2" class="container"> 
   <h1 id="v3"><%=siteConfig.getSiteName()%></h1> 
   <h3 id="v4"><%=siteConfig.getSiteDescription()%></h3> 
   <br/> 
   <table id="list" class="mdui-table mdui-color-transparent">
   <thead>
   <tr>
   <%
   switch(sort){
   case "name":
        if(isasc){
            out.println("<th style=\"width:55%\"><a href=\"./?folder=" + folder + "&sort=name&isasc=0\">文件名称 ↑</a></th>");
        }else{
            out.println("<th style=\"width:55%\"><a href=\"./?folder=" + folder + "&sort=name&isasc=1\">文件名称 ↓</a></th>");
        }
        out.println("<th style=\"width:20%\"><a href=\"./?folder=" + folder + "&sort=size&isasc=0\">大小 ↑</a></th>");
        out.println("<th style=\"width:25%\"><a href=\"./?folder=" + folder + "&sort=time&isasc=0\">日期 ↑</a></th>");
        break;
   case "size":
        out.println("<th style=\"width:55%\"><a href=\"./?folder=" + folder + "&sort=name&isasc=0\">文件名称 ↑</a></th>");
        if(isasc){
            out.println("<th style=\"width:20%\"><a href=\"./?folder=" + folder + "&sort=size&isasc=0\">大小 ↑</a></th>");
        }else{
            out.println("<th style=\"width:20%\"><a href=\"./?folder=" + folder + "&sort=size&isasc=1\">大小 ↓</a></th>");
        }
        out.println("<th style=\"width:25%\"><a href=\"./?folder=" + folder + "&sort=time&isasc=0\">日期 ↑</a></th>");
        break;
   case "time":
        out.println("<th style=\"width:55%\"><a href=\"./?folder=" + folder + "&sort=name&isasc=0\">文件名称 ↑</a></th>");
        out.println("<th style=\"width:20%\"><a href=\"./?folder=" + folder + "&sort=size&isasc=0\">大小 ↑</a></th>");
        if(isasc){
            out.println("<th style=\"width:25%\"><a href=\"./?folder=" + folder + "&sort=time&isasc=0\">日期 ↑</a></th>");
        }else{
            out.println("<th style=\"width:25%\"><a href=\"./?folder=" + folder + "&sort=time&isasc=1\">日期 ↓</a></th>");
        }     
        break;
   default:
        out.println("<th style=\"width:55%\"><a href=\"./?folder=" + folder + "&sort=name&isasc=0\">文件名称 ↑</a></th>");
        out.println("<th style=\"width:20%\"><a href=\"./?folder=" + folder + "&sort=size&isasc=0\">大小 ↑</a></th>");
        out.println("<th style=\"width:25%\"><a href=\"./?folder=" + folder + "&sort=time&isasc=0\">日期 ↑</a></th>");       
        break;
   }
   
   %>
   </tr>
   </thead> 
   <tbody>
   <%
        out.print("<p>当前路径：/<a href=\"./\">根目录</a>" + (folder != null ? folder : "") + "</p>\n");
        
        fileList.load(path + "files" + (folder != null ? folder : ""),directoriesToExclude,extensionsToExclude,filesToExclude);
        FileBean[] filrBeans = fileList.getFileBeans(sort,isasc);
        if (filrBeans.length != 0) {
            for (FileBean file : filrBeans) {
                String name = file.getName();
                String time = file.getTime();
                String size = file.getSize();
                if (file.isFolder()) {
                    String pathDir = (folder == null ? "" : folder) + "/" + name;
                    out.println("<tr>");
                    out.println("<td class=\"link\"><a href=\"./?folder=" + pathDir + "\"><i class=\"mdui-icon material-icons\">&#xe2c7;</i>  " + name + "/</a></td>");
                    out.println("<td class=\"size\">-</td>");
                    out.println("<td class=\"date\">" + time + "</td>");
                    out.println("</tr>");
                } else {
                    String pathFile = "files" + (folder != null ? folder : "") + "/" + name;
                    out.println("<tr>");
                    out.println("<td class=\"link\"><a href=\"" + pathFile + "\"><i class=\"mdui-icon material-icons\">&#xe24d;</i>" + name + "</a></td>");
                    out.println("<td class=\"size\">" + size + "</td>");
                    out.println("<td class=\"date\">" + time + "</td>");
                    out.println("</tr>");
                }
            }
        }
   %>
   </tbody>
   </table>
   </br>
     <%@include file="./include/footer.html"%>
  </div>  
 </body>
</html>