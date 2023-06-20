# Covid19Dashboard
中文 | [English](README-EN.md)

这是一个关于COVID-19疫情的数据大盘，主要展示了从2019年到2022年底的全球感染数据。

## 效果演示
<iframe src="//player.bilibili.com/player.html?aid=869281764&bvid=BV1RV4y1m72f&cid=1149688063&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"> </iframe>

## 如何运行？
### 初始化数据
首先将[init-sql](init-sql)目录下的sql脚本在您本地的数据库中进行数据初始化。

### 修改配置
将配置文件[application.yml](src%2Fmain%2Fresources%2Fapplication.yml)中mysql的部分修改掉：
```yaml
mysql:
  worlds:
    jdbcUrl: 改成您本地的库
    username: 您本地库的用户名
    password: 您本地库的密码
    minIdle: 1
```
### 运行
以上全部就绪后，运行[Application.java](src%2Fmain%2Fjava%2Fdemo%2Fcovid19%2Fdashboard%2FApplication.java)中的`main`方法即可。
### 访问
以上全部完成后，在您的浏览器中输入以下链接即可访问：
> http://127.0.0.1:8081/dashboard/covid19/dashboard/cn

数据刷新频率为1s/次，您可以修改[dashboard.html](src%2Fmain%2Fresources%2Ftemplates%2Fdashboard.html)中`trigger`方法的参数来控制频率，默认1000（单位：毫秒）