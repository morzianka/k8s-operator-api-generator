FROM bellsoft/liberica-openjdk-alpine:11.0.3
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /opt/openapi-spring-1.0.5.jar
ENTRYPOINT ["java","-jar","/opt/openapi-spring-1.0.5.jar"]