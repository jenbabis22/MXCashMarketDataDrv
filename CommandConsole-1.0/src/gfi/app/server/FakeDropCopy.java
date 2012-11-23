package gfi.app.server;

import gfi.app.manager.CommandManager;

import java.io.IOException;

import org.apache.xmlrpc.XmlRpcException;

public class FakeDropCopy {
	private static CommandManager commandManager;
	private static FakeProccessDropCopy fakeProccess;
	
	public static void main(String[] args) throws IOException, XmlRpcException {
		//ESTAS LINEAS PODRIAN IR DENTRO DEL COMMAND MANAGER
		commandManager = new CommandManager();

		fakeProccess = new FakeProccessDropCopy();
		fakeProccess.fakeProccess();
		
		//server = new ServerWebManager();
		//server.run();
		// TODO Auto-generated method stub
		System.out.println("Levantando el Drop Copy");
		
		//INICIANDO EL WEB SERVER DEL XML-RPC 
		System.out.println("Servidor Web-Xml Levantado");
	}

}
