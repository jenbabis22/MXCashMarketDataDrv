#!/bin/bash
#===================================================================================
# $Id:$
#
# shortbinary2long.sh
#
# USAGE: shortbinary2long.sh <file in short binary format> <file in long binary format>
#
# DESCRIPTION: Converts market data files saved in short binary format, used by some legacy drivers to the original format 
#              sent by the exchange. 
#
#  See COPYING For license
#===================================================================================


export JAVA_OPTS=" -Xms512m -Xmx1024m -XX:MaxPermSize=512m "

java -cp ../mx-cash-marketdata-driver.jar:../lib  com.interacciones.mxcashmarketdata.driver.util.AppropriateFormat -src $1 -dst $2
