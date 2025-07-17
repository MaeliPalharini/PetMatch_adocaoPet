FROM eclipse-temurin:17-jdk-alpine
# Forçando rebuild

# Diretório da aplicação
WORKDIR /app

# Copiando arquivos necessários
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Permissão e baixar dependências
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copiar o restante da aplicação
COPY src ./src

# Gerar o .jar sem rodar os testes e listar arquivos para debug
RUN ./mvnw clean package -DskipTests && ls -la target

# Copia o jar de dentro do próprio container (evita erro de cache externo)
RUN cp target/app.jar app.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=railway", "-jar", "app.jar"]
