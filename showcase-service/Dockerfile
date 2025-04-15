FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /shop

COPY . ./

RUN gradle clean bootJar -x test

FROM openjdk:21-slim

WORKDIR /shop

COPY --from=builder /shop/build/libs/intershop-1.0-SNAPSHOT.jar /shop/shop.jar

ENTRYPOINT ["java", "-jar", "/shop/shop.jar"]