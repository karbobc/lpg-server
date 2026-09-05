#!/usr/bin/env sh

exec curl \
  --fail \
  --silent \
  --show-error \
  --output /dev/null \
  --connect-timeout 2 \
  --max-time 4 \
  "http://127.0.0.1:9999/actuator/health"
