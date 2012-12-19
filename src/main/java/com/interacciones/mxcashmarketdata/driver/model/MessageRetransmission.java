// $Id$
// MXCashMarketDataDrv - An OpenMama based driver for the Mexican Cash Market Binary Feed
// Copyright (c) 2012 Interacciones Casa de Bolsa
/* 
  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.
 
  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.
 
  You should have received a copy of the GNU Lesser General Public
  License along with this library; if not, write to the Free Software
  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/


package com.interacciones.mxcashmarketdata.driver.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class MessageRetransmission {

    private static ByteBuffer messageBuffer;
    private static int MESSAGE_LENGTH = 278;
    private static int FILLER_BYTE_LENGTH = 20;
    private static int DATA_LENGTH = 222;


    /**
     * *BINARY***
     */
    private static int SOH = 0X01;
    private static int DC4 = 0X14;
    private static int _128 = 0X80;
    private static int BYTE_NULL = 0X00;
    private static int ZERO = 0X30;
    private static int _172 = 0XAC;
    private static int EOT = 0X04;

    /**
     * *ASCII***
     */
    private static long sequencelong;
    //private static String SEQUENCE  = "0000001";
    private static String TIME = "000000";
    private static String TYPE = "02";
    private static String ORIGIN = "03";
    private static String DESTINATION = "00";
    private static String CONTENT = "000000";
    private static String LENGTH = "000";
    private static byte[] DATA;
    private String message;

    /**
     * *BINARY***
     */
    private static int RC = 0X0D;
    private static int CHECKSUM = 0X032;

    private static byte[] getByte;

    public MessageRetransmission() {
        messageBuffer = ByteBuffer.allocate(MESSAGE_LENGTH);
        messageBuffer.order(ByteOrder.LITTLE_ENDIAN);

    }

    /**
     * Binary format
     */
    private void getHeaderBinary(){
    /**Armando el header**/
        messageBuffer.position(0);
	messageBuffer.put((byte)SOH)
		     .put((byte)DC4)
		     .put((byte)_128)
		     .put((byte)BYTE_NULL)
		     .put(fillerByte((byte)BYTE_NULL, FILLER_BYTE_LENGTH));
    }
	
    private void getBodyBinary(){
        messageBuffer.put(message.getBytes())
		     .put((byte)RC)
		     .put((byte)ZERO)
		     .put((byte)_172)
		     .put((byte)EOT);
    }

    /**
     * Message Retransmission
     */
    private void getHeader() {
        /**Building the header**/
        messageBuffer.position(0);
        messageBuffer.put((byte) SOH)
                .put((byte) DC4)
                .put((byte) _128)
                .put((byte) BYTE_NULL)
                        //.put(fillerByte((byte)BYTE_NULL, FILLER_BYTE_LENGTH));
                .put(fillerString("0", FILLER_BYTE_LENGTH));
    }

    /**
     * Message Retransmission
     */
    private void getBody() {
        messageBuffer.put(getSequencelong().getBytes())
                .put(TIME.getBytes())
                .put(ORIGIN.getBytes())
                .put(TYPE.getBytes())
                .put(DESTINATION.getBytes())
                .put(CONTENT.getBytes())
                .put(LENGTH.getBytes())
                .put(fillerString(" ", DATA_LENGTH))
                .put((byte) RC)
                        //.put((byte)BYTE_NULL)
                .put((byte) ZERO)
                        //.put((byte)CHECKSUM)
                .put((byte) _172)
                .put((byte) EOT);
    }

    /**
     * Binary format
     */
    public void MsgConstructBinary(){
        getHeaderBinary();
	getBodyBinary();
	toString();
    }

    /**
     * Message Retransmission
     */
    public void MsgConstruct() {
        getHeader();
        getBody();
        toString();
    }

    public byte[] getByte() {

        return messageBuffer.array();
    }

    public static byte[] fillerString(String value, String relleno, int length) {
        StringBuffer tmp;
        int start;
        if (value != null) {
            tmp = new StringBuffer(value);
            assert (value.length() <= length);
            if (value.length() == length) {
                return tmp.toString().getBytes();
            }
            start = value.length();
        } else {
            tmp = new StringBuffer();
            start = 0;
        }

        for (int i = start; i < length; i++)
            tmp.append(relleno);

        return tmp.toString().getBytes();
    }

    public static String fillerStringRigth(String value, String filling, int length) {
        StringBuffer tmp = filler(value, filling, length);

        tmp.append(value);

        return tmp.toString();
    }

    public String fillerStringRigth(String value, String filling, int length, boolean rigth) {
        StringBuffer tmp = filler(value, filling, length);
        
        if(rigth){
            tmp.append(value);
        }else{
            String strTmp = tmp.toString();
            tmp = new StringBuffer(value.concat(strTmp));
        }
        
        return tmp.toString();
    }

    public static byte[] fillerString(String value, int length) {
        StringBuffer tmp;
        int start;
        if (value != null) {
            tmp = new StringBuffer(value);
            assert (value.length() <= length);
            if (value.length() == length) {
                return tmp.toString().getBytes();
            }
            start = value.length();
        } else {
            tmp = new StringBuffer();
            start = 0;
        }

        for (int i = start; i < length; i++)
            tmp.append(value);

        return tmp.toString().getBytes();
    }

    public static byte[] fillerByte(byte value, int length) {
        byte[] byteArray = new byte[length];
        return byteArray;
    }

    private StringBuffer filler(String value, String filling, int length) {
        StringBuffer tmp;
        int start=0;
        if (value != null) {
            tmp = new StringBuffer();
            for (int i = start; i < (length-value.length()); i++)
                tmp.append(filling);
            
        } else {
            tmp = new StringBuffer();
            start = 0;
        }
        
        return tmp;
    }

    @Override
    public String toString() {
        return "MessageRetransmission [getByte()=" + Arrays.toString(getByte())
                + "]";
    }

    public static String getSequencelong() {
        return fillerStringRigth(new String().valueOf(sequencelong), "0", 7);
    }

    public static void setSequencelong(long sequencelong) {
        MessageRetransmission.sequencelong = sequencelong;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

