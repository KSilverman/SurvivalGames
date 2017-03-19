package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EVT_Consume implements Listener {

	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event)
	{
		final Player player = event.getPlayer();
		final float before = player.getSaturation();

		new BukkitRunnable()
		{
			public void run() 
			{
				float change = player.getSaturation() - before;
				player.setSaturation((float)(before + change * 2.5D));
			}
		}
		.runTaskLater(SurvivalGames.getInstance(), 1L);
	}

}
