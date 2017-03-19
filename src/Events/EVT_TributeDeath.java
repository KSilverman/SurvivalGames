package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import Misc.Items;
import Objects.Tribute;

public class EVT_TributeDeath implements Listener
{


	@EventHandler
	public void onDeath(TributeDeathEvent e)
	{
		Tribute killed = e.getTribute();
		int points = getLostPoints(killed.getPoints());


		if (e.getKiller() == null)
		{
			SurvivalGames.getInstance().getGame().setAlive(SurvivalGames.getInstance().getGame().getAlive() - 1);
			killed.setSpectator(true);
			killed.setDeaths(killed.getDeaths() + 1);
			killed.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§cYou died and lost §e" + points + " §cpoints!");
			killed.setPoints(killed.getPoints() - points);
			if (SurvivalGames.getInstance().getGame().getAlive() == 4)
				SurvivalGames.getInstance().getGame().init_deathmatch_countdown();
			else if (SurvivalGames.getInstance().getGame().getAlive() == 1)
			{
				for (Player p : Bukkit.getServer().getOnlinePlayers())
					p.setAllowFlight(true);
				//!f
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SurvivalGames.getInstance(), new Runnable()
				{
					@Override
					public void run()
					{
						for(Tribute t : SurvivalGames.getInstance().getPlayers().values())
							if(!t.isSpectator())
								if(t.getPlayer() != null)
								{
									Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§b§l" + t.getName() + " §ahas won the Survival Games!!!");
									for(Tribute tt : SurvivalGames.getInstance().getPlayers().values())
										if(tt.getPlayer() != null)
											if(tt.getBet().equals(t.getName()))
												tt.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§6You gambled and won " + tt.getBetAmount());
								}
					}
				}, 0L, 20L * 3);
				//f
				SurvivalGames.getInstance().getGame().end();
			}
			return;
		}
		final Tribute killer = e.getKiller();

		killed.getPlayer().teleport(killed.getPlayer().getWorld().getSpawnLocation());
		killed.setSpectator(true);

		killed.setDeaths(killed.getDeaths() + 1);

		killed.setPoints(killed.getPoints() - points);
		killer.setPoints(killer.getPoints() + points);

		killed.setKillStreak(0);
		killer.setKillStreak(killer.getKillSterak() + 1);

		if(killer.getKillSterak() == 3)
		{
			Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + ChatColor.RED + "" + ChatColor.BOLD + killer.getPlayer().getName() + ChatColor.BLUE + "" + ChatColor.BOLD + " IS ON A KILLSTREAK OF 3!!");
			killer.getPlayer().getInventory().addItem(Items.balls);
		}

		if(killer.getKillSterak() == 5)
		{
			Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + ChatColor.RED + "" + ChatColor.BOLD + killer.getPlayer().getName() + ChatColor.BLUE + "" + ChatColor.BOLD + " IS ON A KILLSTREAK OF 5!!");
			killer.getPlayer().getInventory().addItem(Items.plate);
		}



		killed.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§cYou died and lost §e" + points + " §cpoints!");
		killer.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§aYou killed §e" + killed.getName() + " §aand gained §e" + points + " §apoints!");

		SurvivalGames.getInstance().getGame().setAlive(SurvivalGames.getInstance().getGame().getAlive() - 1);

		if (SurvivalGames.getInstance().getGame().getAlive() == 4)
			SurvivalGames.getInstance().getGame().init_deathmatch_countdown();
		else if (SurvivalGames.getInstance().getGame().getAlive() == 1)
		{
			for (Player p : Bukkit.getServer().getOnlinePlayers())
				p.setAllowFlight(true);
			//!f
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SurvivalGames.getInstance(), new Runnable()
			{
				@Override
				public void run()
				{
					Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§b§l" + killer.getName() + " §ahas won the Survival Games!!!");
				}
			}, 0L, 20L * 3);
			//f
			SurvivalGames.getInstance().getGame().end();
		}
	}



	public int getLostPoints(int points)
	{
		if (points >= 100)
		{
			int noh = points / 100;
			double lost = noh * (0.5);
			return (int) (lost * 10);
		}
		if (points > 0 && points <= 25)
			return 1;
		if (points > 26 && points <= 50)
			return 2;
		if (points > 50 && points <= 75)
			return 3;
		if (points > 76 && points <= 99)
			return 4;
		return 0;
	}
}