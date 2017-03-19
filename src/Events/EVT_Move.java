package Events;

import java.util.List;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import Enums.GameTime;

public class EVT_Move implements Listener
{


	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{

		Location spawn = e.getPlayer().getWorld().getSpawnLocation();
		List<Entity> ent = e.getPlayer().getWorld().getEntities();

		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.LOBBY)
		{
			if(e.getPlayer().getLocation().getY() <= 40)
				e.getPlayer().teleport(SurvivalGames.getInstance().getLobby());
		}

		if (SurvivalGames.getInstance().getGame().getTime() == GameTime.PREGAME)
		{
			if (SurvivalGames.getInstance().getFrozen().contains(e.getPlayer().getName()) && e.getTo().getX() != e.getFrom().getX() || e.getTo().getZ() != e.getFrom().getZ())
			{
				e.setTo(e.getFrom());
			}
		}


		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.DEATHMATCH)
		{
			for(Entity entity : ent)
			{

				if(entity instanceof Player && entity.getLocation().distance(spawn) > SurvivalGames.getInstance().getConfigHandler().getRadius())
				{
					Player p = (Player) entity;
					p.setHealth((double)(p.getHealth() - 0.1));
					p.sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + ChatColor.RED + "§lYou cannot leave spawn during deathmatch!");
					p.getWorld().playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 10, 1);
				}
			}
		}
	}
