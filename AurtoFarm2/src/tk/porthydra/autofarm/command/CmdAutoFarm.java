package tk.porthydra.autofarm.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.porthydra.autofarm.AutoFarm;
import tk.porthydra.autofarm.config.Config;

public class CmdAutoFarm implements CommandExecutor {
	
	private AutoFarm plugin;
	private Config c;
	
	public CmdAutoFarm(AutoFarm plugin) {
		this.plugin = plugin;
		this.c = plugin.getC();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("autofarm")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("mode") && args.length == 2 && p.hasPermission("autofarm.mode")) {
						String[] modes = plugin.getModes();
						Boolean found = false;
						for (int i = 0; i < modes.length; i++) {
							if (args[1].equalsIgnoreCase(modes[i]) || args[1].equalsIgnoreCase(modes[i] + "s")) {
								HashMap<Player, Material> mode = plugin.getMode();
								mode.put(p, plugin.getCrops()[i]);
								plugin.setMode(mode);
								p.sendMessage(ChatColor.GOLD + "Mode set to: " + ChatColor.DARK_RED + modes[i]);
								found = true;
							}
						}
						if (!found) {
							help(p);
						}
					}
					else if (args[0].equalsIgnoreCase("toggle") && p.hasPermission("autofarm.toggle")) {
						HashMap<Player, Boolean> toggles = plugin.getToggles();
						toggles.put(p, !toggles.get(p));
						p.sendMessage(ChatColor.GOLD + "Automatic farming mode: " + ChatColor.DARK_RED + toggles.get(p));
					}
					else if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("autofarm.reload")) {
						c.reload();
						sender.sendMessage(ChatColor.GOLD + "Plugin reloaded!");
					}
					else help(p);
				}
				else help(p);
			}
			else if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("autofarm.reload")) {
					c.reload();
					sender.sendMessage("Plugin reloaded!");
			}
			else sender.sendMessage("Unrecognised arguments! Some commands require you to be a player");
		}
		return true;
	}
	
	private void help(Player p) {
		p.sendMessage(ChatColor.GOLD + "Unrecognised arguments! Valid sub-commands: ");
		if (p.hasPermission("autofarm.toggle")) p.sendMessage(ChatColor.BLUE + "   /af toggle"
				+ ChatColor.DARK_AQUA + " - toggle auto-farming on and off");
		if (p.hasPermission("autofarm.mode")) p.sendMessage(ChatColor.BLUE + "   /af mode <wheat|carrot|potato|melon|pumpkin|netherwart|beetroot>"
				+ ChatColor.DARK_AQUA + " - select auto-plant mode");
		if (p.hasPermission("autofarm.reload")) p.sendMessage(ChatColor.BLUE + "   /af reload"
				+ ChatColor.DARK_AQUA + " - reload the plugin's config");
	}

}
