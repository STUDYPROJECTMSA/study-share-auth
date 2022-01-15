FROM openjdk:11-jre-slim
LABEL maintainer = "StudyShare"
VOLUME /tmp
ARG JAR_FILE=./*.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]