FROM openjdk:17-jdk-alpine

## Change directory
WORKDIR /app

## Create non-root user
RUN adduser -D meeting_service
RUN chown -R meeting_service:meeting_service /app
USER meeting_service

## Copy war file and run app
COPY target/meeting-service-0.0.1-SNAPSHOT.war meeting-service.war
ENTRYPOINT ["java","-jar","meeting-service.war"]

## Expose port 8082
EXPOSE 8082