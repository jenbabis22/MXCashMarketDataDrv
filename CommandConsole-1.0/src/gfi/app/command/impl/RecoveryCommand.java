package gfi.app.command.impl;

import gfi.app.command.CommandRemote;

public class RecoveryCommand extends CommandRemote {

	@Override
	public String getCommand() {
		return "recovery";
	}

	@Override
	public String getDescripcion() {
		String desc = "Recuperacion en caso de un failover";
		return desc;
	}

	@Override
	public String Usage() {
		String usage = "|recovery | recovery {file}|";
		return usage;
	}

	@Override
	public void validacion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String execute(String[] args) {
		for (String string : args) {
			System.out.println("Argumento: " + string);
		}
		
		System.out.println("Ejecucion del comando");
		String rescommand;
		rescommand = "Ejecucion del comando recovery";
		return rescommand;
	}
}