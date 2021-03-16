package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.GlobalCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearWarps extends GlobalCommand {
    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    public ClearWarps(KirmikCore plugin) {
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    protected void execute(CommandSender sender, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!Utilities.checkPermissions((CommandSender)player, true, new String[] { "kirmikcore.clearwarps", "kirmikcore.admin" })) {
                Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
                return;
            }
            for (String warpName : this.plugin.getWarpsFile().getConfig().getKeys(false))
                this.plugin.getWarpsFile().getConfig().set(warpName, null);
            this.plugin.getWarpsFile().saveConfig();
            Utilities.message((CommandSender)player, this.messageHandler.string("Clear_Warps_Message", "#ff4a4aYou have deleted all the warps!"));
            return;
        }
        if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            for (String warpName : this.plugin.getWarpsFile().getConfig().getKeys(false))
                this.plugin.getWarpsFile().getConfig().set(warpName, null);
            this.plugin.getWarpsFile().saveConfig();
            Utilities.logInfo(true, "Todos los Warps fueron eliminados.");
        }
    }
}