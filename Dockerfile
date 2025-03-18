FROM openjdk:17-jdk-alpine

# Change directory
WORKDIR /app

# Copy war file
COPY target/meeting-service-0.0.1-SNAPSHOT.war meeting-service.war

# Create non-root user
RUN adduser -D meeting_service
RUN chown -R meeting_service:meeting_service /app
USER meeting_service

# Run app
ENTRYPOINT ["sh","-c","java -jar -Dspring.config.location=${CONFIG_PATH} meeting-service.war"]

# Expose port 8083
EXPOSE 8083