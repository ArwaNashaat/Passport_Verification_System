
FROM openjdk:8
WORKDIR /airport
ADD build/libs/api-1.0-SNAPSHOT.jar airport-api.jar
COPY . .
EXPOSE 8080
ENTRYPOINT ["java","-jar","airport-api.jar"]
