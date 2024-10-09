#!/usr/bin/env sh

JVM_OPTS="-Xmx512m \
          -Xms512m \
          -XX:+UseZGC \
          -XX:+ZGenerational \
          -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
          -javaagent:/app/$(sed -n 's|.*"\(.*transmittable-thread-local.*\)".*|\1|p' BOOT-INF/classpath.idx) \
          ${JVM_OPTS}"

# shellcheck disable=SC2068,SC2086
if [ $# -eq 0 ]; then
  exec java ${JVM_OPTS} org.springframework.boot.loader.launch.JarLauncher ${@}
else
  exec ${@}
fi
