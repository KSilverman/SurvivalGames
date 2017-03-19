package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import Objects.Tribute;

public class EVT_Click implements Listener {

	@EventHandler
	public void onPlayerInvClick(InventoryClickEvent e)
	{
		Player p = (Player) e.getWhoClicked();
		Tribute trib = Tribute.getTribute(p);

		if(trib.isSpectator())
		{
			e.setCancelled(true);
			if(e.getInventory().getName().equals(ChatColor.BOLD + "Spectator Teleporter"))
			{
				if(e.getCurrentItem() == null)
				{
					e.setCancelled(true);
					return;
				}

				String name = e.getCurrentItem().getItemMeta().getDisplayName();
				if(name == null)
				{
					return;
				}

				Player px = Bukkit.getPlayer(name);
				if(px != null)
				{
					if(SurvivalGames.getInstance().getAlivePlayers().contains(name))
					{
						p.teleport(px);
						p.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + ChatColor.GREEN + "Teleported you to " + ChatColor.BLUE + name);
						p.closeInventory();
					}
				}
				else
					p.closeInventory();
			}
		}


	}

}
