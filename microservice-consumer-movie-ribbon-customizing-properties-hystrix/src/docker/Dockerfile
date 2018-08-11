#docker search jdk找到的最小jdk基础镜像
FROM fabric8/java-jboss-openjdk8-jdk
VOLUME /tmp
ADD microservice-consumer-movie-ribbon-customizing-properties-hystrix-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 10001
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]