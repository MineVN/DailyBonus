package kdvn.plugin.dailybonus.object;

import java.util.HashMap;
import java.util.Map;

public class DBPlayerData {
	
	public static Map<String, DBPlayerData> data = new HashMap<String, DBPlayerData> ();
	
	public int lastDay;
	public long lastTime;
	
	public DBPlayerData(int lastDay, long lastTime)  {
		this.lastDay = lastDay;
		this.lastTime = lastTime;
	}
	
	
}
