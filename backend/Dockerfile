FROM maven:3.5-jdk-8 AS build
COPY src /usr/matcha/src
COPY pom.xml /usr/matcha
RUN mvn -f /usr/matcha/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/matcha/target/backend-1.0-SNAPSHOT.jar /usr/matcha/backend-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/matcha/backend-1.0-SNAPSHOT.jar"]
