Java Enterprise Online Project
===============================

Наиболее востребованные технологии /инструменты / фреймворки Java Enterprise:
Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson)/ Bootstrap(CSS)/ jQuery + plugins.

Using cURL
==========

### Get all meals 
>curl -X GET http://localhost:8080/topjava/rest/meals

### Get meals between 
>curl -X GET http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10%3A00%3A00&endDate=2020-01-31&endTime=18%3A00%3A00

### Get meal by id
>curl -X GET http://localhost:8080/topjava/rest/{id}

### Delete meal by id
>curl -X DELETE http://localhost:8080/topjava/rest/{id}

### Create new meal
>curl -X POST \
http://localhost:8080/topjava/rest/meals \
-H 'cache-control: no-cache' \
-H 'content-type: application/json' \
-d '{
"dateTime": "2022-01-30T15:20:40",
"description": "Description",
"calories": 1500
}'

### Update meal
>curl -X PUT \
http://localhost:8080/topjava/rest/meals/100008 \
-H 'cache-control: no-cache' \
-H 'content-type: application/json' \
-d '{
"dateTime": "2020-07-18T16:20:40",
"description": "Updated Description",
"calories": 1750
}'
