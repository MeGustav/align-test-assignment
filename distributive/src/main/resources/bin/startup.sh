#!/bin/sh

# Getting working directory
bin_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
workdir="$bin_dir/../"
# ClassPath
LIB_DIR=$workdir/lib
CONF_DIR=$workdir/conf

JAVA_OPTS="$JAVA_OPTS -Dspring.config.location=$CONF_DIR/application.properties"
JAVA_OPTS="$JAVA_OPTS -Dloader.path=$CONF_DIR"
JAVA_OPTS="$JAVA_OPTS -Dlogging.config=$CONF_DIR/logback.xml"

java -version
exec java $JAVA_OPTS -jar $LIB_DIR/application*.jar com.megustav.align.Application