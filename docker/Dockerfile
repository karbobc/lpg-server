FROM eclipse-temurin:25.0.4_7-jdk

LABEL maintainer="Karbob <karbobc@gmail.com>"

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

COPY --chmod=755 docker/entrypoint.sh /entrypoint.sh
COPY --chmod=755 docker/healthcheck.sh /healthcheck.sh

WORKDIR /app
COPY layers/dependencies/ /app/
COPY layers/spring-boot-loader/ /app/
COPY layers/snapshot-dependencies/ /app/
COPY layers/application/ /app/

VOLUME /app/logs
EXPOSE 12312

HEALTHCHECK \
    --interval=30s \
    --timeout=5s \
    --start-period=60s \
    --retries=3 \
    CMD ["/healthcheck.sh"]

ENTRYPOINT ["/entrypoint.sh"]