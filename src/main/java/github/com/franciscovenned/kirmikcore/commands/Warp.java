package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.commands.GlobalCommand;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.Warps;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warp extends GlobalCommand {

    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    public Warp(KirmikCore plugin) {
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    protected void execute(CommandSender sender, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (strings.length == 0) {
                Utilities.message((CommandSender)player, this.messageHandler
                        .getPrefix() + "#14abc9Warp: #ff4a4a/warp <warpname> #14abc9- Un Nombre del Warp Especifico.", this.messageHandler
                        .getPrefix() + "#14abc9Warp Otros #ff4a4a/warp <warpname> <playername> #14abc9- Manda a otro a la locacion del Warp");
                return;
            }
            if (strings.length == 1) {
                String warpName = strings[0];
                playerWarp(player, warpName);
                return;
            }
            if (strings.length == 2) {
                Player target = Bukkit.getPlayer(strings[1]);
                String warpName = strings[0];
                playerWarpOthers((CommandSender)player, target, warpName);
                return;
            }
        }
        if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            if (strings.length != 2) {
                Utilities.logInfo(true, "Warp Others /warp <warpname> <playername> - Send another player to a warp location");
                return;
            }
            Player target = Bukkit.getPlayer(strings[1]);
            String warpName = strings[0];
            playerWarpOthers(sender, target, warpName);
        }
    }

    private void playerWarp(Player player, String warpName) {
        Warps warpHandler = new Warps(this.plugin, player, warpName);
        if (!this.plugin.getWarpsFile().getConfig().isSet(warpName)) {
            Utilities.message((CommandSender)player, this.messageHandler.string("Invalid_Warp_Name", "#ff4a4aEse Warp no existe!"));
            return;
        }
        if (!this.plugin.getConfigFile().getConfig().getBoolean("Per_Warp_Permissions")) {
            if (!Utilities.checkPermissions((CommandSender)player, true, "kirmikcore.warps", "kirmikcore.admin")) {
                Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aNo tienes permisos."));
                return;
            }
            warpHandler.beforeWarpEffects();
            warpHandler.postWarpEffects();
            return;
        }
        if (!Utilities.checkPermissions((CommandSender)player, true, new String[] { "kirmikcore.warp." + warpName.toLowerCase(), "kirmikcore.warp.all", "kirmikcore.admin" })) {
            Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aNo tienes permisos para ello."));
            return;
        }
        warpHandler.beforeWarpEffects();
        warpHandler.postWarpEffects();
    }

    private void playerWarpOthers(CommandSender sender, Player target, String warpName) {
        Warps warpHandler = new Warps(this.plugin, target, warpName);
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!this.plugin.getWarpsFile().getConfig().isSet(warpName)) {
                Utilities.message((CommandSender)player, this.messageHandler.string("Invalid_Warp_Name", "#ff4a4aSorry, that warp does not exist!"));
                return;
            }
            if (!Utilities.checkPermissions((CommandSender)player, true, new String[] { "kirmikcore.warps.others", "kirmikcore.*" })) {
                Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
                return;
            }
            if (target == null) {
                Utilities.message((CommandSender)player, this.messageHandler.string("Invalid_Player", "#ff4a4aEl Jugador no fue encontrado."));
                return;
            }
            warpHandler.beforeWarpEffects();
            warpHandler.postWarpEffects();
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
            warpHandler.beforeWarpEffects();
            warpHandler.postWarpEffects();
        }
    }
}
