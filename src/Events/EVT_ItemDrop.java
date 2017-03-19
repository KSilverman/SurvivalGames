package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import Enums.GameTime;
import Objects.Tribute;

public class EVT_ItemDrop implements Listener
{


	@EventHandler
	public void onDrop(PlayerDropItemEvent e)
	{
		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.LOBBY)
		{
			e.setCancelled(true);
		}
		if (Tribute.getTribute(e.getPlayer()).isSpectator())
		{
			e.setCancelled(true);
			return;
		}
	}
}
