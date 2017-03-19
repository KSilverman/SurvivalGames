package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import Enums.GameTime;

public class EVT_Tag implements Listener {

	@EventHandler
	public void onPlayerTag(PlayerReceiveNameTagEvent event) 
	{
		if (SurvivalGames.getInstance().getGame().getTime() == GameTime.LOBBY || SurvivalGames.getInstance().getGame().getTime() == GameTime.PREGAME)
		{
			if (SurvivalGames.vips.contains(event.getNamedPlayer().getName())) 
			{
				event.setTag(ChatColor.DARK_PURPLE + event.getNamedPlayer().getName());
			} 
			else if (SurvivalGames.mods.contains(event.getNamedPlayer().getName()))
			{
				event.setTag(ChatColor.DARK_AQUA + event.getNamedPlayer().getName());
			}
			else if (SurvivalGames.owners.contains(event.getNamedPlayer().getName()))
			{
				event.setTag(ChatColor.DARK_RED + event.getNamedPlayer().getName());
			} 
			else if (SurvivalGames.admins.contains(event.getNamedPlayer().getName()))
			{
				event.setTag(ChatColor.RED + event.getNamedPlayer().getName());
			} 
			else if (SurvivalGames.builders.contains(event.getNamedPlayer().getName()))
			{
				event.setTag(ChatColor.LIGHT_PURPLE + event.getNamedPlayer().getName());
			} 
			else if (SurvivalGames.coders.contains(event.getNamedPlayer().getName())) 
			{
				event.setTag(ChatColor.RED + event.getNamedPlayer().getName());
			} 
			else if (SurvivalGames.masters.contains(event.getNamedPlayer().getName())) 
			{
				event.setTag(ChatColor.GRAY + event.getNamedPlayer().getName());
			} 
			else if (SurvivalGames.legends.contains(event.getNamedPlayer().getName()))
			{
				event.setTag(ChatColor.GOLD + event.getNamedPlayer().getName());
			} 
			else if (SurvivalGames.gods.contains(event.getNamedPlayer().getName()))
			{
				event.setTag(ChatColor.AQUA + event.getNamedPlayer().getName());
			} 
			else 
			{
				event.setTag(ChatColor.GREEN + event.getNamedPlayer().getName());
			}
		}

		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.GAME)
		{
			event.setTag(ChatColor.RED + event.getNamedPlayer().getName());
		}
	}

}
