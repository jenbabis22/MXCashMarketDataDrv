MXCashMarketDataDrv
===================

An OpenMama based driver for the Mexican Cash Market Binary Feed

Copyright (c) 2012 Interacciones Casa de Bolsa

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

An OpenMama based driver for the Mexican Cash Market Binary Feed

This project aims to have a full featured DRIVER for the market data distributed through BMV that publishes using OpenMama.

Why DRIVER?

Usually the software pieces that distribute market data are called Feed Handlers, but they support advanced features such as fault tolerance that our Driver Initially will not have, because is based on a version of OpenMama publishing that does not support them.

What about the Mexican Cash Market

The Mexican Cash Market is also know as BMV Capitales. BMV stands for Bolsa Mexicana de Valores (Mexican Stock Exchange). This driver will initially handle the market data distribute through the 'Setrib Tradicional' interface, and if needed will handle the market data distributed throughout the FIX interface as well.

BMV, Setrib Tradicional and all other trademarks remain the property of their respective owners. 

Why OpenMama

We believe that currently OpenMama offers a unique way of distributing market data in an open source manner. we like that.

Can i put this into production?

You can do whatever you want with it, as long as you understand you are on your own.