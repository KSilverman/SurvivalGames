package Objects;

import java.util.Random;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import Enums.GameTime;

public class Game {
	int alive;
	boolean grace;
	GameMap map;
	GameTime time;
	Tribute pl;




	public Game()
	{
		alive = 0;
	}



	//=======================================================


	public void start()
	{
		alive = Bukkit.getServer().getOnlinePlayers().length;
		time = GameTime.GAME;

		if (map == null)
		{
			GameMap[] maps = SurvivalGames.getInstance().getMaps().values().toArray(new GameMap[SurvivalGames.getInstance().getMaps().values().size() - 1]);
			int select = new Random().nextInt(maps.length);
			map = maps[select];
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§e§lForce selecting map: §r" + map.getName());
		}

		for (Player p : Bukkit.getServer().getOnlinePlayers())
		{
			for (Podium pp : map.podiums)
			{
				if (!pp.isOccupied())
				{
					p.teleport(pp.getLocation());
					pp.setOccupied(true);
				}
			}
			p.getInventory().clear();
			p.getActivePotionEffects().clear();
			p.setHealth(20.0);
			p.setFoodLevel(20);
			SurvivalGames.getInstance().getFrozen().remove(p.getName());
			p.getLocation().getWorld().setTime(0);
		}


		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SurvivalGames.getInstance(), new Runnable()
		{

			@Override
			public void run(){

				for(Player p : Bukkit.getOnlinePlayers())
				{
					pl = Tribute.getTribute(p); 	
					for(Player pp : Bukkit.getOnlinePlayers())
					{
						if(pl.isSpectator())
							pp.hidePlayer(pl.getPlayer());
					}
				}


			}
		}, 20L * SurvivalGames.getInstance().getConfigHandler().getGameTime());

		Bukkit.getServer().broadcastMessage(SurvivalGames.getInstance().getConfigHandler().getGraceMessage());
		setGrace(true);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SurvivalGames.getInstance(), new Runnable()
		{


			@Override
			public void run()
			{
				setGrace(false);
				Bukkit.getServer().broadcastMessage(SurvivalGames.getInstance().getConfigHandler().getGraceEndedMessage());
				for (Player p : Bukkit.getServer().getOnlinePlayers())
					p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0F, 2.0F);
			}
		}, 20L * SurvivalGames.getInstance().getConfigHandler().getGraceTime());


		SurvivalGames.getInstance().init_gamecoutdown();

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SurvivalGames.getInstance(), new Runnable()

		{

			@Override
			public void run()
			{
				init_deathmatch_countdown();
			}
		}, 20L * 60 * 30);

	}



	public void end()
	{
		for(final Player p : Bukkit.getOnlinePlayers()){
			//!f
			time = GameTime.RESTARTING;
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SurvivalGames.getInstance(), new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						SurvivalGames.redirectRequest("hub", p);
						Thread.sleep(200);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
				}
			}, 20L * SurvivalGames.getInstance().getConfigHandler().getTimeBeforeShutdown());
			//f
		}
	}



	public void init_deathmatch()
	{
		time = GameTime.DEATHMATCH;
		map = SurvivalGames.getInstance().getGame().getMap();


		Podium[] podium = map.getPodiums().toArray(new Podium[map.getPodiums().size() - 1]);


		for (int i = 0; i < podium.length; i++)
			for (String st : SurvivalGames.getInstance().getAlivePlayers())
			{
				//Bukkit.getPlayer(st).teleport(podium[i].getLocation());
				if(SurvivalGames.getInstance().getPlayerLocations().containsKey(st))
					Bukkit.getPlayer(st).teleport(SurvivalGames.getInstance().getPlayerLocations().get(st));
			}





		for (String t : SurvivalGames.getInstance().getAlivePlayers())
		{
			if (t != null)
			{
				for(int i = 0; i < SurvivalGames.getInstance().getGame().getAlive(); i++)
				{
					Bukkit.getPlayer(t).teleport(SurvivalGames.getInstance().getGame().getMap().getPodiums().get(i).getLocation());
				}
				//t.getPlayer().teleport(Bukkit.getWorld(SurvivalGames.getInstance().getGame().getMap().getName()).getSpawnLocation());
			}
		}
		List<Location> sphere = Shapes.getSphere(Bukkit.getWorld(SurvivalGames.getInstance().getGame().getMap().getName()).getSpawnLocation(), 35, 100, 35, false);
		for(int i = 0; i < sphere.size(); i++)
		{
			Bukkit.getWorld(SurvivalGames.getInstance().getGame().getMap().getName()).getBlockAt(sphere.get(i)).setType(Material.GLASS);
		}

		Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + "§4Fight to the death!");
	}



	public void init_deathmatch_countdown()
	{
		Countdown2 cd = new Countdown2();
		cd.startCountdown(60);
	}


	public void setAlive(int amt)
	{
		this.alive = amt;
	}



	public void setGrace(boolean b)
	{
		this.grace = b;
	}



	public void setMap(String name)
	{
		this.map = GameMap.getMap(name);
	}



	public void setGameTime(GameTime time)
	{
		this.time = time;
	}



	//=======================================================


	public boolean isGrace()
	{
		return grace;
	}



	public GameMap getMap()
	{
		return map;
	}



	public int getAlive()
	{
		return alive;
	}



	public GameTime getTime()
	{
		return time;
	}
}
