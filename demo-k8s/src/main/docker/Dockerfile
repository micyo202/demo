FROM openjdk:8
LABEL maintainer="Yanzheng (https://github.com/micyo202)"
VOLUME /tmp
ADD demo-k8s.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xmx512m", "-jar", "/app.jar"]
EXPOSE 8088