FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8282
ADD target/waraclecoding-1.0.0.jar waraclecoding-1.0.0.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /waraclecoding-1.0.0.jar" ]