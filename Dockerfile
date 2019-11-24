FROM openjdk:12

ENV EXECUTABLE_JAR=simple-webservice-0.0.1-SNAPSHOT.jar
ENV APP_LANG_TEMPLATES_LOCATION=/greeting_templates

WORKDIR /app

COPY target/$EXECUTABLE_JAR /app
#COPY src/main/resources/greeting_templates $APP_LANG_TEMPLATES_LOCATION
VOLUME /greeting_templates

CMD java -jar $EXECUTABLE_JAR
