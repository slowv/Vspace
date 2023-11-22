FROM openjdk:17-jdk-alpine
COPY target/*.jar vspace.jar
ENTRYPOINT ["java","-jar","/vspace.jar"]