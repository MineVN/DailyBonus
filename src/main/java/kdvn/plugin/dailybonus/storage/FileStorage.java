package kdvn.plugin.dailybonus.storage;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import kdvn.plugin.dailybonus.main.MainDB;
import kdvn.plugin.dailybonus.object.DBPlayerData;

public class FileStorage {
	
	public static boolean hasData(String player) {
		player = player.toLowerCase();
		File file = new File(MainDB.main.getDataFolder() + "//playerdata//" + player + ".yml");
		return file.exists();
	}
	
	public static File getFile(String player) {
		player = player.toLowerCase();
		File file = new File(MainDB.main.getDataFolder() + "//playerdata//" + player + ".yml");
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return file;
	}
	
	public static DBPlayerData fromFile(File file) {
		FileConfiguration storage = YamlConfiguration.loadConfiguration(file);
		int lastDay = storage.getInt("last-day");
		long lastTime = storage.getLong("last-time");
		DBPlayerData data = new DBPlayerData(lastDay, lastTime);
		return data;
	}
	
	public static void saveData(String player, DBPlayerData data) {
		player = player.toLowerCase();
		File file = getFile(player);
		FileConfiguration storage = YamlConfiguration.loadConfiguration(file);
		storage.set("last-day", data.lastDay);
		storage.set("last-time", data.lastTime);
		try {
			storage.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DBPlayerData getData(String player) {
		player = player.toLowerCase();
		File file = getFile(player);
		return fromFile(file);
	}
	
}
