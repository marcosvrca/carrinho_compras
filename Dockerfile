FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
# Copia especificamente o JAR principal, ignorando o .original
COPY --from=build /app/target/AtividadeAssociacao-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Garante que o Spring Boot escute na porta definida pelo Railway via variável de ambiente PORT
ENTRYPOINT ["java", "-Dserver.port=${PORT:8080}", "-jar", "app.jar"]