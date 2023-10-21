FROM gradle:8.1.1-jdk-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src/
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:17-alpine
EXPOSE 8080
RUN mkdir /opt/app
COPY --from=build /home/gradle/src/build/libs/*.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]
