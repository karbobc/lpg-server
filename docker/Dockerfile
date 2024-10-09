FROM eclipse-temurin:21-jdk

LABEL maintainer="Karbob <karbobc@gmail.com>"

WORKDIR /app
COPY layers/application/ /app/
COPY layers/dependencies/ /app/
COPY layers/snapshot-dependencies/ /app/
COPY layers/spring-boot-loader/ /app/
COPY docker/entrypoint.sh /entrypoint.sh
COPY docker/healthcheck.sh /healthcheck.sh

VOLUME /app/logs
EXPOSE 12312

HEALTHCHECK --interval=60s --timeout=10s CMD ["/healthcheck.sh"]

ENTRYPOINT ["/entrypoint.sh"]