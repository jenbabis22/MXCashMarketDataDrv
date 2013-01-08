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

java -jar ../mx-cash-marketdata-driver.jar -port_mina 1656
