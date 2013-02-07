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

package com.interacciones.mxcashmarketdata.driver.process.impl;

import com.interacciones.mxcashmarketdata.driver.process.MessageProcessing;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//This class recieves messages and writes them to a log file.
public class FileMessageProcessing implements MessageProcessing {
    protected final static Log LOGGER = LogFactory.getLog(FileMessageProcessing.class);
    /**
     * Down to file
     */
    private static File file = null;
    private static FileWriter is = null;
    private static BufferedWriter bf = null;

    private static SimpleDateFormat formato = null;

    public FileMessageProcessing() {
        try {
            file = new File("ProcessingFilter.log");
            is = new FileWriter(file, true);
            bf = new BufferedWriter(is);
	    formato = new SimpleDateFormat("H:mm:ss.SSS");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receive(String message, long sequence) {
        try {
            bf.write(message.concat("-".concat(formato.format(new Date()))));
            bf.write(13);
            bf.write(10);
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            bf.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    protected void finalize() throws Throwable {
        bf.close();
        is.close();
    }

    ;
}

