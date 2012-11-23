package gfi.app.manager;

import java.util.HashMap;

public class CommandList {
	
	private CommandList() {
		// TODO Auto-generated constructor stub
		this.setList(new HashMap<String, String>());
	}
	
	private HashMap<String, String> list;
	
	private static CommandList instance;
	
	public static synchronized CommandList getInstance(){
		if(instance == null){
			instance = new CommandList();
		}
		return instance;
	}

	public void setList(HashMap<String, String> list) {
		this.list = list;
	}

	public HashMap<String, String> getList() {
		return list;
	}
}
