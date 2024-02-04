#!/usr/bin/env sh

curl --insecure --fail --silent --show-error \
     "http://127.0.0.1:12312/heartbeat/alive" || exit 1