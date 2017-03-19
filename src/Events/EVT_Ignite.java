package Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class EVT_Ignite implements Listener {

	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		event.setCancelled(true);
	}

}
