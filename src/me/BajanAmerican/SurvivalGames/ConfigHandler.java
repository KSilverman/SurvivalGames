package me.BajanAmerican.SurvivalGames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler {

	File file;
	FileConfiguration cfg;

	boolean chestRegen;
	int chestRegenTime;

	int timeBeforeShutdown;
	int gracePeriod;
	int gameTime;
	int deathmatchmatchtime;

	String graceMSG;
	String graceEndedMSG;
	String staffMSG;

	List<Material> breakables;

	List<Material> placeables;

	int minimumPlayers;

	int radius;



	@SuppressWarnings("deprecation")
	public ConfigHandler(File f)
	{
		this.file = f;
		this.cfg = YamlConfiguration.loadConfiguration(f);

		gracePeriod = cfg.getInt("grace-period");
		chestRegen = cfg.getBoolean("chest-regen");
		timeBeforeShutdown = cfg.getInt("shutdown-time");
		gameTime = cfg.getInt("game-time");
		deathmatchmatchtime = cfg.getInt("deathmacth-start-time");
		radius = cfg.getInt("deathmatch-radius");
		graceMSG = cfg.getString("grace-period-message").replace("&", "§");
		graceEndedMSG = cfg.getString("grace-period-ended-message").replace("&", "§");
		staffMSG = cfg.getString("staff-join-message").replace("&", "§");
		minimumPlayers = cfg.getInt("minimum-players");
		breakables = new ArrayList<Material>();
		for (int id : cfg.getIntegerList("breakable-blocks"))
		{
			breakables.add(Material.getMaterial(id));
			System.out.println("Added: " + Material.getMaterial(id) + " to the breakable blocks list!");
		}

		placeables = new ArrayList<Material>();
		for (int id : cfg.getIntegerList("placeable-blocks"))
		{
			placeables.add(Material.getMaterial(id));
			System.out.println("Added: " + Material.getMaterial(id) + " to the placeable blocks list!");
		}

		SurvivalGames.getInstance().setAutoStart(cfg.getBoolean("auto-start"));

		World world = Bukkit.getServer().getWorld(cfg.getString("lobby.world"));
		double x = cfg.getDouble("lobby.x");
		double y = cfg.getDouble("lobby.y");
		double z = cfg.getDouble("lobby.z");
		float pitch = Float.parseFloat(cfg.getString("lobby.pitch"));
		float yaw = Float.parseFloat(cfg.getString("lobby.yaw"));
		Location loc = new Location(world, x, y, z);
		loc.setPitch(pitch);
		loc.setYaw(yaw);
		SurvivalGames.getInstance().setLobby(loc);

		SurvivalGames.getInstance().voteTime = cfg.getInt("vote-time");
	}



	public boolean hasChestRegen()
	{
		return chestRegen;
	}



	public int getChestRegenTime()
	{
		return chestRegenTime;
	}



	public int getTimeBeforeShutdown()
	{
		return timeBeforeShutdown;
	}


	public int getGameTime()
	{
		return gameTime;
	}


	public int getDeathMatchMatchTime()
	{
		return deathmatchmatchtime;
	}


	public int getGraceTime()
	{
		return gracePeriod;
	}



	public String getGraceMessage()
	{
		return graceMSG;
	}



	public String getGraceEndedMessage()
	{
		return graceEndedMSG;
	}



	public String getStaffMessage()
	{
		return staffMSG;
	}



	public List<Material> getBreakables()
	{
		return breakables;
	}


	public List<Material> getPlaceables()
	{
		return placeables;
	}



	public int getMinPlayers()
	{
		return minimumPlayers;
	}

	public int getRadius()
	{
		return radius;
	}

}
