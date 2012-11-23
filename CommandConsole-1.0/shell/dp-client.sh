#!/bin/sh

MEMORY=124m;

if [ -f $JAVA_HOME/bin/java ]; then
 JAVAEXEC=$JAVA_HOME/bin/java
else
 JAVAEXEC=java
fi

JAVA_OPTS="-Xms$MEMORY -Xmx$MEMORY "
CLASS_PATH=../bin:../lib/*

$JAVAEXEC -cp $CLASS_PATH $JAVA_OPTS gfi.app.client.ConsoleRm $*