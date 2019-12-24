FROM amazoncorretto:8
RUN mkdir /app
WORKDIR /app
COPY ./application/build/libs/application-1.0-SNAPSHOT.jar .
#COPY ./dd-java-agent.jar .
ENTRYPOINT ["java", "-jar", "application-1.0-SNAPSHOT.jar", "application.yaml"]
