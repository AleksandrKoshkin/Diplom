FROM openjdk:17-oracle
VOLUME /tmp
EXPOSE 8090
ADD target/untitled35-1.0-SNAPSHOT.jar cloudService.jar
ENTRYPOINT ["java", "-jar", "/cloudService.jar"]