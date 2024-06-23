FROM openjdk:21-jdk-slim

# Adicione um volume apontando para /tmp
VOLUME /tmp

# Argumento para o JAR_FILE
ARG JAR_FILE=build/libs/*.jar

# Copie o JAR_FILE para o contêiner
COPY ${JAR_FILE} app.jar

#name of the image:


# Exponha a porta da aplicação
EXPOSE 8080

# Execute a aplicação
ENTRYPOINT ["java","-jar","/app.jar"]