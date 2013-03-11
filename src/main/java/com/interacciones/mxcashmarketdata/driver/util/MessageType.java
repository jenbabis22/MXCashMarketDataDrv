// $Id: 5d27e77750662053c48f59edb91e8913f3bb3f61 $
// MXCashMarketDataDrv - An OpenMama based driver for the Mexican Cash Market Binary Feed
// SPT Software contribution
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

/**
 * @summary Enum to handle message types
 * @author SPT Software Rene Barrera (rene.barrera@sptsoftware.com) Miguel
 * Suárez (miguel.suarez@sptsoftware.com) February 2013
 */
public enum MessageType {

    O("O", true),
    DO("DO", true),
    K("K", true),
    P("P", true),
    DP("DP", true),
    O1("O1", true),
    D1("D1", true),
    Type2("2", true),
    U("U", true),
    E("E", true),
    X("X", true),
    Y("Y", true);
    private String IdType;
    private boolean publishable; // True if can be published

    /**
     * @return Identifier
     */
    public String getIdType() {
        return IdType;
    }

    /**
     * @return True if can be published
     */
    public boolean esPublicable() {
        return publishable;
    }

    /**
     * Sets whether this type can be published
     */
    public void setPublishable(boolean pub) {
        this.publishable = pub;
    }

    /**
     * Constructor
     *
     * @param idType Type identifier
     * @param pub Can be published?
     */
    MessageType(String idType, boolean pub) {
        this.IdType = idType;
        this.publishable = pub;
    }
}
