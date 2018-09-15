package tk.porthydra.autofarm.listener;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import tk.porthydra.autofarm.AutoFarm;
import tk.porthydra.autofarm.config.Config;

public class PlayerLogin implements Listener {
	
	private AutoFarm plugin;
	
	public PlayerLogin(AutoFarm plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e) {
		Config c = plugin.getC();
		HashMap<Player, Boolean> toggles = plugin.getToggles();
		HashMap<Player, Material> mode = plugin.getMode();
		if (!toggles.containsKey(e.getPlayer())) {
			toggles.put(e.getPlayer(), c.defaultToggle());
			if(c.sendMessageOnLogin())
				e.getPlayer().sendMessage(ChatColor.GOLD + "Automatic farming mode: " + ChatColor.DARK_RED + c.defaultToggle());
		}
		if (!mode.containsKey(e.getPlayer())) {
			mode.put(e.getPlayer(), c.defaultMode());
			if(c.sendMessageOnLogin())
				e.getPlayer().sendMessage(ChatColor.GOLD + "Mode set to: " + ChatColor.DARK_RED + c.defaultMode().toString());
		}
		plugin.setToggles(toggles);
		plugin.setMode(mode);
	}
}
