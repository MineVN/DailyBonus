package kdvn.plugin.dailybonus.object;

import org.bukkit.entity.Player;

import kdvn.plugin.dailybonus.storage.FileStorage;

public class DBAPI {
	
	public static String toString(long miliTime) {
		return (miliTime / 3600000) + "h " + ((miliTime % 3600000) / 60000) + "'";
	}
	
	public static String getRemain(DBPlayerData data, int day) {
		int sub = 1;
		return toString((long) sub * 86400000 - (System.currentTimeMillis() - data.lastTime));
	}
	
	public static boolean canClaimNext(DBPlayerData data) {
		return System.currentTimeMillis() - data.lastTime >= 86400 * 1000;
	}
	
	public static boolean canClaim(DBPlayerData data, int day) {
		int lastDay = data.lastDay;
		if (day <= lastDay + 1) {
			return canClaimNext(data);
		} else return false;
	}
	
	public static boolean isClaimed(DBPlayerData data, int day) {
		return data.lastDay >= day;
	}
	
	public static void claim(Player player, int day) {
		DBPlayerData data = DBPlayerData.data.get(player.getName());
		DBTime dbTime = DBTime.times.get(day);
		dbTime.giveReward(player);
		data.lastDay = day;
		data.lastTime = System.currentTimeMillis();
		if (day == DBTime.times.size()) {
			data.lastDay = 0;
		}
	}
	
	public static void load(String name) {
		DBPlayerData data = FileStorage.getData(name);
		DBPlayerData.data.put(name, data);
	}
	
	public static void save(String name) {
		DBPlayerData data = DBPlayerData.data.get(name);
		DBPlayerData.data.remove(name);
		FileStorage.saveData(name, data);
	}
	
}
