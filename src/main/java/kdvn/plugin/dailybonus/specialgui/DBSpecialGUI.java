package kdvn.plugin.dailybonus.specialgui;

import java.util.HashMap;
import java.util.Map;

import kdvn.plugin.dailybonus.object.DBTime;

public class DBSpecialGUI {
	
	public String title;
	
	public long interval;
	public long start;
	public long end;
	
	public Map<Integer, DBTime> slots = new HashMap<Integer, DBTime> ();
	
	public DBSpecialGUI(String title, long interval, long start, long end) {
		this.title = title;
		this.interval = interval;
		this.start = start;
		this.end = end;
	}
	
}
