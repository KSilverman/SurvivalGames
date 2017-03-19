package Commands;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;

public class CMD_ForceStart {

	@CommandHandler(name="forcestart",permission="HungerGames.ForceStart")
	public void onCommand(Player sender, String[] args)
	{
		if(!sender.hasPermission("HungerGames.ForceStart"))
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou don't have permission to start the game!");
			return;
		}

		sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§6Force starting game! §4[WARNING]");
		SurvivalGames.getInstance().getGame().start();

	}
}
