package gfi.app.manager;

import org.apache.xmlrpc.server.PropertyHandlerMapping;

import gfi.app.command.CommandRemote;

public abstract class CommandManagerInvokerImpl {
	private CommandInvoker invoker;
	protected CommandManagerInvokerImpl() {
		invoker = CommandInvoker.getInstance();
	}
	
	protected void initInvoke(PropertyHandlerMapping handlerMapping){
		CommandInvoker invoker = CommandInvoker.getInstance();
		invoker.setHandlerMapping(handlerMapping);
		invoker.addCommandDefault();
	}
	
	public void addCommand(CommandRemote command){
		invoker.addCommand(command);
	}
}
