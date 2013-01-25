#!/bin/bash
#===================================================================================
# $Id:$
#
# driver-client.sh
#
# USAGE: driver-client.sh <host driver> <port driver> <file in long binary format>
#
# DESCRIPTION: Runs client to send messages(SETRIB) to market data driver
#
#  See COPYING For license
#===================================================================================

#export WOMBAT_PATH=../config

export JAVA_OPTS=" -Xms512m -Xmx1024m -XX:MaxPermSize=512m "

java -cp ../mx-cash-marketdata-driver.jar:../lib  com.interacciones.mxcashmarketdata.driver.client.DriverClient -host $1 -port $2 -src $3
