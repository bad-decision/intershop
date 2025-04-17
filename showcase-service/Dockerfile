FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /showcase

COPY . ./

RUN gradle clean bootJar -x test

FROM openjdk:21

WORKDIR /showcase

COPY --from=builder /showcase/build/libs/showcase-1.0-SNAPSHOT.jar /showcase/showcase.jar

ENTRYPOINT ["java", "-jar", "/showcase/showcase.jar"]