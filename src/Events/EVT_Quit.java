package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import Enums.GameTime;
import Objects.Tribute;

public class EVT_Quit implements Listener
{


	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		e.setQuitMessage(null);

		if(SurvivalGames.getInstance().getAlivePlayers().contains(e.getPlayer().getName()))
			SurvivalGames.getInstance().getAlivePlayers().remove(e.getPlayer().getName());

		Tribute t = Tribute.getTribute(e.getPlayer());
		t.setKillStreak(0);
		if (SurvivalGames.getInstance().getGame().getTime() != GameTime.LOBBY)
		{
			if (!t.isSpectator())
			{
				Bukkit.getServer().getPluginManager().callEvent(new TributeDeathEvent(t, null, SurvivalGames.getInstance().getGame().getTime()));
			}
		}
	}
}
