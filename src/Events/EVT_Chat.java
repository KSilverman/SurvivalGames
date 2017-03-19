package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import Objects.Tribute;

public class EVT_Chat implements Listener
{


	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		String origonal = event.getMessage();
		String points = ChatColor.GRAY + "[" + ChatColor.YELLOW + Tribute.getTribute(event.getPlayer()).getPoints() + ChatColor.GRAY + "] ";
		String dead = ChatColor.DARK_RED + "[DEAD]";

		if(Tribute.getTribute(player).isSpectator())
		{
			if(SurvivalGames.mods.contains(player.getName()))
			{
				event.setFormat(dead + points + ChatColor.GRAY + "(" + ChatColor.DARK_AQUA + "Mod" + ChatColor.GRAY + ")" + ChatColor.DARK_AQUA + player.getName() + 
						ChatColor.GRAY + ": " + ChatColor.RESET + "" + origonal);
			} 
			else if(SurvivalGames.owners.contains(player.getName()))
			{
				event.setFormat(dead + points + ChatColor.GRAY + "(" + ChatColor.DARK_RED + "Owner" + ChatColor.GRAY + ")" + ChatColor.DARK_RED + player.getName() + 
						ChatColor.GRAY + ": " + ChatColor.RESET + "" + origonal);;
			}
			else if(SurvivalGames.coders.contains(player.getName()))
			{
				event.setFormat(dead + points + ChatColor.GRAY + "(" + ChatColor.RED + "Coder" + ChatColor.GRAY + ")" + ChatColor.RED + player.getName() + 
						ChatColor.GRAY + ": " + ChatColor.RESET + "" + origonal);
			}  
			else if(SurvivalGames.admins.contains(player.getName()))
			{
				event.setFormat(dead + points + ChatColor.GRAY + "(" + ChatColor.RED + "Admin" + ChatColor.GRAY + ")" + ChatColor.RED + player.getName() + 
						ChatColor.GRAY + ": " + ChatColor.RESET + "" + origonal);
			} 
			else if(SurvivalGames.vips.contains(player.getName()))
			{
				event.setFormat(dead + points + ChatColor.DARK_PURPLE + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
			}
			else if(SurvivalGames.masters.contains(player.getName()))
			{
				event.setFormat(dead + points + ChatColor.GRAY + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
			} 
			else if(SurvivalGames.legends.contains(player.getName()))
			{
				event.setFormat(dead + points + ChatColor.GOLD + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
			} 
			else if(SurvivalGames.gods.contains(player.getName()))
			{
				event.setFormat(dead + points + ChatColor.AQUA + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
			} 
			else if(SurvivalGames.builders.contains(player.getName()))
			{
				event.setFormat(dead + points + ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
			}
			else 
			{
				event.setFormat(dead + points + ChatColor.GREEN + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
			}

			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(!(Tribute.getTribute(p).isSpectator()))
					event.getRecipients().remove(p);
			}
		}


		if(SurvivalGames.mods.contains(player.getName()))
		{
			event.setFormat(points + ChatColor.GRAY + "(" + ChatColor.DARK_AQUA + "Mod" + ChatColor.GRAY + ")" + ChatColor.DARK_AQUA + player.getName() + 
					ChatColor.GRAY + ": " + ChatColor.RESET + "" + origonal);
		}
		else if(SurvivalGames.owners.contains(player.getName()))
		{
			event.setFormat(points + ChatColor.GRAY + "(" + ChatColor.DARK_RED + "Owner" + ChatColor.GRAY + ")" + ChatColor.DARK_RED + player.getName() + 
					ChatColor.GRAY + ": " + ChatColor.RESET + "" + origonal);;
		} 
		else if(SurvivalGames.coders.contains(player.getName()))
		{
			event.setFormat(points + ChatColor.GRAY + "(" + ChatColor.RED + "Coder" + ChatColor.GRAY + ")" + ChatColor.RED + player.getName() + 
					ChatColor.GRAY + ": " + ChatColor.RESET + "" + origonal);
		}  
		else if(SurvivalGames.admins.contains(player.getName()))
		{
			event.setFormat(points + ChatColor.GRAY + "(" + ChatColor.RED + "Admin" + ChatColor.GRAY + ")" + ChatColor.RED + player.getName() + 
					ChatColor.GRAY + ": " + ChatColor.RESET + "" + origonal);
		} 
		else if(SurvivalGames.vips.contains(player.getName()))
		{
			event.setFormat(points + ChatColor.DARK_PURPLE + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
		} 
		else if(SurvivalGames.masters.contains(player.getName()))
		{
			event.setFormat(points + ChatColor.GRAY + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
		} 
		else if(SurvivalGames.legends.contains(player.getName()))
		{
			event.setFormat(points + ChatColor.GOLD + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
		} 
		else if(SurvivalGames.gods.contains(player.getName()))
		{
			event.setFormat(points + ChatColor.AQUA + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
		} 
		else if(SurvivalGames.builders.contains(player.getName()))
		{
			event.setFormat(points + ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
		}
		else
		{
			event.setFormat(points + ChatColor.GREEN + player.getName() + ChatColor.GRAY + ":" + ChatColor.RESET + " " + origonal);
		}
	}
}
