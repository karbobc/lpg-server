FROM eclipse-temurin:21.0.4_7-jdk

LABEL maintainer="Karbob <karbobc@gmail.com>"

WORKDIR /app
COPY ./libs /app/
COPY docker/entrypoint.sh /entrypoint.sh
COPY docker/healthcheck.sh /healthcheck.sh
ENV JVM_OPTS="-Xmx512m \
              -Xms512m \
              -XX:+UseZGC \
              -XX:+ZGenerational \
              -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
              -javaagent:/app/ttl.jar \
              -Xbootclasspath/a:/app/ttl.jar"

VOLUME /app/logs
EXPOSE 12312

HEALTHCHECK --interval=60s --timeout=10s CMD ["/healthcheck.sh"]

ENTRYPOINT ["/entrypoint.sh"]