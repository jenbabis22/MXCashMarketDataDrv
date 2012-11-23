package gfi.app.command.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import gfi.app.command.CommandRemote;
import gfi.app.manager.CommandList;

public class UsageCommand extends CommandRemote {

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return "usage";
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		return "Muestra los Comandos Disponibles";
	}

	@Override
	public String Usage() {
		// TODO Auto-generated method stub
		return "| usage | {command} |";
	}

	@Override
	public void validacion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String execute(String[] args) {
		StringBuffer resultCommand = new StringBuffer();
		CommandList command = CommandList.getInstance();
		
		HashMap<String, String> map = command.getList();
		
		//map.entrySet().iterator();
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry type = (Entry) iterator.next();
			//podria utilizar JSON
			resultCommand.append(type.getKey() + "|" + type.getValue() + "|@");
		}
		return resultCommand.toString();
	}
}
