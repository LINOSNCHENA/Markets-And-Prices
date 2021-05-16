# DASHBOARD FOR STOCK MARKETS AND PRICES

Sample applications for displaying some data on the dashboard as a Chart and then as a Table. The sequence of execution is as follows; (1) Download source data from the API web, which is an MS Excel document. (2) Extract records from MS Excel and transform them into JSON data and then save it in the database. (3) Extract data from this database and then make it available through spring boot restful services. (4) The Vue frontend the receive from spring boot the data and then finally displays it in the table and the chart.

## Technology stack utilized

1. Spring-boot
2. Vue Frontend
3. MySql/Postgresql Database
4. Source data API

## Commands to execute the application

```
Backend
==============================================
mvn clean package
mvn spring-boot:run

Frontend
==============================================
npm i
npm run serve

```

## Sample localhost links

## get

http://localhost:8040/api/markets/listed

## post

http://localhost:8040/api/markets/selected/2020/08/01 \
http://localhost:8040/api/markets/selected/2020/11/02 \
http://localhost:8040/api/markets/selected/2020/12/03 \


## Functionality

1. Access records form API
2. Limit accessible dates of the data
3. View data as a Table and Chart
4. Testing(Pending)

## User View Experience

Below are screen shots from the application's output

![ M#1 ](https://github.com/LINOSNCHENA/Markets-and-stocks-prices-dashboard/blob/main/UxViews/page1.png)
![ M#2 ](https://github.com/LINOSNCHENA/Markets-and-stocks-prices-dashboard/blob/main/UxViews/page2.png)
![ M#3 ](https://github.com/LINOSNCHENA/Markets-and-stocks-prices-dashboard/blob/main/UxViews/page3.png)
![ M#4 ](https://github.com/LINOSNCHENA/Markets-and-stocks-prices-dashboard/blob/main/UxViews/page4.png)
![ M#5 ](https://github.com/LINOSNCHENA/Markets-and-stocks-prices-dashboard/blob/main/UxViews/page5.png)
![ M#6 ](https://github.com/LINOSNCHENA/Markets-and-stocks-prices-dashboard/blob/main/UxViews/page6.png)


## Lecture #47


```
16/05/2021

```
# END