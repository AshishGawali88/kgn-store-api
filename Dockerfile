# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e dependency:go-offline
COPY src ./src
RUN mvn -q -e clean package -DskipTests

# ---- Run stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
# Run as an unprivileged user, not root.
RUN useradd -r -u 1001 appuser
COPY --from=build /app/target/kgn-store-api-1.0.0.jar app.jar
USER appuser
EXPOSE 8080
# Keep memory modest for free 512MB tiers.
ENV JAVA_OPTS="-XX:MaxRAMPercentage=70 -XX:+UseSerialGC"
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
