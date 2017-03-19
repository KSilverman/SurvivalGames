package Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.BajanAmerican.SurvivalGames.SurvivalGames;
import Objects.Tribute;

public class EVT_KillDeath implements Listener
{


	@EventHandler
	public void onKillDeath(PlayerDeathEvent e)
	{
		Player dead = e.getEntity();
		e.setDeathMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§4§lYou hear a cannon in the distance. A tribute has fallen.");

		if(SurvivalGames.getInstance().getAlivePlayers().contains(dead.getName()))
			SurvivalGames.getInstance().getAlivePlayers().remove(dead.getName());

		if(SurvivalGames.getInstance().getPlayerLocations().containsKey(dead.getName()))
			SurvivalGames.getInstance().getPlayerLocations().remove(dead.getName());

		if (!Tribute.getTribute(dead).isSpectator())
			if (dead.getKiller() != null)
			{
				Bukkit.getServer().getPluginManager().callEvent(new TributeDeathEvent(Tribute.getTribute(dead), Tribute.getTribute(dead.getKiller()), SurvivalGames.getInstance().getGame().getTime()));
				for (Player p : Bukkit.getServer().getOnlinePlayers())
				{
					p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1.0F, 1.0F);
				}
			}
			else
			{
				Bukkit.getServer().getPluginManager().callEvent(new TributeDeathEvent(Tribute.getTribute(dead), null, SurvivalGames.getInstance().getGame().getTime()));
			}
	}


}
