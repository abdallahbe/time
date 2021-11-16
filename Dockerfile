FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/timesheet-1.9.jar timesheet-1.9.jar
ENTRYPOINT ["java","-jar","/timesheet-1.9.jar"]
