package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpList extends PlayerCommand {
    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    public WarpList(KirmikCore plugin) {
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    protected void execute(Player player, String[] strings) {
        if (!Utilities.checkPermissions((CommandSender)player, true, new String[] { "kirmikcore.listwarps", "kirmikcore.admin" })) {
            Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
            return;
        }
        if (this.plugin.getWarpsFile().getConfig().getKeys(false).size() == 0) {
            Utilities.message((CommandSender)player, this.messageHandler.getPrefix() + "#ff4a4aNo hay ningun Warp seteado.");
            return;
        }
        Utilities.message((CommandSender)player, this.messageHandler.getPrefix() + "#14abc9Los Warps Actuales:");
        for (String warpName : this.plugin.getWarpsFile().getConfig().getKeys(false))
            Utilities.message((CommandSender)player, "#ff4a4a" + warpName);
    }
}
