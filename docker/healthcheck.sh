#!/usr/bin/env sh

curl --insecure --fail --silent --show-error \
     "http://127.0.0.1:9999/actuator/health" || exit 1
