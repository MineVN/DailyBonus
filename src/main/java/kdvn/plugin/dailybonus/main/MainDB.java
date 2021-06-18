package kdvn.plugin.dailybonus.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

import kdvn.plugin.dailybonus.object.DBAPI;
import kdvn.plugin.dailybonus.object.DBPerm;
import kdvn.plugin.dailybonus.object.DBTime;

public class MainDB extends JavaPlugin {
	
	public static MainDB main;
	
	public static int GUI_SIZE = 36;
	public static List<String> SPECIAL_DAYS = new ArrayList<String> ();
	
	public FileConfiguration config;
	
	@Override
	public void onEnable() {
		main = this;
		this.saveDefaultConfig();
		this.reloadCF();
		Bukkit.getPluginManager().registerEvents(new DBEventHandling(), this);
		this.getCommand("diemdanh").setExecutor(new Commands());
		
		Bukkit.getOnlinePlayers().forEach(p -> {
			DBAPI.load(p.getName());
		});
	}
	
	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(p -> {
			DBAPI.save(p.getName());
		});
	}
	
	public void reloadCF() {
		File file = new File(this.getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(file);
		GUI_SIZE = config.getInt("gui-size");
		SPECIAL_DAYS = config.getStringList("special-days");
		config.getConfigurationSection("day").getKeys(false).forEach(time -> {
			if (config.contains("day." + time + ".inherit")) {
				int inherit = config.getInt("day." + time + ".inherit");
				DBTime.times.put(Integer.parseInt(time), DBTime.times.get(inherit));
				return;
			}
			List<String> commands = config.getStringList("day." + time + ".commands");
			List<String> info = config.getStringList("day." + time + ".info");
			
			// DBPerm
			List<DBPerm> dbps = Lists.newArrayList();
			config.getStringList("day." + time + ".perm").forEach(s -> {
				String p = s.split(";")[0];
				List<String> cmds = Lists.newArrayList(s.split(";")[1].split(":"));
				String lore = s.split(";")[2];
				
				dbps.add(new DBPerm(p, cmds, lore));
			});
			
			DBTime dbTime = new DBTime(commands, info, dbps);
			DBTime.times.put(Integer.parseInt(time), dbTime);
		});
		// Create data file
		file = new File(getDataFolder() + "//playerdata//");
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	public void saveCF() {
		File file = new File(this.getDataFolder(), "config.yml");
		try {
			config.save(file);
		} catch (IOException e) {}
	}
	
}
