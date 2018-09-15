package tk.porthydra.autofarm.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import tk.porthydra.autofarm.AutoFarm;
import tk.porthydra.autofarm.config.Config;

public class CropListener implements Listener {
	
	private HashMap<Location, Material> locs = new HashMap<Location, Material>();
	
	private AutoFarm plugin;
	private Material[] crops;
	private Material[] seeds;
	private Player pl;
	
	public CropListener(AutoFarm plugin) {
		this.plugin = plugin;
		this.crops = plugin.getCrops();
		this.seeds = plugin.getSeeds();
	}
	
	@EventHandler
	public void onCropBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		Config c = plugin.getC();
		
		for (int n = 0; n < seeds.length; n++) 
		{
			if (c.isAutoRePlant()) 
			{
				if (plugin.getToggles().get(p)) 
				{
					if (p.hasPermission("autofarm.crop." + plugin.getModes()[n])) 
					{
						if (!c.isItemEnabled() || p.getItemInHand().getType() == c.autoItem()) 
						{
							if (b.getType() == crops[n]) 
							{ 
								Block down = b.getRelative(BlockFace.DOWN); 
								if (down.getType() == Material.SOIL && plugin.getCrops()[n] != Material.NETHER_WARTS) 
								{ // Checking if the block below is soil
									locs.put(b.getLocation(), seeds[n]); // Adding values to the hashmap
									pl = p;
									if (c.isItemEnabled()) p.getItemInHand().setDurability(
											(short) (p.getItemInHand().getDurability() - c.durabilityLoss()));
								}
								else if (down.getType() == Material.SOUL_SAND && plugin.getCrops()[n] == Material.NETHER_WARTS) 
								{
									locs.put(b.getLocation(), seeds[n]); // Adding values to the hashmap
									pl = p;
									if (c.isItemEnabled()) p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() - c.durabilityLoss()));
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onCropDrop(ItemSpawnEvent e) {
		Location l = null;
		
		for (Entry<Location, Material> entry : locs.entrySet()) {
			
			Location spawn = e.getLocation();
			spawn.setYaw(0);
			spawn.setPitch(0);
			spawn.setX(Location.locToBlock(spawn.getX()));
			spawn.setZ(Location.locToBlock(spawn.getZ()));
			spawn.setY(Location.locToBlock(spawn.getY())); // Normalising the location
			
			Location map = entry.getKey();
			map.setPitch(0);
			map.setYaw(0); // Normalising the location
			
			if (spawn.equals(map)) { // Checking the locations are the same
				
				ItemStack i = e.getEntity().getItemStack();
				if (i.getType() == entry.getValue()) { // Checking that the seed dropped is what it's supposed to be
					
					l = entry.getKey(); // To prevent CME
					
					Block b = e.getLocation().getBlock();
					Config c = plugin.getC();
					
					for (int n = 0; n < seeds.length; n++) {
						if (entry.getValue() == seeds[n]) {
							b.setType(crops[n]); // Replanting the seed
							List<String> messages = c.getReplantMessage();
							if (messages != null) {
								for (String s : messages) pl.sendMessage(s);
							}
							pl = null;
						}
					}
					
					e.setCancelled(true);
				}
			}
		}
		
		if (l != null) locs.remove(l); // To prevent CME
	}
}
