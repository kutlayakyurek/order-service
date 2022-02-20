# Sample Spring Boot - RabbitMQ Project

Sample Spring Boot project along with RabbitMQ integration written with Java

## Introduction

This project aims for implementing sample order based microservice

## How To Test

1. Install OpenJDK14

(Ubuntu, Debian) https://docs.datastax.com/en/jdk-install/doc/jdk-install/installOpenJdkDeb.html
(Macosx) https://dzone.com/articles/install-openjdk-versions-on-the-mac

2. Install Apache Maven

https://www.baeldung.com/install-maven-on-windows-linux-mac

3. Build package

mvn clean package

4. Run compose file

docker-compose up -d

5. Open the browser and go to localhost:15672 and add queue and exchange like below

queue name: order-queue
exchange: order-exchange (Type must be topic)
routingKey: order-routingKey

6. Go to localhost:8080/swagger-ui.html to check Swagger for APIs

7. All apis need authentication so send the request below to get token

POST
{
"username": "test",
"password": "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6"
}

8. For subsequent requests add 'Authorization' header with format below:

Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjQ1NDAzNDEzLCJpYXQiOjE2N (Change value with the result of authenticate endpoint)

9. There are sample data in order to make testing easier. To test out order service and RabbitMq, simply send the request below:

http://localhost:8080/order

POST
{
"productId": "3",
"contactId": "3"
}

10. For simplicity, using H2 in-memory database. So everytime you run the application, the data will be initialized as it is in the data.sql file