package Commands;

import java.util.Random;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Commands.SimpleCommand.CommandHandler;
import Enums.GameTime;
import Objects.Tribute;

public class CMD_Item {

	@CommandHandler(name = "item")
	public void onCommand(Player sender, String[] args) {

		if ((SurvivalGames.owners.contains(sender.getName())
				|| SurvivalGames.admins.contains(sender.getName())
				|| SurvivalGames.mods.contains(sender.getName())
				|| SurvivalGames.vips.contains(sender.getName())
				|| SurvivalGames.coders.contains(sender.getName())
				|| SurvivalGames.builders.contains(sender.getName())
				|| SurvivalGames.masters.contains(sender.getName())
				|| SurvivalGames.legends.contains(sender.getName()) 
				|| SurvivalGames.gods.contains(sender.getName()))) 
		{

			if (args.length == 0)
			{

				if (SurvivalGames.getInstance().getGame().getTime() == GameTime.GAME)
				{


					if (!(Tribute.getTribute(sender).isSpectator())) 
					{

						if (!(SurvivalGames.getInstance().getItemHolders().contains(sender.getName()))) 
						{

							if (SurvivalGames.masters.contains(sender.getName())|| SurvivalGames.legends.contains(sender.getName()))
							{
								ItemStack[] items = SurvivalGames
										.getInstance()
										.getTierOne()
										.toArray(
												new ItemStack[SurvivalGames
												              .getInstance().getTierOne()
												              .size() - 1]);
								int select = new Random().nextInt(items.length);
								sender.getInventory().addItem(items[select]);
								sender.sendMessage(ChatColor.GOLD + "["
										+ ChatColor.ITALIC + "Survival Games"
										+ ChatColor.GOLD + "] " + ChatColor.AQUA
										+ "You recieved a " + ChatColor.YELLOW
										+ "§l" + items[select].getType().toString());
								SurvivalGames.getInstance().getItemHolders()
								.add(sender.getName());
								return;
							}
							else
							{
								ItemStack[] items = SurvivalGames
										.getInstance()
										.getTierTwo()
										.toArray(
												new ItemStack[SurvivalGames
												              .getInstance().getTierTwo()
												              .size() - 1]);
								int select = new Random().nextInt(items.length);
								sender.getInventory().addItem(items[select]);
								sender.sendMessage(ChatColor.GOLD + "["
										+ ChatColor.ITALIC + "Survival Games"
										+ ChatColor.GOLD + "] " + ChatColor.AQUA
										+ "You recieved a " + ChatColor.YELLOW
										+ "§l" + items[select].getType().toString());
								SurvivalGames.getInstance().getItemHolders()
								.add(sender.getName());
								return;
							}
						} 
						else
						{
							sender.sendMessage(ChatColor.GOLD
									+ "["
									+ ChatColor.ITALIC
									+ "Survival Games"
									+ ChatColor.GOLD
									+ "] "
									+ ChatColor.RED
									+ "You can only use this command once per game!");
							return;
						}
					} 
					else
					{
						sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC
								+ "Survival Games" + ChatColor.GOLD + "] "
								+ ChatColor.RED
								+ "You must be a tribute to use this command!");
						return;
					}
				} 
				else
				{
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC
							+ "Survival Games" + ChatColor.GOLD + "] "
							+ ChatColor.RED
							+ "You can only use this command during the game!");
					return;
				}
			}
			else
			{
				sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC
						+ "Survival Games" + ChatColor.GOLD + "] "
						+ ChatColor.RED + "Incorrect usage! /item");
				return;
			}
		} 
		else
		{
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC
					+ "Survival Games" + ChatColor.GOLD + "] "
					+ ChatColor.LIGHT_PURPLE
					+ "You must be a donor to use this command! Go to "
					+ "\nhttp://www.theparkmc.com/shop to purchase a rank!");
			return;
		}
	}
}
