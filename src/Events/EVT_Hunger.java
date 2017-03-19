package Events;

import java.util.Random;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import Enums.GameTime;
import Objects.Tribute;

public class EVT_Hunger implements Listener{

	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent event) {

		Player player = (Player) event.getEntity();

		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.LOBBY)
		{
			event.setCancelled(true);
		}

		if (Tribute.getTribute(player).isSpectator())
		{
			event.setCancelled(true);
			return;
		}
		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.GAME || SurvivalGames.getInstance().getGame().getTime() == GameTime.DEATHMATCH)
		{
			if (event.getFoodLevel() < ((Player)event.getEntity()).getFoodLevel())
				event.setCancelled(new Random().nextInt(100) < 66);
		}
	}

}
