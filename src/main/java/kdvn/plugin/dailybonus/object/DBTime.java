package kdvn.plugin.dailybonus.object;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import kdvn.plugin.dailybonus.main.MainDB;

public class DBTime {
	
	public static Map<Integer, DBTime> times = new HashMap<Integer, DBTime> ();
	
	public List<String> commands = Arrays.asList();
	public List<String> info = Arrays.asList();
	public List<DBPerm> dbperms = Lists.newArrayList();
	
	public DBTime(List<String> commands, List<String> info, List<DBPerm> dbperms) {
		this.commands = commands;
		this.info = info;
		this.dbperms = dbperms;
	}
	
	public void giveReward(Player player) {
		Bukkit.getScheduler().runTask(MainDB.main, () -> {
			commands.forEach(cmd -> {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getName()));
			});
			dbperms.forEach(dbp -> {
				if (player.hasPermission(dbp.getPermission()) ) {
					dbp.getCommands().forEach(cmd -> {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getName()));
					});
				}
			});
			System.out.println("[DailyBonus] Gave reward to " + player.getName());
		});
	}
	
}
