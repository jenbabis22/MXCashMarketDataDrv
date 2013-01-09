
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

package com.interacciones.mxcashmarketdata.driver.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import com.interacciones.mxcashmarketdata.driver.model.MessageRetransmission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppropriateFormat {
	protected final static Log LOGGER=LogFactory.getLog(AppropriateFormat.class);
	private static final int INIT_ASCII = 0;
	private static final int END_ASCII = 250;
	
	private static File file = null;
	private static FileInputStream is = null;
	private static InputStreamReader isr = null;
	private static BufferedReader br = null;
	
	private static FileOutputStream fos = null;
	private static ObjectOutputStream oos = null;
	
	private static String source = "30122010.txt";
	private static String destination = "converterBinary";
	//private static String path = "";
	
	public static void main(String[] args) throws Throwable {
		init(args);
		
	    long time = System.currentTimeMillis();
	    //for (;;) {
	        try {
	        	//read
	            file = new File(source);
	            is = new FileInputStream(file);
	            isr = new InputStreamReader(is);
		 	    br = new BufferedReader(isr);
		 	    //write
			    fos = new FileOutputStream(destination);
				oos = new ObjectOutputStream(fos);
		 	    
		 	    int data = 0;
			    int count = 0;
			    
			    StringBuffer message = new StringBuffer();
			    while(data != -1){
			        data = br.read();
			        message.append((char)data);
			        count++;
			        if (data==10){
			        	transform(message);
			        	count=0;
			            message.delete(INIT_ASCII,message.length());
			        }
			    }
	            //break;
	        } catch (Exception e) {
	            LOGGER.error("Warning " + e.getMessage());
	            e.printStackTrace();
	        }
	        close();
	    //}
	    time = System.currentTimeMillis() - time;
	    LOGGER.info("Time " + time);
	}
	
	private static String transform(StringBuffer ascii) throws IOException{
		MessageRetransmission msgRetransmission = new MessageRetransmission();
		String binary = "";
		
		binary = (ascii.delete(ascii.length()-15, ascii.length()).toString());
		if(binary.length()<END_ASCII){
			System.out.println("CheckSum Incorrecto: " + ascii.length());
			int length = END_ASCII;
			binary = msgRetransmission.fillerStringRigth(binary, " ", length, false);
		}
		msgRetransmission.setMessage(binary);
		msgRetransmission.MsgConstructBinary();
		writeToFile(msgRetransmission.getByte());
		
		return binary;
	}
	
	private static void writeToFile(byte[] message) throws IOException {
		oos.write(message);
	}
	
	private static void init(String[] args){
		if(args.length <= 0){
			LOGGER.info("Default Parameters");
		}else {
			for (int i = 0; i < args.length;) {
				String arg = args[i];
				
				if(arg.equals("-src")){
					source = args[i+1];
					i+=2;
				}else if(arg.equals("-dst")){
					destination = args[i+1];
					i+=2;
				}else{
					i++;
				}
			}
		}
	}
	
	private static void close() throws IOException{
		//write
		oos.flush();
		oos.close();
		fos.close();
		
		//read
		br.close();
		isr.close();
		is.close();
	}
}
