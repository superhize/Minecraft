package tk.porthydra.autofarm.listener;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import tk.porthydra.autofarm.AutoFarm;

public class PlayerLogout implements Listener {
	
	private AutoFarm plugin;
	
	public PlayerLogout(AutoFarm plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e) {
		HashMap<Player, Boolean> toggles = plugin.getToggles();
		HashMap<Player, Material> mode = plugin.getMode();
		if (toggles.containsKey(e.getPlayer())) {
			toggles.remove(e.getPlayer());
		}
		if (mode.containsKey(e.getPlayer())) {
			mode.remove(e.getPlayer());
		}
		plugin.setMode(mode);
		plugin.setToggles(toggles);
	}
}
