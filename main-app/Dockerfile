FROM eclipse-temurin:17-jdk-focal as builder

RUN apt-get update \
  && apt-get install -y ca-certificates curl --no-install-recommends \
#   && rm -rf /var/lib/apt/lists/* \
  && apt-get install -y dos2unix

# # common for all images
# ENV MAVEN_HOME /usr/share/maven

# COPY --from=maven:3.9.2-eclipse-temurin-11 ${MAVEN_HOME} ${MAVEN_HOME}
# COPY --from=maven:3.9.2-eclipse-temurin-11 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
# COPY --from=maven:3.9.2-eclipse-temurin-11 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

# RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

# ARG MAVEN_VERSION=3.9.2
# ARG USER_HOME_DIR="/root"
# ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# # ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
WORKDIR /app
# COPY . .

COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .
COPY src ./src

RUN dos2unix mvnw
RUN chmod +x mvnw
RUN ./mvnw clean package
COPY ./target/*.jar app.jar

FROM eclipse-temurin:17-jre-focal

COPY --from=builder /app/app.jar .
ENTRYPOINT ["java", "-jar", "app.jar"]

# CMD ["mvn", "spring-boot:run"]
