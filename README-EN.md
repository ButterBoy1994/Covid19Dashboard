# Covid19Dashboard
[中文](README.md) | English

This is a data dashboard about the COVID-19 pandemic, primarily showcasing global infection data from 2019 to the end of 2022.

## Demonstration
<iframe src="//player.bilibili.com/player.html?aid=869281764&bvid=BV1RV4y1m72f&cid=1149688063&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"> </iframe>

## How to start？
### Initialize data
First, initialize the data by executing the SQL scripts in the [init-sql](init-sql) directory in your local database.

### Modify Configuration
Modify the `MySQL` section in the [application.yml](src%2Fmain%2Fresources%2Fapplication.yml) configuration file.
```yaml
mysql:
  worlds:
    jdbcUrl: your database url
    username: your username
    password: your password
    minIdle: 1
```
### Execution
Once all of the above is ready, you can run the `main` method in [Application.java](src%2Fmain%2Fjava%2Fdemo%2Fcovid19%2Fdashboard%2FApplication.java).
### Access
After completing the above steps, you can access the website by entering the following link in your browser.
> http://127.0.0.1:8081/dashboard/covid19/dashboard/en

The data refresh rate is 1 second per update. You can modify the parameter of the `trigger` method in [dashboard.html](src%2Fmain%2Fresources%2Ftemplates%2Fdashboard.html) to control the frequency. The default value is 1000 (unit: milliseconds).