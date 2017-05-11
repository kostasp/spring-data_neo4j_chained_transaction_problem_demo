FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    SLEEP_FOR_DB_TO_BE_READY=10

# add directly the war
ADD *.war /app.war

VOLUME /tmp
EXPOSE 8092
CMD echo "The application will start in ${SLEEP_FOR_DB_TO_BE_READY}s..." && \
    sleep ${SLEEP_FOR_DB_TO_BE_READY} && \
    java -Djava.security.egd=file:/dev/./urandom -jar /app.war

