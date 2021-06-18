package kdvn.plugin.dailybonus.object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import kdvn.plugin.dailybonus.main.MainDB;

public class DBGUI {
	
	public static final String TITLE = "§2§lĐIỂM DANH HẰNG NGÀY";
	
	public static Inventory getGUI(Player player) {
		Inventory inv = Bukkit.createInventory(null, MainDB.GUI_SIZE, TITLE);
		
		Bukkit.getScheduler().runTaskAsynchronously(MainDB.main, () -> {
			for (int i = 0 ; i < DBTime.times.size() ; i++) {
				inv.setItem(i, getIcon(player, i + 1));
			}
		});
		
		return inv;
	}
	
	public static void eventGUI(InventoryClickEvent e) {
		if (!e.getView().getTitle().equals(TITLE)) return;
		e.setCancelled(true);
		if (e.getClickedInventory() != e.getWhoClicked().getOpenInventory().getTopInventory()) return;
		int slot = e.getSlot();
		Player player = (Player) e.getWhoClicked();
		if (slot <= DBTime.times.size()) {
			int day = slot + 1;
			if (e.getInventory().getItem(slot) == null) return;
			DBPlayerData data = DBPlayerData.data.get(player.getName());
			DBStat stat = DBStat.getStat(data, day);
			if (stat == DBStat.CAN_CLAIM || player.hasPermission("db.*")) {
				DBAPI.claim(player, day);
				player.sendMessage("§aBạn đã nhận quà điểm danh ngày #" + day);
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
				player.closeInventory();
			}
		}
	}
	
	public static void eventGUI(InventoryDragEvent e) {
		if (e.getView().getTitle().equals(TITLE)) e.setCancelled(true);
	}
	
	private static ItemStack getIcon(Player player, int day) {
		DBStat stat = DBStat.getStat(DBPlayerData.data.get(player.getName()), day);
		DBTime dbTime = DBTime.times.get(day);
		DBPlayerData data = DBPlayerData.data.get(player.getName());
		
		ItemStack icon = new ItemStack(stat.material, 1, (short) stat.durability);
		
		String name = stat.color + "Điểm danh ngày #" + day;
		List<String> lore = new ArrayList<String> ();
		lore.add("§6Phần thưởng:");
		dbTime.info.forEach(s -> {
			lore.add(s.replace("&", "§"));
		});
		
		// DBPerm
		dbTime.dbperms.forEach(dbp -> {
			if (player.hasPermission(new Permission(dbp.getPermission(), PermissionDefault.FALSE))) {
				lore.add(dbp.getLore());
			}
		});
		
		if (stat == DBStat.CAN_CLAIM) {
			lore.add("");
			lore.add(stat.color + "Click để nhận");
		}
		if (stat == DBStat.CANT_CLAIM) {
			lore.add("");
			if (day == data.lastDay + 1) {
				lore.add(stat.color + "Còn " + DBAPI.getRemain(data, day));
			}
			else lore.add(stat.color + "Chưa thể nhận");
		}
		if (stat == DBStat.CLAIMED) {
			lore.add("");
			lore.add(stat.color + "Đã nhận");
		}
		
		
		ItemMeta meta = icon.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);

		if (MainDB.SPECIAL_DAYS.contains(day + "")) {
			meta.addEnchant(Enchantment.DURABILITY, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		
		icon.setItemMeta(meta);
		icon.setAmount(day);
		
		return icon;
	}
	
}
