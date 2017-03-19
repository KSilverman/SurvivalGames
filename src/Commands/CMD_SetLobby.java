package Commands;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;

public class CMD_SetLobby {
	@CommandHandler(name = "setlobby")
	public void onCommand(Player sender, String[] args)
	{
		if (sender.hasPermission("HungerGames.SetLobby"))
		{
			SurvivalGames.getInstance().setLobby(sender.getLocation());
			Location loc = sender.getLocation();
			SurvivalGames.getInstance().getConfig().set("lobby.world", loc.getWorld().getName());
			SurvivalGames.getInstance().getConfig().set("lobby.x", loc.getX());
			SurvivalGames.getInstance().getConfig().set("lobby.y", loc.getY());
			SurvivalGames.getInstance().getConfig().set("lobby.z", loc.getZ());
			SurvivalGames.getInstance().getConfig().set("lobby.pitch", loc.getPitch());
			SurvivalGames.getInstance().getConfig().set("lobby.yaw", loc.getYaw());
			SurvivalGames.getInstance().saveConfig();
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§aLobby set!");
		}
	}
}
