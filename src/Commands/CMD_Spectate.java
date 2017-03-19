package Commands;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;
import Enums.GameTime;
import Objects.Tribute;

public class CMD_Spectate {
	@CommandHandler(name = "spectate")
	public void onCommand(Player sender, String[] args)
	{
		if (SurvivalGames.getInstance().getGame().getTime() == GameTime.LOBBY || SurvivalGames.getInstance().getGame().getTime() == GameTime.PREGAME || SurvivalGames.getInstance().getGame().getTime() == GameTime.RESTARTING)
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou can not spectate now!");
			return;
		}
		if (!Tribute.getTribute(sender).isSpectator())
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou must be dead to spectate!");
			return;
		}
		if (args.length != 1)
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou must specify a player to teleport to.");
			return;
		}
		Player p = Bukkit.getServer().getPlayerExact(args[0]);
		if (p == null)
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou can not spectate fake players!");
			return;
		}
		sender.teleport(p.getLocation());
		sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§aNow spectating: §e" + args[0]);
	}
}
