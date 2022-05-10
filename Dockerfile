FROM openjdk:11.0.4-jre-slim

WORKDIR /app

ADD /build/libs/userapi-1.0.0.jar ./userapi-1.0.0.jar

CMD ["java", "-jar", "userapi-1.0.0.jar"]