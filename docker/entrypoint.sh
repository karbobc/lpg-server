#!/usr/bin/env sh

exec java "${JVM_OPTS}" -jar /app/lpg-server.jar "${@}"