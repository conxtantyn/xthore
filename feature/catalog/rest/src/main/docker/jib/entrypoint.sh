#!/bin/sh

echo "The application will start in ${POST_DELAY}s..." && sleep ${POST_DELAY}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "${MAIN_CLASS}"  "$@"
