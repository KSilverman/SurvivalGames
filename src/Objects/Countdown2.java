package Objects;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Countdown2 {
	int time;
	int id;
	int[] intervals = new int[] { 240, 120, 60, 45, 30, 20, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };



	public void startCountdown(int seconds)
	{
		this.time = seconds;
		this.id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SurvivalGames.getInstance(), new Runnable()
		{


			@Override
			public void run()
			{
				if (time > 0)
				{
					for (int interval : intervals)
					{
						if (time == interval)
						{
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§4Deathmatch in " + time + " seconds!");
							for (Player p : Bukkit.getServer().getOnlinePlayers())
							{
								p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 2.0F);
							}
						}
					}
					time--;
				}
				else
				{
					cancelCountdown(id);
					System.out.println("Countdown is finished.");
					SurvivalGames.getInstance().getGame().init_deathmatch();
				}
			}
		}, 20L, 20L);
	}



	public static void cancelCountdown(int taskID)
	{
		Bukkit.getServer().getScheduler().cancelTask(taskID);
	}



	public int getTime()
	{
		return time;
	}
}
