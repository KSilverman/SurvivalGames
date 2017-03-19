package Misc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

	private static ItemStack yt = new ItemStack(Material.SNOW_BALL, 2);
	private static ItemStack stp = new ItemStack(Material.STONE_PLATE, 5);

	private static ItemStack mp = new ItemStack(Material.MAP);
	public static final ItemStack pMap = setName(mp, "Spectator Teleporter", ChatColor.BOLD);


	public static ItemStack balls = setName(yt, "§lFrozen Snowball", ChatColor.DARK_GRAY);
	public static ItemStack plate = setName(stp, "§lTnT Pressure Plate", ChatColor.GOLD);

	public static ItemStack compass = new ItemStack(Material.COMPASS);
	public static final ItemStack lobbyCompass = setName(compass, "Return To Hub", ChatColor.BLUE);

	private static ItemStack setName(ItemStack is, String name, ChatColor colour)
	{
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(colour + name);
		is.setItemMeta(im);
		return is;
	}

}
