FROM openjdk
COPY target/tutor-0.0.1-SNAPSHOT.jar tutor-back.jar
CMD ["java", "-jar", "tutor-back.jar"]