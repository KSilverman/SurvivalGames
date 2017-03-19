package Events;

import java.util.Random;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import Enums.GameTime;
import Misc.Items;
import Objects.Tribute;

public class EVT_Join implements Listener {


	public static ItemStack compass = new ItemStack(Material.COMPASS);
	public static final ItemStack lobbyCompass = setName(compass, "Return To Hub", ChatColor.BLUE);

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		if (SurvivalGames.getInstance().getGame().getTime() != GameTime.LOBBY)
		{
			if (SurvivalGames.getInstance().getGame().getTime() == GameTime.DEATHMATCH
					|| SurvivalGames.getInstance().getGame().getTime() == GameTime.RESTARTING
					|| SurvivalGames.getInstance().getGame().getTime() == GameTime.PREGAME) 
			{
				e.setJoinMessage(null);
				SurvivalGames.redirectRequest("hub", e.getPlayer());
				if(SurvivalGames.getInstance().getGame().getTime() == GameTime.DEATHMATCH)
					e.getPlayer().sendMessage(ChatColor.AQUA + "[TheParkMC] " + ChatColor.DARK_RED + "You can't join during during deathmatch!");
				if(SurvivalGames.getInstance().getGame().getTime() == GameTime.PREGAME)
					e.getPlayer().sendMessage(ChatColor.AQUA + "[TheParkMC] " + ChatColor.DARK_RED + "You can't join during during pregame!");
				if(SurvivalGames.getInstance().getGame().getTime() == GameTime.RESTARTING)
					e.getPlayer().sendMessage(ChatColor.AQUA + "[TheParkMC] " + ChatColor.DARK_RED + "You can't join during during cleanup!");
				return;
			}

			if (!SurvivalGames.getInstance().getPlayers().containsKey(e.getPlayer().getName())) 
			{
				Tribute t = new Tribute();
				t.setName(e.getPlayer().getName());
				t.setKills(0);
				t.setDeaths(0);
				t.setPoints(100);
				t.setWins(0);
				t.setLosses(0);
				t.setKillStreak(0);
				t.save();
				SurvivalGames.getInstance().getPlayers().put(t.getName(), t);
			}

			e.setJoinMessage(null);
			SurvivalGames.playerGiveRanks(e.getPlayer());
			Tribute.getTribute(e.getPlayer()).setSpectator(true);
			e.getPlayer().setAllowFlight(true);
			e.getPlayer().getInventory().addItem(lobbyCompass);
			e.getPlayer().getInventory().addItem(Items.pMap);
			setListName(e.getPlayer(), ChatColor.BLACK + e.getPlayer().getName(), ChatColor.BLACK);
			Player[] rndP = Bukkit.getOnlinePlayers();
			Random r = new Random();
			Player p = rndP[r.nextInt(Bukkit.getOnlinePlayers().length)];
			//e.getPlayer().teleport(SurvivalGames.getInstance().getGame().getMap().getLocation().getLocation().getWorld().getSpawnLocation());
			e.getPlayer().teleport(p.getWorld().getSpawnLocation());
			for(Player ppp : Bukkit.getOnlinePlayers())
				ppp.hidePlayer(e.getPlayer());
			return;
		}

		Player p = e.getPlayer();
		SurvivalGames.playerGiveRanks(p);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setAllowFlight(false);
		p.setLevel(0);
		p.setExp(0);
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		e.getPlayer().getInventory().addItem(lobbyCompass);
		SurvivalGames.getInstance().getAlivePlayers().add(e.getPlayer().getName());
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());

		if (!SurvivalGames.getInstance().getPlayers().containsKey(e.getPlayer().getName()))
		{
			Tribute t = new Tribute();
			t.setName(e.getPlayer().getName());
			t.setKills(0);
			t.setDeaths(0);
			t.setPoints(100);
			t.setWins(0);
			t.setLosses(0);
			t.setKillStreak(0);
			t.save();
			SurvivalGames.getInstance().getPlayers().put(t.getName(), t);
		}

		if (e.getPlayer().isOp())
			e.setJoinMessage(SurvivalGames.getInstance().getConfigHandler()
					.getStaffMessage());

		e.getPlayer().teleport(SurvivalGames.getInstance().getLobby());

		if (SurvivalGames.getInstance().getScoreboard() != null)
			e.getPlayer().setScoreboard(SurvivalGames.getInstance().getScoreboard());

		if (SurvivalGames.vips.contains(p.getName())) 
		{
			setListName(p, ChatColor.DARK_PURPLE + p.getName(), ChatColor.DARK_PURPLE);
			e.setJoinMessage("\n" + ChatColor.DARK_PURPLE + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		} 
		else if (SurvivalGames.mods.contains(p.getName())) 
		{
			setListName(p, ChatColor.DARK_AQUA + p.getName(), ChatColor.DARK_AQUA);
			e.setJoinMessage("\n" + ChatColor.DARK_AQUA + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		} 
		else if (SurvivalGames.admins.contains(p.getName()))
		{
			setListName(p, ChatColor.RED + p.getName(), ChatColor.RED);
			e.setJoinMessage("\n" + ChatColor.RED + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		} 
		else if (SurvivalGames.owners.contains(p.getName())) 
		{
			setListName(p, ChatColor.DARK_RED + p.getName(), ChatColor.DARK_RED);
			e.setJoinMessage("\n" + ChatColor.DARK_RED + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		} 
		else if (SurvivalGames.builders.contains(p.getName())) 
		{
			setListName(p, ChatColor.LIGHT_PURPLE + p.getName(),ChatColor.LIGHT_PURPLE);
			e.setJoinMessage("\n" + ChatColor.LIGHT_PURPLE + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		} 
		else if (SurvivalGames.coders.contains(p.getName())) 
		{
			setListName(p, ChatColor.RED + p.getName(), ChatColor.RED);
			e.setJoinMessage("\n" + ChatColor.RED + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		} 
		else if (SurvivalGames.masters.contains(p.getName())) 
		{
			setListName(p, ChatColor.GRAY + p.getName(), ChatColor.GRAY);
			e.setJoinMessage("\n" + ChatColor.GRAY + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		} 
		else if (SurvivalGames.legends.contains(p.getName())) 
		{
			setListName(p, ChatColor.GOLD + p.getName(), ChatColor.GOLD);
			e.setJoinMessage("\n" + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		} 
		else if (SurvivalGames.gods.contains(p.getName())) 
		{
			setListName(p, ChatColor.AQUA + p.getName(), ChatColor.AQUA);
			e.setJoinMessage("\n" + ChatColor.AQUA + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		} 
		else
		{
			setListName(p, ChatColor.GREEN + p.getName(), ChatColor.GREEN);
			e.setJoinMessage("\n" + ChatColor.GREEN + e.getPlayer().getName() + ChatColor.AQUA + " Has Joined Survival Games!");
		}
	}

	public static void setListName(Player player, String name, ChatColor C) 
	{
		String s = name;
		if (s.length() > 16)
			s = s.substring(0, 16);
		player.setPlayerListName(s);
	}

	private static ItemStack setName(ItemStack is, String name, ChatColor colour) 
	{
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(colour + name);
		is.setItemMeta(im);
		return is;
	}
}
