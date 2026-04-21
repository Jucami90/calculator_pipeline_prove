FROM eclipse-temurin:21-jdk-alpine AS builder
LABEL authors="jucami90"
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine
LABEL authors="jucami90"
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
# Railway inyecta $PORT automáticamente
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:=8080}"]