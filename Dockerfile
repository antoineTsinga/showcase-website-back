# Utiliser l'image de base pour la compilation
FROM eclipse-temurin:20-jdk AS build

# Copier le contenu du projet
COPY . /app

# Définir le répertoire de travail
WORKDIR /app

# Donner les permissions d'exécution au script mvnw
RUN chmod +x ./mvnw

# Exécuter la commande de build Maven sans les tests
RUN ./mvnw clean package -DskipTests

# Utiliser l'image de base pour l'exécution
FROM eclipse-temurin:20-jre

# Copier l'artefact compilé depuis l'étape de build
COPY --from=build /app/target/showcase-backend.jar /app/showcase-backend.jar

# Définir le point d'entrée
ENTRYPOINT ["java", "-jar", "/app/showcase-backend.jar"]
