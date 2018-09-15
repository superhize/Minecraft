package tk.porthydra.autofarm;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tk.porthydra.autofarm.command.CmdAutoFarm;
import tk.porthydra.autofarm.config.Config;
import tk.porthydra.autofarm.listener.CropListener;
import tk.porthydra.autofarm.listener.HoeListener;
import tk.porthydra.autofarm.listener.PlayerLogin;
import tk.porthydra.autofarm.listener.PlayerLogout;

public class AutoFarm extends JavaPlugin {
	
	private HashMap<Player, Boolean> toggles = new HashMap<Player, Boolean>();
	private HashMap<Player, Material> mode = new HashMap<Player, Material>();
	private Config c = new Config(this); 
	
	
	private Material[] seeds = {Material.SEEDS, Material.CARROT_ITEM, Material.POTATO_ITEM, Material.MELON_SEEDS, 
			Material.PUMPKIN_SEEDS, Material.NETHER_STALK, Material.BEETROOT_SEEDS};

	private Material[] crops = {Material.CROPS, Material.CARROT, Material.POTATO, Material.MELON_STEM,
			Material.PUMPKIN_STEM, Material.NETHER_WARTS, Material.BEETROOT_BLOCK};
	
	private String[] modes = {"wheat", "carrot", "potato", "melon", "pumpkin", "netherwart", "beetroot"};
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		reloadConfig();
		
		getServer().getPluginManager().registerEvents(new CropListener(this), this);
		getServer().getPluginManager().registerEvents(new HoeListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerLogin(this), this);
		getServer().getPluginManager().registerEvents(new PlayerLogout(this), this);
		
		getCommand("AutoFarm").setExecutor(new CmdAutoFarm(this));
		
		if (c.defaultMode() == null) {
			for (int i = 0; i < 3; i ++) {
				getLogger().severe("Auto-plant default mode improperly configured!");
			}
		}
		
		if (c.autoItem() == null) {
			for (int i = 0; i < 3; i++) {
				getLogger().severe("Auto-replant item material improperly configured!");
			}
		}
		
		if (c.getAutoPlantMessage() == null) getLogger().warning("Auto-plant message not configured!");
		if (c.getReplantMessage() == null) getLogger().warning("Auto-replant message not configured!");
	}
	
	@Override
	public void onDisable() {
		reloadConfig();
	}

	public HashMap<Player, Boolean> getToggles() {
		return toggles;
	}

	public void setToggles(HashMap<Player, Boolean> toggles) {
		this.toggles = toggles;
	}

	public HashMap<Player, Material> getMode() {
		return mode;
	}

	public void setMode(HashMap<Player, Material> mode) {
		this.mode = mode;
	}

	public Config getC() {
		return c;
	}

	public void setC(Config c) {
		this.c = c;
	}

	public Material[] getSeeds() {
		return seeds;
	}

	public Material[] getCrops() {
		return crops;
	}
	
	public String[] getModes() {
		return modes;
	}
}
