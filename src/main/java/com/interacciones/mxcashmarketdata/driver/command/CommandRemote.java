package gfi.app.command;

public abstract class CommandRemote {
	
	protected CommandRemote() {
	}
	
	public abstract String getCommand();
	public abstract String getDescripcion();
	
	public abstract String Usage();
	public abstract void validacion();
	
	public abstract String execute(String[] args);
	
	public String executeCommand(Object[] args){
		System.out.println("Convirtiendo el Object[] a String[]");
		//if(args.length <= 1){
			//return Usage();
		//}
		
		String[] argsStr = new String[args.length];
		int cont=0;
		for (Object object : args) {
			argsStr[cont] = (String)object;
			cont++;
		}
		
		if(argsStr.length > 1 && argsStr[1].equalsIgnoreCase("Usage")){
			return Usage();
		}
		
		return execute(argsStr);
	}
}
