package kdvn.plugin.dailybonus.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import kdvn.plugin.dailybonus.object.DBAPI;
import kdvn.plugin.dailybonus.object.DBGUI;

public class DBEventHandling implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		String name = e.getPlayer().getName();
		DBAPI.load(name);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		String name = e.getPlayer().getName();
		DBAPI.save(name);
	}
	
	@EventHandler
	public void onGUI(InventoryClickEvent e) {
		DBGUI.eventGUI(e);
	}
	
	@EventHandler
	public void onGUI2(InventoryDragEvent e) {
		DBGUI.eventGUI(e);
	}
	
}
