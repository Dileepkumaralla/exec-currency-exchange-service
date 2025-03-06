FROM openjdk:17-alpine
WORKDIR /app
COPY target/currency-exchange-api.jar /app/currency-exchange-api.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "currency-exchange-api.jar"]