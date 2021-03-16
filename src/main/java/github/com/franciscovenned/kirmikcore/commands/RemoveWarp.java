package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.GlobalCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RemoveWarp extends GlobalCommand {
    private final KirmikCore plugin;
    private final MessageHandler messageHandler;

    public RemoveWarp(KirmikCore plugin) {
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());

    }


    @Override
    protected void execute(CommandSender sender, String[] strings) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if(!Utilities.checkPermissions((CommandSender)player, true, "kirmikcore.delwarp", "kirmikcore.admin")){
                Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, no tienes permisos para esto"));
                return;
            }
            if (strings.length != 1) {
                Utilities.message((CommandSender)player, this.messageHandler.getPrefix() + "#14abc9/delwarp <warp name> - Removes a specific warp a player has set.");
                return;
            }

            String warpName = strings[0];
            if (!this.plugin.getWarpsFile().getConfig().isSet(warpName)) {
                Utilities.message((CommandSender)player, this.messageHandler.string("Invalid_Warp_Name", "#ff4a4aSorry, that warp does not exist."));
                return;
            }
            this.plugin.getWarpsFile().getConfig().set(warpName, null);
            this.plugin.getWarpsFile().saveConfig();
            Utilities.message((CommandSender)player, this.messageHandler.string("Remove_Warp_Message", "#14abc9You have successfully deleted the warp %warpName%").replace("%warpName%", warpName));
            return;
        }
        if (sender instanceof ConsoleCommandSender){
            if (strings.length != 1){
                Utilities.logInfo(true, "/delwarp <warp name> - Removes specific warp player has set");
                return;
            }
        }
        String warpName = strings[0];
        if (!this.plugin.getWarpsFile().getConfig().isSet(warpName)){
            Utilities.logInfo(true, "Sorry, the warp do not exist");
            return;
        }
        this.plugin.getWarpsFile().getConfig().set(warpName, null);
        this.plugin.getWarpsFile().saveConfig();
        Utilities.logInfo(true, this.messageHandler.string("Remove_Warp_Message", "#14abc9You have successfully deleted the warp %warpName%").replace("%warpName%", warpName));

    }
}
