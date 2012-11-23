package gfi.app.server;

import gfi.app.command.impl.RecoveryCommand;
import gfi.app.command.impl.StopProccessCommand;
import gfi.app.manager.CommandManagerInvokerImpl;

public class FakeProccessDropCopy extends CommandManagerInvokerImpl {
	public void fakeProccess(){
		System.out.println("Arrancando el Proceso Fake");
		//Prubea para Agregar Comandos que no estan por default
		StopProccessCommand rec = new StopProccessCommand();
		this.addCommand(rec);

		RecoveryCommand recovery = new RecoveryCommand();
		this.addCommand(recovery);
		
		System.out.println("Preparado para mi proceso Fake");
	}

}
