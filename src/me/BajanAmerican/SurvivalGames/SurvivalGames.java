package me.BajanAmerican.SurvivalGames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResultListener;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import Commands.CMD_AddSpawn;
import Commands.CMD_DeathMatch;
import Commands.CMD_Delay;
import Commands.CMD_DeleteSpawn;
import Commands.CMD_ForceStart;
import Commands.CMD_Gamble;
import Commands.CMD_Hub;
import Commands.CMD_Item;
import Commands.CMD_Points;
import Commands.CMD_SetLobby;
import Commands.CMD_Spectate;
import Commands.CMD_V;
import Commands.CMD_Vote;
import Commands.SimpleCommand;
import Enums.GameTime;
import Events.EVT_Block;
import Events.EVT_Chat;
import Events.EVT_Click;
import Events.EVT_Consume;
import Events.EVT_Damage;
import Events.EVT_Explode;
import Events.EVT_Hax;
import Events.EVT_Hunger;
import Events.EVT_Ignite;
import Events.EVT_Interact;
import Events.EVT_ItemDrop;
import Events.EVT_ItemPickup;
import Events.EVT_Join;
import Events.EVT_KillDeath;
import Events.EVT_Move;
import Events.EVT_PVP;
import Events.EVT_Ping;
import Events.EVT_Quit;
import Events.EVT_Respawn;
import Events.EVT_Tag;
import Events.EVT_TributeDeath;
import Events.EVT_Weather;
import Objects.Countdown;
import Objects.Countdown2;
import Objects.Countdown3;
import Objects.Countdown4;
import Objects.Game;
import Objects.GameMap;
import Objects.Podium;
import Objects.TempMap;
import Objects.Tribute;

public class SurvivalGames extends JavaPlugin{
	private static SurvivalGames instance;
	ConfigHandler cfgHandler;
	Game game;
	File rootDir;
	File playersDir;
	File mapsDir;
	File config;
	File chestsFile;
	Map<String, Tribute> players;
	boolean firstTime;
	boolean autoStart;
	List<String> voters;
	List<String> itemholders;
	Map<String, GameMap> maps;
	Map<String, TempMap> tempMaps;
	Map<String, Location> playerLocs;
	Location lobby;
	private static int votingTime;
	public static boolean timeChange;
	public static int temp;

	Scoreboard sb;
	Objective votesObjective;
	Score map1;
	Score map1score;
	Score map2;
	Score map2score;
	Score map3;
	Score map3score;
	Score map4;
	Score map4score;
	public int voteTime;

	List<String> worldNames;
	Set<String> frozen;
	List<Location> chests;
	List<ItemStack> tier1;
	List<ItemStack> tier2;
	List<ItemStack> tier3;
	List<String> alivePlayers;

	public static Connection connection;

	public Location hitt;

	public static List<String> owners = new ArrayList<String>(); //Dark Red
	public static List<String> admins = new ArrayList<String>(); //Red
	public static List<String> mods = new ArrayList<String>(); //Dark Aqua
	public static List<String> vips = new ArrayList<String>(); //Dark Purple
	public static List<String> legends = new ArrayList<String>(); //Gold
	public static List<String> masters = new ArrayList<String>(); //Gray
	public static List<String> gods = new ArrayList<String>(); //Aqua
	public static List<String> coders = new ArrayList<String>(); //Dark Blue	
	public static List<String> builders = new ArrayList<String>(); //Pink
	public static List<String> frozenn = new ArrayList<String>();


	@Override
	public void onEnable()
	{
		instance = this;
		tier1 = new ArrayList<ItemStack>();
		tier2 = new ArrayList<ItemStack>();
		tier3 = new ArrayList<ItemStack>();
		voters = new ArrayList<String>();
		maps = new HashMap<String, GameMap>();
		tempMaps = new HashMap<String, TempMap>();
		players = new HashMap<String, Tribute>();
		playerLocs = new HashMap<String, Location>();
		game = new Game();
		game.setGameTime(GameTime.LOBBY);
		frozen = new HashSet<String>();
		chests = new ArrayList<Location>();
		alivePlayers = new ArrayList<String>();
		itemholders = new ArrayList<String>();
		timeChange = false;

		init_files();
		cfgHandler = new ConfigHandler(config);
		worldNames = getConfig().getStringList("worlds");
		init_data();
		init_events();
		init_commands();
		init_chests();
		init_maps();

		File[] worlds = getServer().getWorldContainer().listFiles();
		for (File f : worlds){
			for (String w : worldNames){
				if (f.getName().equals(w))
				{
					if (Bukkit.getWorld(w) == null)
						try
					{
							Bukkit.createWorld(new WorldCreator(w));
							System.out.println("Creating world " + w + " because it is null.");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					if (GameMap.getMap(w) == null)
					{
						GameMap gm = new GameMap();
						gm.setName(w);
						gm.setLocation(Bukkit.getWorld(w).getSpawnLocation());
						gm.save();
						maps.put(w, gm);
						Bukkit.broadcastMessage("§4Created map: " + w + ". Must be configured first!");
					}
				}
			}
		}

		if (autoStart)
		{
			init_vote();
			init_votetime();
		}

	}


	@Override
	public void onDisable()
	{
		instance = null;
		try
		{
			if(connection != null && !connection.isClosed())
			{
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		for (Tribute t : players.values())
			try
		{
				File f = new File(playersDir + "/" + t.getName() + ".dat");
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(t);
				oos.close();
				System.out.println("Saved data for player: " + t.getName());
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			System.out.println("Error saving player data: " + t.getName() + "!");
		}
	}



	/*
	 * Create and load files
	 */
	private void init_files()
	{
		rootDir = new File(getDataFolder() + "");
		if (!rootDir.exists())
		{
			rootDir.mkdir();
			firstTime = true;
		}

		playersDir = new File(getDataFolder() + "/players/");
		if (!playersDir.exists())
			playersDir.mkdir();

		config = new File(getDataFolder() + "/config.yml");
		if (!config.exists())
			saveConfig();

		chestsFile = new File(getDataFolder() + "/chests.yml");
		if (!chestsFile.exists())
			try
		{
				chestsFile.createNewFile();
				FileConfiguration fc = YamlConfiguration.loadConfiguration(chestsFile);
				fc.set("tier-one", new ArrayList<Integer>());
				fc.set("tier-two", new ArrayList<Integer>());
				fc.set("tier-three", new ArrayList<Integer>());
				fc.save(chestsFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		mapsDir = new File(getDataFolder() + "/maps/");
		if (!mapsDir.exists())
			mapsDir.mkdir();
	}



	/*
	 * Start the main game countdown
	 */
	private void init_countdown()
	{

		if (Bukkit.getServer().getOnlinePlayers().length >= cfgHandler.getMinPlayers())
		{
			game.setGameTime(GameTime.PREGAME);
			Podium[] podium = game.getMap().getPodiums().toArray(new Podium[game.getMap().getPodiums().size() - 1]);
			for (int i = 0; i < podium.length; i++)
				for (Player p : Bukkit.getServer().getOnlinePlayers())
				{
					if (!frozen.contains(p.getName()))
						frozen.add(p.getName());
					if (!podium[i].isOccupied())
					{
						p.teleport(podium[i].getLocation());
						podium[i].setOccupied(true);
						playerLocs.put(p.getName(), podium[i].getLocation());
						p.getWorld().setTime(0);
					}
					else
						continue;
				}
			Countdown c = new Countdown();
			c.startCountdown(getConfig().getInt("countdown-time"));
		}
		else
		{
			voters.clear();
			init_vote();
			Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§5Not enough players to start game!");
			init_votetime();

		}
	}

	public void init_gamecoutdown()
	{
		if(game.getTime().equals(GameTime.GAME))
		{
			Countdown3 cd = new Countdown3();
			cd.startCountdown(getConfig().getInt("game-time"));
			if(cd.getTime() == getConfig().getInt("deathmacth-start-time"))
			{
				Countdown2 cd2 = new Countdown2();
				cd2.startCountdown(60);
				if(cd2.getTime() == 0)
				{
					game.init_deathmatch();
				}
			}
			if(cd.getTime() == 0)
			{
				Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " + ChatColor.DARK_RED + "" + ChatColor.BOLD + "The Timer Is Up! The Game Is Over!!!");
				game.end();
			}
		}
	}

	public void init_votetime()
	{
		if(game.getTime().equals(GameTime.LOBBY))
		{
			Countdown4 cd = new Countdown4();
			cd.startCountdown(voteTime);
			if(timeChange)
			{
				Countdown4.cancelCountdown(voteTime);
				Countdown4 cd4 = new Countdown4();
				cd4.startCountdown(temp);
				timeChange = false;
			}
			if(getConfig().getInt("minimum-players") < Bukkit.getOnlinePlayers().length && cd.getTime() == 0)
			{
				cd.startCountdown(voteTime);
			}
			votingTime = cd.getTime();
		}
	}


	/*
	 * Register events
	 */
	private void init_events()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EVT_PVP(), this);
		pm.registerEvents(new EVT_Join(), this);
		pm.registerEvents(new EVT_Block(), this);
		pm.registerEvents(new EVT_Move(), this);
		pm.registerEvents(new EVT_Interact(), this);
		pm.registerEvents(new EVT_KillDeath(), this);
		pm.registerEvents(new EVT_Damage(), this);
		pm.registerEvents(new EVT_Respawn(), this);
		pm.registerEvents(new EVT_ItemDrop(), this);
		pm.registerEvents(new EVT_ItemPickup(), this);
		pm.registerEvents(new EVT_TributeDeath(), this);
		pm.registerEvents(new EVT_Ping(), this);
		pm.registerEvents(new EVT_Quit(), this);
		pm.registerEvents(new EVT_Chat(), this);
		pm.registerEvents(new EVT_Hax(), this);
		pm.registerEvents(new EVT_Hunger(), this);
		pm.registerEvents(new EVT_Tag(), this);
		pm.registerEvents(new EVT_Weather(), this);
		pm.registerEvents(new EVT_Explode(), this);
		pm.registerEvents(new EVT_Click(), this);
		pm.registerEvents(new EVT_Consume(), this);
		pm.registerEvents(new EVT_Ignite(), this);
	}



	/*
	 * Load and grab data
	 */
	private void init_data()
	{
		File[] playerFiles = playersDir.listFiles();
		for (int i = 0; i < playerFiles.length; i++)
		{
			File f = playerFiles[i];
			try
			{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				Tribute t = (Tribute) ois.readObject();
				players.put(t.getName(), t);
				ois.close();
				System.out.println("Loaded player data for " + t.getName());
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		File[] mapFiles = mapsDir.listFiles();
		for (int i = 0; i < mapFiles.length; i++)
		{
			File f = mapFiles[i];
			try
			{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				GameMap gm = (GameMap) ois.readObject();
				maps.put(gm.getName(), gm);

				ois.close();
				System.out.println("Map " + gm.getName() + " is ready to be played!");
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}



	/*
	 * Starts the map voting
	 */
	private void init_vote()
	{
		sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		votesObjective = sb.registerNewObjective("map_votes", "dummy");
		votesObjective.setDisplayName("     §aNext Map:     ");
		votesObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

		Set<String> temp = new HashSet<String>();
		for (String key : maps.keySet())
			temp.add(key);

		final Map<Score, String> voteMaps = new HashMap<Score, String>();

		final String m_1 = getRandomMap(temp);
		temp.remove(m_1);
		final String m_2 = getRandomMap(temp);
		temp.remove(m_2);
		final String m_3 = getRandomMap(temp);
		temp.remove(m_3);
		final String m_4 = getRandomMap(temp);
		temp.remove(m_4);

		//Score blank1 = votesObjective.getScore(Bukkit.getOfflinePlayer(" "));
		Score timeSec = votesObjective.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "" + ChatColor.BOLD + "TIME LEFT:"));
		timeSec.setScore(votingTime);
		map1 = votesObjective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + m_1));
		//map1score = votesObjective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + String.valueOf(0)));
		map1.setScore(0);
		voteMaps.put(map1, m_1);
		map2 = votesObjective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + m_2));
		voteMaps.put(map2, m_2);
		map2.setScore(0);
		map3 = votesObjective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + m_3));
		voteMaps.put(map3, m_3);
		map3.setScore(0);
		map4 = votesObjective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + m_4));
		voteMaps.put(map4, m_4);
		map4.setScore(0);

		for (Player p : getServer().getOnlinePlayers())
			p.setScoreboard(sb);

		final int task1 = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{

			@Override
			public void run()
			{
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§31) §d" + m_1);
				Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§32) §d" + m_2);
				Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§33) §d" + m_3);
				Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§34) §d" + m_4);
				Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§e§lVote for the next map with /vote <id> !");
				Bukkit.broadcastMessage("");
			}
		}, 0L, 20L * 15);

		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{

			@Override
			public void run()
			{
				getServer().getScheduler().cancelTask(task1);
				for (Player p : getServer().getOnlinePlayers())
				{
					voters.add(p.getName());
					p.setScoreboard(getServer().getScoreboardManager().getNewScoreboard());
				}
				Integer[] i = new Integer[] { map1.getScore(), map2.getScore(), map3.getScore(), map4.getScore() };

				List<Score> scores = new ArrayList<Score>();
				scores.addAll(Arrays.asList(map1, map2, map3, map4));
				for (Score m : scores)
					if (findLargest(i) == m.getScore())
					{
						String s = voteMaps.get(m);
						Bukkit.broadcastMessage("§cVoting is over! Next map is... §e" + voteMaps.get(m));
						if (tempMaps.containsKey(s))
						{
							Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§3Map: §e" + tempMaps.get(s).getName());
							Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§3Description: §e" + tempMaps.get(s).getDescription());
							Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§3Author(s): §e" + tempMaps.get(s).getAuthors());
							Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.ITALIC + "Survival Games" + ChatColor.GOLD + "] " +  "§3Link: §e" + tempMaps.get(s).getDownload());
						}
						game.setMap(s);
						Bukkit.getWorld(s).setAutoSave(false);
						Bukkit.getWorld(s).setTime(0);
						init_countdown();
						break;
					}
			}
		}, 20L * voteTime);

	}



	/*
	 * Register commands
	 */
	private void init_commands()
	{
		SimpleCommand.registerCommands(this, new CMD_AddSpawn());
		SimpleCommand.registerCommands(this, new CMD_DeleteSpawn());
		SimpleCommand.registerCommands(this, new CMD_ForceStart());
		SimpleCommand.registerCommands(this, new CMD_Vote());
		SimpleCommand.registerCommands(this, new CMD_SetLobby());
		SimpleCommand.registerCommands(this, new CMD_Points());
		SimpleCommand.registerCommands(this, new CMD_Gamble());
		SimpleCommand.registerCommands(this, new CMD_DeathMatch());
		SimpleCommand.registerCommands(this, new CMD_Spectate());
		SimpleCommand.registerCommands(this, new CMD_Hub());
		SimpleCommand.registerCommands(this, new CMD_V());
		SimpleCommand.registerCommands(this, new CMD_Item());
		SimpleCommand.registerCommands(this, new CMD_Delay());
	}



	@SuppressWarnings("deprecation")
	private void init_chests()
	{
		FileConfiguration fc = YamlConfiguration.loadConfiguration(chestsFile);

		List<Integer> ids1 = fc.getIntegerList("tier-one");
		for (int i : ids1)
		try
		{
				ItemStack is;
				if(Material.getMaterial(i) == Material.ARROW)
					is = new ItemStack(Material.ARROW, 4);
				else if(Material.getMaterial(i) == Material.INK_SACK)
					is = new ItemStack(351, 1, (short) 3);
				else
					is = new ItemStack(Material.getMaterial(i), 1);
				tier1.add(is);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		List<Integer> ids2 = fc.getIntegerList("tier-two");
		for (int i : ids2)
		try
		{
				ItemStack is = new ItemStack(Material.getMaterial(i), 1);
				tier2.add(is);
		}
		catch (Exception e)
		{

		}

		List<Integer> ids3 = fc.getIntegerList("tier-three");
		for (int i : ids3)
		try
		{
				ItemStack is = new ItemStack(Material.getMaterial(i), 1);
				tier3.add(is);
		}
		catch (Exception e)
		{

		}
	}





	private void init_maps()
	{
		if (getConfig() == null)
			saveDefaultConfig();

		ConfigurationSection cs = getConfig().getConfigurationSection("maps");
		for (String key : cs.getKeys(false))
		{
			String name = getConfig().getString("maps." + key + ".name");
			String description = getConfig().getString("maps." + key + ".description");
			String authors = getConfig().getString("maps." + key + ".authors");
			String download = getConfig().getString("maps." + key + ".download");
			tempMaps.put(key, new TempMap(name, description, authors, download));
		}

	}


	public static Connect getBukkitConnect()
	{
		return (Connect) Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	}

	public static void redirectRequest(String server, final Player player)
	{
		try 
		{
			//create connection
			Connect c = getBukkitConnect();
			//new RedirectRequest to transfer the player
			c.request(new RedirectRequest(server, player.getName())).registerListener(new FutureResultListener<RedirectResult>() 
			{
				//listen for a successful transfer
				public void onResult(RedirectResult redirectResult)
				{
					if (redirectResult.getStatusCode() == StatusCode.SUCCESS)
					{
						return;
					}
					player.sendMessage(ChatColor.AQUA + "[TheParkMC]" + ChatColor.DARK_RED + " Could not connect");
				}
			});

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public synchronized static void openConnection()
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "username", "password");
		}
		catch(Exception e)
		{
			
		}
	}

	public synchronized static void closeConnection()
	{
		try
		{
			connection.close();
		}
		catch(Exception e)
		{
			
		}
	}

	public synchronized static boolean playerOwnersContainsPlayer(Player player)
	{
		try
		{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `player_rank` WHERE player=?;");
			sql.setString(1, player.getName());
			ResultSet resultSet = sql.executeQuery();
			boolean containsPlayer = resultSet.next();

			sql.close();
			resultSet.close();

			return containsPlayer;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static void playerGiveRanks(Player player)
	{
		openConnection();
		try
		{
			String rank = "";
			if(playerOwnersContainsPlayer(player))
			{
				PreparedStatement sql = connection.prepareStatement("SELECT rank FROM `player_rank` WHERE player=?;");
				sql.setString(1, player.getName());

				ResultSet result = sql.executeQuery();
				result.next();

				rank = result.getString("rank");

				if(rank.equalsIgnoreCase("owner"))
				{
					owners.add(player.getName());
				}

				if(rank.equalsIgnoreCase("vip"))
				{
					vips.add(player.getName());
				}

				if(rank.equalsIgnoreCase("mod"))
				{
					mods.add(player.getName());
				}

				if(rank.equalsIgnoreCase("master"))
				{
					masters.add(player.getName());
				}

				if(rank.equalsIgnoreCase("admin"))
				{
					admins.add(player.getName());
				}

				if(rank.equalsIgnoreCase("legend"))
				{
					legends.add(player.getName());
				}
				if(rank.equalsIgnoreCase("builder"))
				{
					builders.add(player.getName());
				}

				if(rank.equalsIgnoreCase("coder"))
				{
					coders.add(player.getName());
				}

				if(rank.equalsIgnoreCase("god"))
				{
					gods.add(player.getName());
				}

				sql.close();
				result.close();
			} 
			else 
			{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT INTO `player_rank` values(?,?);");
				newPlayer.setString(1, player.getName());
				if(owners.contains(player.getName()))
				{
					newPlayer.setString(2, "owner");
				} 
				else if(vips.contains(player.getName()))
				{
					newPlayer.setString(2, "vip");
				} 
				else if(mods.contains(player.getName()))
				{
					newPlayer.setString(2, "mod");
				}
				else if(masters.contains(player.getName()))
				{
					newPlayer.setString(2, "master");
				} 
				else if(admins.contains(player.getName()))
				{
					newPlayer.setString(2, "admin");
				}
				else if(legends.contains(player.getName()))
				{
					newPlayer.setString(2, "legend");
				} 
				else if(builders.contains(player.getName()))
				{
					newPlayer.setString(2, "builder");
				} 
				else if(coders.contains(player.getName()))
				{
					newPlayer.setString(2, "coder");
				} 
				else if(gods.contains(player.getName()))
				{
					newPlayer.setString(2, "god");
				}
				newPlayer.execute();
				newPlayer.close();
			}
		} 
		catch(Exception e)
		{
			
		} 
		finally
		{
			closeConnection();
		}
	}

	public synchronized static boolean playerDataContainsPlayer(Player player)
	{
		try
		{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `player_sg_data` WHERE player=?;");
			sql.setString(1, player.getName());
			ResultSet resultSet = sql.executeQuery();
			boolean containsPlayer = resultSet.next();

			sql.close();
			resultSet.close();

			return containsPlayer;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static void playerLogin(Player player)
	{
		openConnection();
		try
		{
			int previousKills = 0;

			if(playerDataContainsPlayer(player))
			{
				PreparedStatement sql = connection.prepareStatement("SELECT logins FROM `player_sg_data` WHERE player=?;");
				sql.setString(1, player.getName());

				ResultSet result = sql.executeQuery();
				result.next();

				previousKills = result.getInt("logins");

				PreparedStatement loginsUpdate = connection.prepareStatement("UPDATE `player_sg_data` SET logins=? WHERE player=?;");
				loginsUpdate.setInt(1, previousKills + 1);
				loginsUpdate.setString(2, player.getName());
				loginsUpdate.executeUpdate();

				loginsUpdate.close();
				sql.close();
				result.close();
			} 
			else 
			{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT INTO `player_data` values(?,0,0,100,0,0,0,1);");
				newPlayer.setString(1, player.getName());
				newPlayer.execute();
				newPlayer.close();
			}
		} 
		catch(Exception e)
		{
			
		} 
		finally 
		{
			closeConnection();
		}
	}


	public void setAutoStart(boolean boolean1)
	{
		this.autoStart = boolean1;
	}


	public void setLobby(Location loc)
	{
		this.lobby = loc;
	}


	public static SurvivalGames getInstance()
	{
		return instance;
	}



	public ConfigHandler getConfigHandler()
	{
		return cfgHandler;
	}



	public Map<String, Tribute> getPlayers()
	{
		return players;
	}



	public Game getGame()
	{
		return game;
	}



	public List<String> getVoted()
	{
		return voters;
	}



	public Location getLobby()
	{
		return lobby;
	}


	public List<String> getAlivePlayers()
	{
		return alivePlayers;
	}

	public List<String> getItemHolders()
	{
		return itemholders;
	}

	public Map<String, GameMap> getMaps()
	{
		return maps;
	}

	public Map<String, Location> getPlayerLocations()
	{
		return playerLocs;
	}



	public String getRandomMap(Set<String> maps)
	{
		String[] a = maps.toArray(new String[maps.size() - 1]);
		int select = new Random().nextInt((a.length));
		return a[select];
	}

	public void setVotingTime(int a)
	{
		temp = a;
	}



	public Score getMap(int number)
	{
		switch (number)
		{
		case 1:
			return map1;
		case 2:
			return map2;
		case 3:
			return map3;
		case 4:
			return map4;
		}
		return null;
	}



	public static int findLargest(Integer[] numbers)
	{
		int largest = numbers[0];
		for (int i = 1; i < numbers.length; i++)
			if (numbers[i] > largest)
				largest = numbers[i];
		return largest;
	}

	public int getVotingTime()
	{
		return votingTime;
	}



	public Scoreboard getScoreboard()
	{
		return sb;
	}



	public Set<String> getFrozen()
	{
		return frozen;
	}



	public List<Location> getChests()
	{
		return chests;
	}



	public List<ItemStack> getTierOne()
	{
		return tier1;
	}



	public List<ItemStack> getTierTwo()
	{
		return tier2;
	}



	public List<ItemStack> getTierThree()
	{
		return tier3;
	}
}
