FROM --platform=$TARGETPLATFORM eclipse-temurin:21-jdk

LABEL maintainer="Karbob <karbobc@gmail.com>"

WORKDIR /app
COPY ./libs /app/
ENV JVM_OPTS="-Xmx512m \
              -Xms512m \
              -XX:+UseZGC \
              -XX:+ZGenerational \
              -javaagent:/app/ttl.jar \
              -Xbootclasspath/a:/app/ttl.jar"

ENTRYPOINT ["sh", "-c", "java ${JVM_OPTS} -jar /app/app.jar"]