package Events;

import java.util.Random;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import Enums.GameTime;
import Objects.Tribute;

public class EVT_Interact implements Listener
{


	@EventHandler
	public void onClick(PlayerInteractEvent e)
	{
		Tribute trib = Tribute.getTribute(e.getPlayer());

		if (trib.isSpectator())
		{
			if(e.getPlayer().getItemInHand().getType() == Material.MAP)
			{
				if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					Inventory inv = Bukkit.createInventory(e.getPlayer(), GetSize(), ChatColor.BOLD + "Spectator Teleporter");
					for(String pp : SurvivalGames.getInstance().getAlivePlayers())
					{
						@SuppressWarnings("deprecation")
						ItemStack skull = new ItemStack(397, 1, (short) 3);
						SkullMeta meta = (SkullMeta) skull.getItemMeta();
						meta.setDisplayName(pp);
						skull.setItemMeta(meta);
						inv.addItem(skull);
					}
					e.getPlayer().openInventory(inv);
				}
			}
			e.setCancelled(true);
		}

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if (!trib.isSpectator())
			{
				if (e.getClickedBlock().getState() instanceof Chest)
					doChest(e.getClickedBlock());             	
			}
		}

		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(e.getPlayer().getItemInHand().getType() == Material.FLINT_AND_STEEL)
			{

				if(e.getClickedBlock().getType() == Material.TNT)
				{
					e.setCancelled(true);
				}

				e.getPlayer().getItemInHand().setDurability((short) (e.getPlayer().getItemInHand().getDurability() - 22));
			} 
		}

		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.LOBBY || SurvivalGames.getInstance().getGame().getTime() == GameTime.PREGAME)
		{
			if(e.getPlayer().getItemInHand().getType() == Material.COMPASS)
			{
				if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					SurvivalGames.redirectRequest("hub", e.getPlayer());
				}
			}
		}

		if(SurvivalGames.getInstance().getGame().getTime() == GameTime.GAME)
		{
			if(e.getAction() == Action.PHYSICAL)
			{       		 
				if(e.getClickedBlock().getType() == Material.STONE_PLATE)
				{

					if(Tribute.getTribute(e.getPlayer()).isSpectator())
					{
						e.setCancelled(true);
						return;
					}

					Location l = e.getPlayer().getLocation();
					World world = e.getPlayer().getLocation().getWorld();

					double i = l.getX();
					double j = l.getY();

					double k = l.getZ();

					int r = 5;
					int rsquared = r*r;
					for(int x = -r; x < r; x++)
					{
						for(int z = -r; z < r; z++)
						{
							if(x*x + z*z > rsquared)
								continue;
							Location loc = new Location(world, i+x, j + 10, k+z);
							TNTPrimed tnt = (TNTPrimed) world.spawnEntity(loc, EntityType.PRIMED_TNT);
							tnt.setFuseTicks(30);					  
						}
					}

				}
			}
		}
	}



	public void doChest(Block b)
	{
		if (b.getState() instanceof Chest)
		{
			Chest chest = (Chest) b.getState();

			if (SurvivalGames.getInstance().getChests().contains(b.getLocation()))
				return;

			SurvivalGames.getInstance().getChests().add(b.getLocation());

			Inventory inv = chest.getInventory();

			if(inv.contains(Material.GOLD_BLOCK)){
				fillChest(chest, 2);
			} else if(inv.contains(Material.DIAMOND_BLOCK)){
				fillChest(chest, 2);
			} else {            
				//int chance = new Random().nextInt(50) + 1;
				//if (chance <= 10)
				//fillChest(chest, 3);
				//else if (chance <= 15)
				//fillChest(chest, 2);
				//else if (chance <= 75)
				fillChest(chest, 1);
			}
		}


		if(b.getState() instanceof DoubleChest)
		{
			DoubleChest dc = (DoubleChest) b.getState();

			if (SurvivalGames.getInstance().getChests().contains(b.getLocation()))
				return;

			SurvivalGames.getInstance().getChests().add(b.getLocation());

			Inventory inv = dc.getInventory();

			if(inv.contains(Material.GOLD_BLOCK)){
				fillDoubleChest(dc, 2);
			} else if(inv.contains(Material.DIAMOND_BLOCK)){
				fillDoubleChest(dc, 2);
			} else {            
				//int chance = new Random().nextInt(50) + 1;
				//if (chance <= 10)
				//fillChest(chest, 3);
				//else if (chance <= 15)
				//fillChest(chest, 2);
				//else if (chance <= 75)
				fillDoubleChest(dc, 1);
			}
		}
	}



	public void fillChest(Chest chest, int tier)
	{
		chest.getInventory().clear();
		int amtItems = new Random().nextInt(5) + 3;
		int amtItem = new Random().nextInt(4) + 2;
		if(amtItems > 5)
			amtItems = 5;
		if(amtItem > 4)
			amtItem = 4;
		if (tier == 1)
			for (int i = 0; i < amtItems; i++)
			{
				int slot = new Random().nextInt(chest.getInventory().getSize());
				ItemStack[] items = SurvivalGames.getInstance().getTierOne().toArray(new ItemStack[SurvivalGames.getInstance().getTierOne().size() - 1]);
				int select = new Random().nextInt(items.length);
				chest.getInventory().setItem(slot, items[select]);
			}
		else if (tier == 2)
			for (int i = 0; i < amtItem; i++)
			{
				int slot = new Random().nextInt(chest.getInventory().getSize());
				ItemStack[] items = SurvivalGames.getInstance().getTierTwo().toArray(new ItemStack[SurvivalGames.getInstance().getTierTwo().size() - 1]);
				int select = new Random().nextInt(items.length);
				chest.getInventory().setItem(slot, items[select]);
			}
		else if (tier == 3)
			for (int i = 0; i < amtItems; i++)
			{
				int slot = new Random().nextInt(chest.getInventory().getSize());
				ItemStack[] items = SurvivalGames.getInstance().getTierThree().toArray(new ItemStack[SurvivalGames.getInstance().getTierThree().size() - 1]);
				int select = new Random().nextInt(items.length);
				chest.getInventory().setItem(slot, items[select]);
			}
	}

	public void fillDoubleChest(DoubleChest chest, int tier)
	{
		chest.getInventory().clear();
		int amtItems = new Random().nextInt(10) + 3;
		int amtItem = new Random().nextInt(8) + 4;
		if(amtItems > 10)
			amtItems = 10;
		if(amtItem > 8)
			amtItem = 8;
		if (tier == 1)
			for (int i = 0; i < amtItems; i++)
			{
				int slot = new Random().nextInt(chest.getInventory().getSize());
				ItemStack[] items = SurvivalGames.getInstance().getTierOne().toArray(new ItemStack[SurvivalGames.getInstance().getTierOne().size() - 1]);
				int select = new Random().nextInt(items.length);
				chest.getInventory().setItem(slot, items[select]);
			}
		else if (tier == 2)
			for (int i = 0; i < amtItem; i++)
			{
				int slot = new Random().nextInt(chest.getInventory().getSize());
				ItemStack[] items = SurvivalGames.getInstance().getTierTwo().toArray(new ItemStack[SurvivalGames.getInstance().getTierTwo().size() - 1]);
				int select = new Random().nextInt(items.length);
				chest.getInventory().setItem(slot, items[select]);
			}
		else if (tier == 3)
			for (int i = 0; i < amtItems; i++)
			{
				int slot = new Random().nextInt(chest.getInventory().getSize());
				ItemStack[] items = SurvivalGames.getInstance().getTierThree().toArray(new ItemStack[SurvivalGames.getInstance().getTierThree().size() - 1]);
				int select = new Random().nextInt(items.length);
				chest.getInventory().setItem(slot, items[select]);
			}
	}

	private static int GetSize()
	{
		int size = SurvivalGames.getInstance().getGame().getAlive();
		while(!(size % 9 == 0))
		{
			++size;
		}
		return (size > 54) ? 54 : size;
	}
}