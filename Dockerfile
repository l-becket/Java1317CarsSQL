FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY ./target/cars-jpa-0.0.1-SNAPSHOT.jar ./cars-jpa.jar
EXPOSE 8080
CMD [ "java","-jar", "/app/cars-jpa.jar" ]