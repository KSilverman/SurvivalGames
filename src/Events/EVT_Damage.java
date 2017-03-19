package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import Enums.GameTime;
import Objects.Tribute;

public class EVT_Damage implements Listener
{


	@EventHandler
	public void onDamge(EntityDamageEvent e)
	{
		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.LOBBY || SurvivalGames.getInstance().getGame().getTime() == GameTime.PREGAME || SurvivalGames.getInstance().getGame().getTime() == GameTime.RESTARTING)
		{
			e.setCancelled(true);
		}
		if (e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if (Tribute.getTribute(p).isSpectator())
				e.setCancelled(true);
		}
	}
}
