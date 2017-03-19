package Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import Objects.Tribute;

public class EVT_ItemPickup implements Listener
{


	@EventHandler
	public void onPickup(PlayerPickupItemEvent e)
	{
		if (Tribute.getTribute(e.getPlayer()).isSpectator())
		{
			e.setCancelled(true);
			return;
		}
	}
}
