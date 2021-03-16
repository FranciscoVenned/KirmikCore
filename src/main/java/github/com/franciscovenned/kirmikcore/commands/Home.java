package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.Homes;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.Warps;
import github.com.franciscovenned.kirmikcore.utils.commands.GlobalCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Home extends GlobalCommand {

    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    private FileConfiguration homeFile;

    private FileConfiguration config;

    public Home(KirmikCore plugin) {
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
        this.homeFile = plugin.getHomesFile().getConfig();
        this.config = plugin.getConfig();
    }

    protected void execute(CommandSender sender, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (strings.length == 0) {
                Utilities.message((CommandSender)player, this.messageHandler
                        .getPrefix() + "#14abc9Home: #ff4a4a/home <warpname> #14abc9- Debes especificar el home.", this.messageHandler
                        .getPrefix() + "#14abc9Home a otros #ff4a4a/home <homename> <playername> #14abc9- Envia a otro jugar a la posicion");
                return;
            }
            if (strings.length == 1) {
                String homeName = strings[0];
                playerHome(player, homeName);
                return;
            }
            if (strings.length == 2) {
                Player target = Bukkit.getPlayer(strings[1]);
                String homeName = strings[0];
                playerHomeOthers(player, target, homeName);
                return;
            }
        }
        if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            if (strings.length != 2) {
                Utilities.logInfo(true, "Home a otros /home <homename> <playername> - Enviar a otro jugador a la posicion");
                return;
            }
            Player target = Bukkit.getPlayer(strings[1]);
            String homeName = strings[0];
            playerHomeOthers(sender, target, homeName);
        }
    }

    private void playerHome(Player player, String homeName) {
        Homes homes = new Homes(plugin, player, homeName);
        if (this.homeFile.getConfigurationSection(player.getUniqueId().toString()) != null) {
            for (String path : this.homeFile.getConfigurationSection(player.getUniqueId().toString()).getKeys(false)) {
                if (homeName.equalsIgnoreCase(path)) {
                    homes.postHomeEffects();
                    homes.beforeHomeEffects();
                    player.sendMessage("Fuiste teletransportado al home");
                    return;
                }
            }
        }
    }

    private void playerHomeOthers(CommandSender sender, Player target, String warpName) {
        Homes homeHandler = new Homes(this.plugin, target, warpName);
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!this.plugin.getHomesFile().getConfig().isSet(warpName)) {
                Utilities.message((CommandSender)player, this.messageHandler.string("Invalid_Warp_Name", "#ff4a4aSorry, that warp does not exist!"));
                return;
            }
            if (!Utilities.checkPermissions((CommandSender)player, true, new String[] { "omegawarps.warps.others", "omegawarps.*" })) {
                Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
                return;
            }
            if (target == null) {
                Utilities.message((CommandSender)player, this.messageHandler.string("Invalid_Player", "#ff4a4aSorry, but no player with that name was found."));
                return;
            }
            homeHandler.beforeHomeEffects();
            homeHandler.postHomeEffects();
            return;
        }
        if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            if (!this.plugin.getWarpsFile().getConfig().isSet(warpName)) {
                Utilities.logInfo(true, this.messageHandler.console("Invalid_Warp_Name", "Sorry, that warp does not exist!"));
                return;
            }
            if (target == null) {
                Utilities.logInfo(true, this.messageHandler.console("Invalid_Player", "Sorry, but no player with that name was found."));
                return;
            }
            homeHandler.beforeHomeEffects();
            homeHandler.postHomeEffects();
        }
    }
}
