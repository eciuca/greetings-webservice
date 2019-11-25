FROM openjdk:13-alpine

ENV EXECUTABLE_JAR=greetings-webservice-0.0.1-SNAPSHOT.jar
ENV APP_LANG_TEMPLATES_LOCATION=/greeting_templates

WORKDIR /app
COPY target/$EXECUTABLE_JAR /app

VOLUME /greeting_templates
VOLUME /app_data

CMD java -Dspring.profiles.active=prod -jar $EXECUTABLE_JAR
