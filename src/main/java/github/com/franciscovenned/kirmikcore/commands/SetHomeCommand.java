package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.Homes;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.GlobalCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetHomeCommand extends GlobalCommand {

    private KirmikCore plugin;
    private MessageHandler messageHandler;

    public SetHomeCommand(KirmikCore plugin) {
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    @Override
    protected void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;
        if (!Utilities.checkPermissions((CommandSender)sender, true, "kirmikCore.sethome", "kirmikCore.admin")){
            Utilities.message((CommandSender)sender, this.messageHandler.string("No_Permission", "#ff4a4aNo tienes permisos."));
            return;
        }
        if (strings.length == 0) {
            Utilities.message((CommandSender)sender, this.messageHandler.getPrefix() + "#14abc9/sethome <player name> <home> - Para crear una home a otro jugador.");
            Utilities.message((CommandSender)sender, this.messageHandler.getPrefix() + "#14abc9/sethome <home> - Crea un home.");
            return;
        }
        Homes homes = new Homes(plugin, player, strings[0]);
        if (strings.length == 1) {
            homes.createHome(player.getLocation());
            plugin.getPlayerslist().getConfig().set(player.getUniqueId().toString() + ".Uso", plugin.getLimitshome().get(player));
            plugin.getPlayerslist().saveConfig();
        }
    }
}
