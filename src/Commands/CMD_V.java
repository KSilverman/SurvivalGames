package Commands;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;
import Enums.GameTime;

public class CMD_V {
	@CommandHandler(name = "v")
	public void onVote(Player sender, String[] args)
	{

		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.LOBBY)
		{
			String voteMap;
			if (args.length < 1)
			{
				sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou must specify a map to vote for!");
				sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + SurvivalGames.getInstance().getMap(1).getPlayer().getName());
				sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + SurvivalGames.getInstance().getMap(2).getPlayer().getName());
				sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + SurvivalGames.getInstance().getMap(3).getPlayer().getName());
				sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + SurvivalGames.getInstance().getMap(4).getPlayer().getName());
				return;
			}
			if (!SurvivalGames.getInstance().getVoted().contains(sender.getName()))
			{
				try
				{
					if (Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[0]) > 4)
					{
						sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + ChatColor.RED + "Please enter a valid number 1-4!");
						return;
					}
					voteMap = SurvivalGames.getInstance().getMap(Integer.valueOf(args[0])).getPlayer().getName();
					SurvivalGames.getInstance().getMap(Integer.parseInt(args[0])).setScore(SurvivalGames.getInstance().getMap(Integer.parseInt(args[0])).getScore() + 1);
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§aVoted for: " + voteMap);
					if (!sender.isOp())
					{
						SurvivalGames.getInstance().getVoted().add(sender.getName());
					}
				}
				catch (NumberFormatException nfe)
				{
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§cThat's not even a number!");
					return;
				}
				return;
			}
			else
			{
				sender.sendMessage("§cYou can not vote twice!");
				return;
			}
		}
		else
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + ChatColor.RED + "You cannnot vote whilst in game!");
			return;
		}
	}
}
