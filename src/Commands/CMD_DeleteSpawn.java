package Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;
import Objects.GameMap;

public class CMD_DeleteSpawn {
	@CommandHandler(name = "delspawn")
	public void onCommand(Player sender, String[] args)
	{
		if (!sender.hasPermission("HungerGames.DeleteSpawn"))
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou don't have permission to do this!");
			return;
		}
		GameMap gm = GameMap.getMap(sender.getWorld().getName());
		try
		{
			gm.getPodiums().clear();
			gm.save();
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cDeleted podiums.");
		}
		catch(NullPointerException npe)
		{
			sender.sendMessage(ChatColor.GRAY + "Map is null!");
			System.out.println("Error for map " + gm.getName() + ", it is null.");
		}
	}
}
