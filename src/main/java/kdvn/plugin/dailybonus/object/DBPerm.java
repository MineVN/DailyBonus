package kdvn.plugin.dailybonus.object;

import java.util.List;

public class DBPerm {
	
	private String permission;
	private List<String> commands;
	private String lore;
	
	public DBPerm(String permission, List<String> commands, String lore) {
		this.permission = permission;
		this.commands = commands;
		this.lore = lore.replace("&", "§");
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public List<String> getCommands() {
		return this.commands;
	}
	
	public String getLore() {
		return this.lore;
	}
	
	
	
}
