package Commands;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;
import Enums.GameTime;

public class CMD_Delay {
	@CommandHandler(name = "timerset")
	public void onCommand(Player sender, String[] args)
	{
		if(SurvivalGames.owners.contains(sender.getName()) || SurvivalGames.admins.contains(sender.getName()) || SurvivalGames.coders.contains(sender.getName()) || SurvivalGames.mods.contains(sender.getName()))
		{
			if(SurvivalGames.getInstance().getGame().getTime() == GameTime.LOBBY)
			{
				if(args.length == 1)
				{
					try
					{
						if(Integer.valueOf(args[0]).intValue() < SurvivalGames.getInstance().getConfig().getInt("vote-time") && Integer.valueOf(args[0]).intValue() > 0)
						{
							SurvivalGames.timeChange = true;
							SurvivalGames.temp = Integer.valueOf(args[0]).intValue();
							Bukkit.broadcastMessage(ChatColor.BOLD + sender.getName() + ChatColor.DARK_GREEN + " has changed the countdown timer to " + "§C§l" + String.valueOf(SurvivalGames.getInstance().getVotingTime()) + ChatColor.DARK_GREEN + " seconds!");
						}
						else
						{
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§cYou can only choose numbers from 0 to " + String.valueOf(SurvivalGames.getInstance().getConfig().getInt("vote-time")));
							return;
						}
					}
					catch (NumberFormatException nfe)
					{
						sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§cThat's not even a number!");
						return;
					}
				}
				else
				{
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§cInproper format! /timerset <int>!");
					return;
				}
			}
			else
			{
				sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§cYou can only use this command during lobby!");
				return;
			}
		}
		else
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§cYou do not have permission to use this command!");
			return;
		}
	}
}
