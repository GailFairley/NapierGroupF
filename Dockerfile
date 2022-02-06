FROM openjdk:latest
COPY ./target/courseWork-0.1.0.1-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "courseWork-0.1.0.1-jar-with-dependencies.jar"]