# Markets and Stocks Trending Dashboards and Table

Sample applications for displaying some data on the dashboard as a Chart and as a Table. The sequence of execution is as follows; (1) Download source data from the API web, which is an MS Excel document. (2) Extract records from MS Excel and transform them into JSON data and then save it in the database. (3) Extract data from this database and then make it available through spring boot restful services. (4) The Vue frontend the receive from spring boot the data and then finally displays it in the table and the chart.

## Technology stack

1. Spring-boot
2. Vue Frontend
3. MySql Database
4. Source data API

## Commands to execute the application

```
Backend
mvn clean package
mvn spring-boot:run

Frontend
npm i
npm run serve

```

## Sample route links

## Get(1)
```
http://localhost:8040/api/markets/listed
```
## Post(5)
```
http://localhost:8040/api/markets/selected/2020/08/01
http://localhost:8040/api/markets/selected/2020/11/02
http://localhost:8040/api/markets/selected/2020/12/03
http://localhost:8040/api/markets/selected/2021/01/04
http://localhost:8040/api/markets/selected/2022/03/25

```

## Screen shots

1.
2.
3.


# END