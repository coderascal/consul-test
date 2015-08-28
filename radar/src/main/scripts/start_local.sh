#!/bin/bash

DIST_DIR=$(dirname $(~/bin/readlink $BASH_SOURCE[0]))


LOG_LEVEL="DEBUG"
CONSOLE_LOG_THRESHOLD="DEBUG"
LOG_DIR=$DIST_DIR

# make sure you have java8.31 or higher in your path
java -Xmx256m -classpath "$DIST_DIR/libexec/regservice/*" -Dlog.level=$LOG_LEVEL -Dconsole.log.threshold=$CONSOLE_LOG_THRESHOLD -Dlog.directory=$LOG_DIR -Ddata.directory=. -Dlog4j.configuration=file:$DIST_DIR/etc/regservice/log4j.xml com.bloomberg.core.cmd.BBExec com.bloomberg.testbed.Main -c "file:$DIST_DIR/etc/regservice/regservice.xml" --taskInstanceName $1

