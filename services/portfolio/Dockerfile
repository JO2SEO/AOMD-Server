FROM openjdk:11

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} /HOME/spring/app.jar

WORKDIR /HOME/spring/

EXPOSE 8080

CMD ["bash", "-c", "java ${JAVA_OPTS} -jar ./app.jar"]