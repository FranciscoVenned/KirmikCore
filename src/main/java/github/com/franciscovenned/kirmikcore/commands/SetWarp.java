package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.Warps;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarp extends PlayerCommand {

    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    public SetWarp(KirmikCore plugin) {
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    @Override
    protected void execute(Player player, String[] strings) {
        if (!Utilities.checkPermissions((CommandSender)player, true, new String[] { "kirmikCore.setwarp", "kirmikCore.admin" })){
            Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
            return;
        }
        if (strings.length == 0) {
            Utilities.message((CommandSender)player, this.messageHandler.getPrefix() + "#14abc9/setwarp <player name> <warp name> - Crea un warp en la posicion del jugador.");
            Utilities.message((CommandSender)player, this.messageHandler.getPrefix() + "#14abc9/setwarp <warp name> - Crea un warp en tu posicion.");
            return;
        }
        Warps warpHandler = new Warps(this.plugin, player, strings[0]);
        if (strings.length == 1) {
            warpHandler.createWarp(player.getLocation());
            return;
        }

    }
}
