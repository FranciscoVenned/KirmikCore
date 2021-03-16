package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.commands.GlobalCommand;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand extends GlobalCommand {
    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    public MainCommand(KirmikCore plugin) {
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    protected void execute(CommandSender sender, String[] strings) {
        if (strings.length != 1) {
            invalidArgsCommand(sender);
            return;
        }
        switch (strings[0]) {
            case "version":
                versionCommand(sender);
                return;
            case "help":
                helpCommand(sender);
                return;
            case "reload":
                reloadCommand(sender);
                return;
        }
        invalidArgsCommand(sender);
    }

    private void invalidArgsCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            Utilities.message((CommandSender)player, this.messageHandler
                    .getPrefix() + "#14abc9Kirmikcore #ff4a4av" + this.plugin.getDescription().getVersion() + " #14abc9Creado por el Staff", this.messageHandler
                    .getPrefix() + "#ff4a4a/kirmikcore help #14abc9te muestra todo los comandos disponibles");
            return;
        }
        if (sender instanceof org.bukkit.command.ConsoleCommandSender)
            Utilities.logInfo(true, "KirmikCore " + this.plugin
                    .getDescription().getVersion() + " creado por el equipo de staff", "/kirmikcore help te muestra todo los comandos disponibles");
    }

    private void reloadCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!Utilities.checkPermissions((CommandSender)player, true, new String[] { "kirmikcore.reload", "kirmikcore.*" })) {
                Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, but you do not have permission to use this command."));
                return;
            }
            this.plugin.onReload();
            Utilities.message((CommandSender)player, this.messageHandler.string("Reload_Message", "#14abc9OmegaWarps has successfully been reloaded"));
            return;
        }
        if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            this.plugin.onReload();
            Utilities.logInfo(true, this.messageHandler.console("Reload_Message", "OmegaWarps has successfully been reloaded"));
        }
    }

    private void versionCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            Utilities.message((CommandSender)player, this.messageHandler.getPrefix() + "#14abc9KirmikCore #ff4a4av" + this.plugin.getDescription().getVersion() + " #14abc9creado por el Equipo Del Staff");
            return;
        }
        if (sender instanceof org.bukkit.command.ConsoleCommandSender)
            Utilities.logInfo(true, "KirmikCore" + this.plugin.getDescription().getVersion() + " creado por el Equipo Del Staff");
    }

    private void helpCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            Utilities.message((CommandSender)player, this.messageHandler
                    .getPrefix() + " #14abc9KirkimCore" + this.plugin.getDescription().getVersion() + " creado por el Equipo Del Staff", this.messageHandler
                    .getPrefix() + "#14abc9Reload Command: #ff4a4a/kirmikcore reload", this.messageHandler
                    .getPrefix() + "#14abc9Version Command: #ff4a4a/kirmikcore version", this.messageHandler
                    .getPrefix() + "#14abc9SetWarp command: #ff4a4a/setwarp <player> <warp> #14abc9& #ff4a4a/setwarp <warp>", this.messageHandler
                    .getPrefix() + "#14abc9WarpList command: #ff4a4a/listwarps", this.messageHandler
                    .getPrefix() + "#14abc9RemoveWarp command: #ff4a4a/delwarp <warp>", this.messageHandler
                    .getPrefix() + "#14abc9WarpCheck command: #ff4a4a/checkwarp <warp>", this.messageHandler
                    .getPrefix() + "#14abc9Warp command: #ff4a4a/warp <warp>", this.messageHandler
                    .getPrefix() + "#14abc9Home command: #ff4a4a/sethome <home>", this.messageHandler
                    .getPrefix() + "#14abc9HomeRemove command: #ff4a4a/delhome <home>", this.messageHandler
                    .getPrefix() + "#14abc9GameModes command: #ff4a4a/creative /survival");

        }
        if (sender instanceof org.bukkit.command.ConsoleCommandSender)
            Utilities.logInfo(true, " KirmikCore" + this.plugin
                    .getDescription().getVersion() + " creado por El Equipo De Staff", "Reload Command: /kirmikcore reload", "Version Command: /kirmikcore version", "SetWarp command: /setwarp <player> <warp> & /setwarp <warp>", "WarpList command: /listwarps", "RemoveWarp command: /delwarp <warp>", "WarpCheck command: /checkwarp <warp>", "Warp command: /warp <warp>");
    }
}
