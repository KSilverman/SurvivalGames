package Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;
import Objects.GameMap;
import Objects.Podium;

public class CMD_AddSpawn
{


	@CommandHandler(name = "addspawn")
	public void onCommand(Player sender, String[] args)
	{
		GameMap map = GameMap.getMap(sender.getWorld().getName());
		if (!sender.hasPermission("HungerGames.SetSpawn"))
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou don't have permission to do this!");
			return;
		}
		/**
        if(map == null){
        	map = new GameMap();
        	map.setName(sender.getWorld().getName());
        	sender.sendMessage(ChatColor.GREEN + "There was no GameMap for this world. Making a new one for " + sender.getWorld().getName() + ", you must also do the command again!");
            map.getPodiums().add(new Podium(sender.getLocation()));
            map.save();
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§aSpawn location added.");
        }
		 **/
		try
		{
			map.getPodiums().add(new Podium(sender.getLocation()));
			map.save();
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§aSpawn location added.");
		}
		catch(NullPointerException npe)
		{
			sender.sendMessage(ChatColor.GRAY + "Map is null");
			System.out.println("Error for map " + map.getName() + ", it is null.");
		}
	}
}
