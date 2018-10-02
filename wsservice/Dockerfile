# Build rest service
FROM maven:alpine as builder
WORKDIR /usr/src/app
COPY pom.xml .
COPY ./src ./src
RUN mvn install

# serve rest service
FROM java:8-jre-alpine
WORKDIR /
COPY --from=builder /usr/src/app/target/*.jar /app.jar
CMD java -jar app.jar
