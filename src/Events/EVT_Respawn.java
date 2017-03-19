package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import Enums.GameTime;
import Misc.Items;

public class EVT_Respawn implements Listener
{


	@EventHandler
	public void onRespawn(PlayerRespawnEvent e)
	{
		if (SurvivalGames.getInstance().getGame().getTime() != GameTime.LOBBY)
		{
			e.setRespawnLocation(SurvivalGames.getInstance().getGame().getMap().getPodiums().get(0).getLocation());
			final Player p = e.getPlayer();
			p.setHealth(20);
			p.setAllowFlight(true);
			p.getInventory().setItem(0, Items.lobbyCompass);
			p.getInventory().setItem(8, Items.pMap);
			for(Player pl : Bukkit.getOnlinePlayers())
			{
				pl.hidePlayer(p);
			}
		}
	}
}
