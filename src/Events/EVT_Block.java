package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import Enums.GameTime;
import Objects.Tribute;

public class EVT_Block implements Listener
{


	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e)
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
		Material type = e.getBlock().getType();
		for (Material mat : SurvivalGames.getInstance().getConfigHandler().getBreakables())
		{
			if (type.equals(mat) || type.equals(Material.LEAVES) || type.equals(Material.CAKE) || type.equals(Material.CAKE_BLOCK) || type.equals(Material.BOAT) || type.equals(Material.getMaterial(39)) || type.equals(Material.getMaterial(40)))
			{
				continue;
			}
			if (!e.getPlayer().isOp())
				e.setCancelled(true);
		}
	}



	@EventHandler
	public void onBreak(BlockPlaceEvent e)
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
		Material type = e.getBlock().getType();
		for (Material mat : SurvivalGames.getInstance().getConfigHandler().getPlaceables())
		{
			if (type.equals(mat) || type.equals(Material.CAKE) || type.equals(Material.CAKE_BLOCK) || type.equals(Material.BOAT) || type.equals(Material.STONE_PLATE) || type.equals(Material.FLINT_AND_STEEL))
			{
				continue;
			}
			if (!e.getPlayer().isOp())
				e.setCancelled(true);
		}
	}
}
