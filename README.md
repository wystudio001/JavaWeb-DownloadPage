<h1 align="center">JavaWeb-DownloadPage</h1>

<p align="center">
<img alt="Version" src="https://img.shields.io/badge/version-beta_1-3f51b5.svg?style=flat-square"/>
<img alt="Author" src="https://img.shields.io/badge/author-WYstudio-red.svg?style=flat-square"/>
<img alt="Download" src="https://img.shields.io/badge/download-1.2M-brightgreen.svg?style=flat-square"/>
<a href="https://github.com/wystudio001/JavaWeb-DownloadPage/blob/main/LICENSE"><img alt="License" src="https://img.shields.io/badge/license-Apache2.0-orange.svg?style=flat-square"/></a>
</p>

# 项目简介
> 一个用JavaWeb编写的下载站，没有用框架，单纯jsp文件实现。

# 目前已实现功能
- 支持文件及目录查看，下载 
- 支持自定义站点名称，站点描述
- 支持设置禁止访问目录
- 首页文件列表支持过滤，目前有以下选项
    1. 通过拓展名过滤
    2. 通过文件名过滤
    3. 通过目录名过滤
- 首页文件列表支持排序，目前有以下选项
    1. 通过大小排序
    2. 通过名称排序
    3. 通过修改日期排序    
- 支持直接通过直链下载文件
- 等。。。(敬请期待)    

# 安装方法
```
 1. Fork本仓库或通过git下载到本地
 2. 修改/config目录下的站点配置文件
 3. 使用 Tomcat 新建一个站点，并将网站路径指向你本地下载的仓库地址
  (或复制你本地的仓库到Tomcat站点目录下)
 4. 将需要放到下载站的文件复制到/files目录下
 5. 浏览器访问站点绑定的域名即可使用 
```

站点配置文件说明：
```
 /config/exclude.txt
   这是文件过滤的文件配置，如果某些文件或目录不想让用户在首页的文件列表中看到可以设置
     过滤文件：直接写文件名即可
     过滤目录：以"/'开头，如"/test"
     过滤拓展名：以"."开头，如".txt"
 
 /config/site-name.txt
   这是站点名称的配置目录，在首页会有显示
   
 /config/site-description.txt                  
   这是站点描述的配置目录，在首页会有显示
```
# License 
<a href="https://github.com/wystudio001/JavaWeb-DownloadPage/blob/main/LICENSE"><img alt="License" src="https://img.shields.io/badge/license-Apache2.0-orange.svg?style=flat-square"/></a>

根据 Apache-2.0 许可证开源