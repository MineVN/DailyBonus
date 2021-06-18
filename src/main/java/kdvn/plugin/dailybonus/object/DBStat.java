package kdvn.plugin.dailybonus.object;

import org.bukkit.Material;

public enum DBStat {
	
	CAN_CLAIM("Có thể nhận", "§a", Material.LEGACY_STAINED_GLASS_PANE, 5),
	CANT_CLAIM("Chưa thể nhận", "§c", Material.LEGACY_STAINED_GLASS_PANE, 14),
	CLAIMED("Đã nhận", "§2", Material.LEGACY_STAINED_GLASS_PANE, 13);
	
	public String color;
	public String name;
	public Material material;
	public int durability;
	
	private DBStat(String name, String color, Material material, int durability) {
		this.name = name;
		this.color = color;
		this.material = material;
		this.durability = durability;
	}
	
	public static DBStat getStat(DBPlayerData data, int day)  {
		if (DBAPI.isClaimed(data, day)) return CLAIMED;
		if (DBAPI.canClaim(data, day)) return CAN_CLAIM;
		return CANT_CLAIM;
	}
	
}
