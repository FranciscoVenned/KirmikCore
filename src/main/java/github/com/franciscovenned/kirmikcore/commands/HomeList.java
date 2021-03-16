package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HomeList extends PlayerCommand {

    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    private FileConfiguration homes;

    public HomeList(KirmikCore plugin) {
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
        this.homes = plugin.getHomesFile().getConfig();
    }

    protected void execute(Player player, String[] strings) {
        if (!Utilities.checkPermissions((CommandSender) player, true, "kirmikcore.listhome", "kirmikcore.admin")) {
            Utilities.message((CommandSender) player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
            return;
        }
        if (this.plugin.getWarpsFile().getConfig().getKeys(false).size() == 0) {
            Utilities.message((CommandSender) player, this.messageHandler.getPrefix() + "#ff4a4aNo hay ningun home guardado.");
            return;
        }
        if (!homes.contains(player.getUniqueId().toString())){
            player.sendMessage("No tienes homes");
        }
        player.sendMessage("Estos son tus homes pelotudo por que no tienes memoria me haces gastar neuronas");

        for (String path : homes.getConfigurationSection(player.getUniqueId().toString()).getKeys(false)) {
            player.sendMessage(path);
        }

    }
}



