package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import Objects.Tribute;

public class EVT_PVP implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageByEntityEvent e) 
	{
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) 
		{
			Player p = (Player) e.getEntity();
			Player p2 = (Player) e.getDamager();
			if (SurvivalGames.getInstance().getGame().isGrace())
				e.setCancelled(true);
			if (Tribute.getTribute(p).isSpectator() || Tribute.getTribute(p2).isSpectator())
				e.setCancelled(true);
		}
		if (!(e.getEntity() instanceof Player))
		{
			return;
		}
		final Player victim = (Player) e.getEntity();
		Player attacker = null;
		if (e.getDamager() instanceof Player)
		{
			attacker = (Player) e.getDamager();
		}
		else if (e.getDamager() instanceof Arrow)
		{
			Arrow arrow = (Arrow) e.getDamager();
			if (!(arrow.getShooter() instanceof Player)) 
			{
				return;
			}
			attacker = (Player) arrow.getShooter();
			arrow.remove();
		} 
		else if (e.getDamager() instanceof Snowball)
		{
			Snowball snowball = (Snowball) e.getDamager();
			if (!(snowball.getShooter() instanceof Player)) 
			{
				return;
			}
			attacker = (Player) snowball.getShooter();
			SurvivalGames.frozenn.add(victim.getName());
			SurvivalGames.getInstance().hitt = victim.getLocation();
			new Thread(new Runnable() {
				private int good = 2;

				public void run()
				{
					try
					{
						for (int i = 0; i < 2; i++) 
						{
							good--;
							Thread.sleep(1000);
						}
						if (good == 0) 
						{
							SurvivalGames.frozenn.remove(victim.getName());
							victim.sendMessage(ChatColor.GREEN + "§lYou Are UnFrozen!");
							good = 3;
						}
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}).start();
		}

		if (victim == attacker || attacker == null) 
		{
			return;
		}
	}

}
