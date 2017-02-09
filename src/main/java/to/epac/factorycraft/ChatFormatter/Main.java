package to.epac.factorycraft.ChatFormatter;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;
import to.epac.factorycraft.ChatFormatter.Commands.ChatFormat;

public class Main extends JavaPlugin implements Listener {

	public static Chat chat = null;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		setupChat();

		File configFile;
		configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "Configuration not found. Generating the default one.");
			loadConfiguration();
		}

		getCommand("chatformat").setExecutor(new ChatFormat(this));
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	public void loadConfiguration() {
		//See "Creating you're defaults"
		// NOTE: You do not have to use "plugin." if the class extends the java plugin
		getConfig().options().copyDefaults(true);
		//Save the config whenever you manipulate it
		saveConfig();
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		String msg = getConfig().getString("MessageFormat");
		String prefix = chat.getPlayerPrefix(player);
		String suffix = chat.getPlayerSuffix(player);

		if (getConfig().getBoolean("OpRedName")) {
			if (player.isOp())
				msg = msg.replace("%player%", ChatColor.DARK_RED + player.getDisplayName() + ChatColor.RESET);
		} else
			msg = msg.replace("%player%", player.getDisplayName());

		msg = msg.replace("%prefix%", prefix);
		msg = msg.replace("%suffix%", suffix);
		msg = msg.replace("%message%", event.getMessage());
		msg = ChatColor.translateAlternateColorCodes('&', msg);

		event.setFormat(msg);
	}
}
