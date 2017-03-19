package Commands;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;

public class CMD_DeathMatch {
	@CommandHandler(name = "deathmatch")
	public void on(Player sender, String[] args)
	{
		if (sender.hasPermission("HungerGames.DeathMatch"))
		{
			Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§6" + sender.getName() + " forced deathmatch to happen!");
			SurvivalGames.getInstance().getGame().init_deathmatch_countdown();
		}
	}
}
