package tk.porthydra.autofarm.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.ChatColor;

import tk.porthydra.autofarm.AutoFarm;

public class Config {
	
	private AutoFarm plugin;
	
	public Config(AutoFarm plugin) {
		this.plugin = plugin;
	}
	
	public Boolean defaultToggle() {
		return plugin.getConfig().getBoolean("defaultToggle");
	}
	
	public Material defaultMode() {
		String string = plugin.getConfig().getString("autoplant.defaultMode");
		String[] modes = plugin.getModes();
		for (int i = 0; i < modes.length; i++) {
			if (string.equalsIgnoreCase(modes[i]) || string.equalsIgnoreCase(modes[i] + "s")) {
				return plugin.getCrops()[i];
			}
		}
		return null;
	}
	
	public boolean isItemEnabled() {
		return plugin.getConfig().getBoolean("autoreplant.item.enabled");
	}
	
	public Material autoItem() {
		return Material.getMaterial(plugin.getConfig().getString("autoreplant.item.material"));
	}
	
	public int durabilityLoss() {
		return plugin.getConfig().getInt("autoreplant.item.durabilityLoss");
	}
	
	public List<String> getReplantMessage() {
		List<String> list = plugin.getConfig().getStringList("autoplant.replantMessage");
		List<String> colouredList = new ArrayList<String>();
		if (list != null) { 
			for (String s : list) {
				if (s != null) colouredList.add(ChatColor.translateAlternateColorCodes('&', s));
			}
		}
		else return null;
		return colouredList;
	}
	
	public List<String> getAutoPlantMessage() {
		List<String> list = plugin.getConfig().getStringList("autoplant.autoPlantMessage");
		List<String> colouredList = new ArrayList<String>();
		if (list != null) { 
			for (String s : list) {
				if (s != null) colouredList.add(ChatColor.translateAlternateColorCodes('&', s));
			}
		}
		else return null;
		return colouredList;
	}
	
	public boolean isAutoRePlant() {
		return plugin.getConfig().getBoolean("autoreplant.enabled");
	}
	
	public boolean isAutoPlant() {
		return plugin.getConfig().getBoolean("autoplant.enabled");
	}
	
	public void reload() {
		plugin.reloadConfig();
		plugin.setC(this);
	}
} 
