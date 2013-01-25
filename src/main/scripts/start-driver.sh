#!/bin/bash
#===================================================================================
# $Id:$
#
# start-driver.sh
#
# USAGE: start-driver.sh <port driver>
#
# DESCRIPTION: Runs the market data driver
#
#  See COPYING For license
#===================================================================================

#export WOMBAT_PATH=../config

export JAVA_OPTS=" -Xms512m -Xmx1024m -XX:MaxPermSize=512m "

java -jar ../mx-cash-marketdata-driver.jar -port_mina $1
