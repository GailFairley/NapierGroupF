FROM openjdk:latest
COPY ./target/courseWork.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "courseWork.jar", "db:3307", "3000"]