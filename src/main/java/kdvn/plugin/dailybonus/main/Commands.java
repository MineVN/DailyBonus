package kdvn.plugin.dailybonus.main;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kdvn.plugin.dailybonus.object.DBGUI;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		
		if (sender.hasPermission("db.*")) {
			if (args.length == 1 && args[0].equals("reload")) {
				MainDB.main.reloadCF();
				sender.sendMessage("§aReloaded");
				return false;
			}
		}
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.openInventory(DBGUI.getGUI(player));
			player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
		}
		
		return false;
	}

}
