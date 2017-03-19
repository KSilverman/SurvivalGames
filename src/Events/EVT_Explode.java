package Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import Objects.GameMap;

public class EVT_Explode implements Listener {

	GameMap map;

	@EventHandler
	public void onExplode(EntityExplodeEvent event)
	{
		event.blockList().clear();
	}

}
