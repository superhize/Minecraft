package tk.porthydra.autofarm.listener;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import tk.porthydra.autofarm.AutoFarm;
import tk.porthydra.autofarm.config.Config;

public class HoeListener implements Listener {
	
	private AutoFarm plugin;
	
	public HoeListener(AutoFarm plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onTillGround(PlayerInteractEvent e) {
		
		Config c = plugin.getC();
		if (c.isAutoPlant()) {
			
			Player p = e.getPlayer();
			Block b = e.getClickedBlock();
			
			if (plugin.getToggles().get(e.getPlayer())) { 
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (e.getItem() != null) {
						if (isHoe(e.getItem().getType())) {
							if (b != null) {
								if (b.getType() == Material.DIRT || b.getType() == Material.GRASS) {
									if (b.getRelative(BlockFace.UP).isEmpty()) {
										
										Material crop = plugin.getMode().get(p); // Getting player mode
										
										int index = 0;
										for (int i = 0; i < plugin.getCrops().length; i++) {
											if (plugin.getCrops()[i] == crop) index = i; // Getting the index in the array
										}
										
										Material seed = plugin.getSeeds()[index]; // Getting the corrsponding seed
										
										if (p.hasPermission("autofarm.crop." + plugin.getModes()[index]) && seed != Material.NETHER_STALK) { // Checking perms
											if (p.getInventory().contains(seed)) {
												
												ItemStack it = null;
												int in = 0;
												ItemStack[] contents = p.getInventory().getContents();
												
												for (int i = 0; i < contents.length; i++) {
													if (contents[i] != null) {
														if (contents[i].getType() == seed) {
															it = contents[i].clone(); // Getting a copy of seed stack
															in = i; // Getting index of said stack in inventory
														}
													}
												}
												
												if (it != null) {
												
													if (it.getAmount() != 1) p.getInventory().setItem(in, new ItemStack(it.getType(), (it.getAmount() - 1)));
													// Removing the seeds from the inventory
													else p.getInventory().setItem(in, null);
													// Removing the seeds from the inventory
													p.updateInventory();
													
													List<String> messages = c.getAutoPlantMessage();
													if (messages != null) {
														for (String s : messages) p.sendMessage(s);
													}
													// Sending the message
													
													Block up = b.getRelative(BlockFace.UP);
													up.setType(plugin.getMode().get(p));
													b.setType(Material.SOIL);
													// Changing blocks.
												}
												else p.sendMessage(ChatColor.DARK_RED + "You don't have the correct seed!");
											}
											else p.sendMessage(ChatColor.DARK_RED + "You don't have the correct seed!");
										}
									}
									else p.sendMessage(ChatColor.DARK_RED + "No space!");
								}
							}
						}
					}
				}
			}
		}
	}
	
	private boolean isHoe(Material m) {
		if (m == Material.WOOD_HOE) return true;
		if (m == Material.STONE_HOE) return true;
		if (m == Material.GOLD_HOE) return true;
		if (m == Material.IRON_HOE) return true;
		if (m == Material.DIAMOND_HOE) return true;
		return false;
	}
}
