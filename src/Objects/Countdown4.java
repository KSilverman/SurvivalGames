package Objects;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Countdown4 {
	int time;
	int id;
	int[] intervals = new int[] { 1800, 1500, 1200, 900, 600, 300, 240, 120, 60, 45, 30, 20, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };



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
							if(interval >= 60)
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§9§lThe game is starting in " + time/60 + " minutes!");
							else 
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§9§lThe game is starting in " + time + " seconds!");
							for (Player p : Bukkit.getServer().getOnlinePlayers())
							{
								p.playSound(p.getLocation(), Sound.BURP, 1.0F, 2.0F);
							}
						}
					}
					for(Player p : Bukkit.getOnlinePlayers())
					{
						p.setLevel(time);
					}
					time--;
				}
				else
				{
					cancelCountdown(id);
					System.out.println("Countdown4 is finished.");
				}
			}
		}, 20L, 20L);
	}



	public static void cancelCountdown(int taskID)
	{
		Bukkit.getServer().getScheduler().cancelTask(taskID);
	}


	public void setTime(int x)
	{
		this.time = x;
	}


	public int getTime()
	{
		return time;
	}
}
