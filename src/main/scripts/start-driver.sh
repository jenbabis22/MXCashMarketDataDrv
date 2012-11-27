#!/bin/bash
#===================================================================================
# $Id:$
#
# start-driver.sh
#
# USAGE: start-driver.sh
#
# DESCRIPTION: Runs the market data driver
#
#  See COPYING For license
#===================================================================================


export JAVA_OPTS=" -Xms512m -Xmx1024m -XX:MaxPermSize=512m "

CLASSPATH=../.classes/:../lib/*

java -jar mx-cash-marketdata-driver
