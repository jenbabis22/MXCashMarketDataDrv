package gfi.app.command.impl;

import gfi.app.command.CommandRemote;

public class StopProccessCommand extends CommandRemote{

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return "stop";
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		return "Parando Proceso Fake";
	}

	@Override
	public String Usage() {
		// TODO Auto-generated method stub
		return "|stop | stop {procread } default all|";
	}

	@Override
	public void validacion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String execute(String[] args) {
		String respuesta;
		
		for (String string : args) {
			System.out.println("Argumento: " + string);
		}
		
		System.out.println("Ejecucion del comando Stop Fake");
		respuesta = "Ejecucion del comando stop Fake";
		
		return respuesta;
	}

}
