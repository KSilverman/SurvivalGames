package Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;
import Objects.Tribute;

public class CMD_Points {
	@CommandHandler(name = "points")
	public void onCommand(Player sender, String[] args)
	{
		if (args.length != 3)
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§aCurrent balance: §b" + Tribute.getTribute(sender).getPoints());
			return;
		}

		if (!sender.hasPermission("HungerGames.Points"))
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou can't work with points!");
			return;
		}

		if (!(args[0].equals("add") || args[0].equals("remove")))
			return;


		Player p = Bukkit.getServer().getPlayer(args[1]);
		int amount = Integer.parseInt(args[2]);

		if (args[0].equalsIgnoreCase("add"))
		{
			Tribute.getTribute(p).setPoints(Tribute.getTribute(p).getPoints() + amount);
			p.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§aYou were given " + amount + " points by §e" + sender.getDisplayName());
		}
		else if (args[0].equalsIgnoreCase("remove"))
		{
			Tribute.getTribute(p).setPoints(Tribute.getTribute(p).getPoints() + amount);
			p.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§cYou lost " + amount + " points, by §e" + sender.getDisplayName());
		}

	}
}
