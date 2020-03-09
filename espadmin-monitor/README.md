<h1 style="text-align: center">esp-admin 客户端监控模块</h1>
<div style="text-align: center">

#### 项目简介
希望通过简单的配置即可实现对服务器CPU、内存、磁盘以及交换区占用情况的监控

**账号密码** ```admin/123456```(默认密码都是123456)

####  使用说明
- Window服务器：请将espadmin\espadmin-monitor\src\main\resources目录下的sigar-x86-winnt.dll或者sigar-amd64-winnt.dll 放到C:\Windows\System32或者java的bin目录下
- Linux服务器：请将espadmin\espadmin-monitor\src\main\resources目录下的libsigar-x86-linux.so或者libsigar-amd64-linux.so 放到/usr/lib或者java的bin目录下
- 将打包好的espadmin-monitor-2.2.jar 放到服务器上，执行 nohup java -jar espadmin-monitor-2.2.jar & 即可
