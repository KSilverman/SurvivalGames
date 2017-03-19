package Commands;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;
import Objects.Tribute;

public class CMD_Gamble {
	@CommandHandler(name = "bounty")
	public void onCommand(Player sender, String[] args)
	{
		Tribute t = Tribute.getTribute(sender);
		if (!t.hasBet())
		{
			if (!t.isSpectator())
			{
				sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou can not gamble unless you are spectating!");
				return;
			}
			if (args.length != 2)
			{
				sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou must supply a name to bet on, and an amount!");
				return;
			}
			else if (args.length > 2)
			{
				Player p = Bukkit.getServer().getPlayer(args[0]);
				if (p == null)
				{
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou can not vote for an offline player!");
					return;
				}
				if (Tribute.getTribute(p).isSpectator())
				{
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou can not vote for a dead player!");
					return;
				}
				for (Tribute tt : SurvivalGames.getInstance().getPlayers().values())
				{
					if (tt.hasBet())
					{
						if (args[0].equals(tt.getBet()))
						{
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cSomeone else has already placed bets on this person!");
							return;
						}
					}
				}
				try
				{
					int bet = Integer.parseInt(args[1]);
					if (t.getPoints() < bet)
					{
						sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou can not bet more than you have!");
						return;
					}
					if (bet > 0)
					{
						t.setBet(args[1], bet);
						Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§e" + sender.getName() + " bet " + bet + " on " + p.getName() + "!");
					}
				}
				catch (NumberFormatException nfe)
				{
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cInvalid amount to bet!");
					return;
				}
			}
		}
		else
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou can not bet twice!");
			return;
		}
	}
}
