FROM openjdk:8
LABEL maintainer="Yanzheng (https://github.com/micyo202)"
VOLUME /tmp
COPY demo-k8s.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xmx512m", "-jar", "/app.jar"]