package gfi.app.manager;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

import gfi.app.command.CommandRemote;
import gfi.app.command.impl.RecoveryCommand;
import gfi.app.command.impl.UsageCommand;

public class CommandInvoker {
	private PropertyHandlerMapping handlerMapping;
	private CommandList list;
	
	private CommandInvoker() {
		list = CommandList.getInstance();
	}

	private static CommandInvoker instance;
	
	public static synchronized CommandInvoker getInstance() {
		if(instance == null){
			instance = new CommandInvoker();
		}
		return instance;
	}
	
	public void addCommandDefault() {
		/**
		 * CARGARA LA LISTA DE COMANDOS POR DEFAULT
		 */
		UsageCommand usage = new UsageCommand();
		this.addCommand(usage);
		//handlerMapping.addHandler(recovery.getCommand(), recovery.getClass());
		//se agregaran demas comandos por Default
	}
	
	/**
	 * SE UTILIZARA PARA LA CARGA DE LOS COMANDOS, HASTA LOS NUEVOS
	 * QUE SE AGREGUEN
	 */
	public void addCommand(CommandRemote command){
		try{
			handlerMapping.addHandler(command.getCommand(), command.getClass());
			list.getList().put(command.getCommand(), command.getDescripcion());
		}catch (XmlRpcException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public PropertyHandlerMapping getHandlerMapping() {
		return handlerMapping;
	}

	public void setHandlerMapping(PropertyHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}
}
