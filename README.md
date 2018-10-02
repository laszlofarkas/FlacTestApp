# FlacTestApp

A multi-module Docker based application

## Prerequisites

Each module have a various environment dependency, but in Docker environment these dependencies are provided. Prerequisites for developer environment are described under each [Modules](#modules)

### installing Docker
Please follow the official instructions [https://docs.docker.com/install/]()

### Set properties if necessary
If Docke is running on different ip than 192.168.99.100, you should set the actual url in the following properties
- \wsservice\src\main\resources\application.properties
  - spring.redis.host
- \RESTService\src\main\resources\application.properties
  - spring.redis.host
  - spring.data.mongodb.host
- \website\src\environments\environment.prod.ts
  - httpServerUrl
  - wsServerUrl

### Run the application
Start the application
```
docker-compose up
```
Stop the application 
```
docker-compose down
```

### Properties

## Modules
- [RESTService](#restservice)
- [wsservice](#wsservice)
- [website](#website)


### RESTService
A Spring boot application for handling rest request from websites. It persist the incoming date into mongoDB

#### Prerequisites
- [JDK version 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/index.html)

#### Dependencies
- MongoDB
- Redis

#### Run the application
```
mvn package
java -jar target/restservice-1.0.jar
```


### wsservice
A Spring boot application for handling new incoming messages and forward them to the listening websites over WebSocket.

#### Prerequisites
- [JDK version 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/index.html)

#### Dependencies
- MongoDB
- Redis

#### Run the application
```
mvn package
java -jar target/wsservice-1.0-SNAPSHOT.jar
```


### website
An Angular single-page application, which load and send JSON messages from/to the RESTService, and listening for new messages on wsservice.

#### Prerequisites
- [node.js](https://nodejs.org/en/)
- [Angular CLI](https://cli.angular.io/)

#### Dependencies
- RESTService
- wsservice

#### Run the application
```
ng serve --open
```

## TODO
- Replace hard-coded URLs in property files and set them based on Docker url
- Set storage for mongoDB outside of Docker environment
- Health-check in docker compose
- Integration tests
