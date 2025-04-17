FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /payment

COPY . ./

RUN gradle clean bootJar -x test

FROM openjdk:21

WORKDIR /payment

COPY --from=builder /payment/build/libs/payment-1.0-SNAPSHOT.jar /payment/payment.jar

ENTRYPOINT ["java", "-jar", "/payment/payment.jar"]