#!/usr/bin/env sh
##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

APP_HOME=$(cd "${0%/*}" && pwd -P)
APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")

# Add default JVM options here if desired
DEFAULT_JVM_OPTS=""

# Locate java binary
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        JAVA_CMD="$JAVA_HOME/jre/sh/java"
    else
        JAVA_CMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVA_CMD" ] ; then
        echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" >&2
        exit 1
    fi
else
    JAVA_CMD="$(command -v java)"
fi

if [ ! -x "$JAVA_CMD" ] ; then
    echo "ERROR: Java not found in your PATH or JAVA_HOME." >&2
    exit 1
fi

# Collect arguments
save () {
    for i do
        printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/' \\\\/"
    done
}

APP_ARGS=$(save "$@")

# Execute Gradle
exec "$JAVA_CMD" $DEFAULT_JVM_OPTS -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
