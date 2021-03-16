package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.Homes;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.GlobalCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RemoveHome extends GlobalCommand {
    private final KirmikCore plugin;
    private final MessageHandler messageHandler;
    private FileConfiguration homes;

    public RemoveHome(KirmikCore plugin){
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
        this.homes = plugin.getHomesFile().getConfig();
    }

    @Override
    protected void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;
        if (sender instanceof Player){
            if(!Utilities.checkPermissions((CommandSender)player, true, new String[]{"kirmikcore.delhome", "kirmikcore.admin"})){
                Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, no tienes permisos para esto"));
                return;
            }
            if (strings.length != 1) {
                Utilities.message((CommandSender)player, this.messageHandler.getPrefix() + "#14abc9/delhome <home name> - Remueve un home especifico.");
                return;
            }

            String homeName = strings[0];
            if (this.homes.getConfigurationSection(player.getUniqueId().toString()) != null){
                for (String path : this.homes.getConfigurationSection(player.getUniqueId().toString()).getKeys(false)) {
                    if (homeName.equalsIgnoreCase(path)) {
                        ConfigurationSection camino = plugin.getHomesFile().getConfig().getConfigurationSection(player.getUniqueId().toString());
                        camino.set(homeName, null);
                        player.sendMessage("Has creado removido un home con exito");
                        plugin.getHomesFile().saveConfig();
                        plugin.getLimitshome().compute(player, (k,v) -> v-1);
                        return;
                    }
                }
            }
        }
        String warpName = strings[0];
        boolean find = false;
        if (this.homes.getConfigurationSection(player.getUniqueId().toString()) != null) {
            for (String path : this.homes.getConfigurationSection(player.getUniqueId().toString()).getKeys(false)) {
                if (warpName.equalsIgnoreCase(path)) {
                    find = true;
                    break;
                }
            }
        }
        if (!find) {
            player.sendMessage("No existe el home");
            return;
        }
        this.plugin.getWarpsFile().getConfig().set(warpName, null);
        this.plugin.getWarpsFile().saveConfig();
        Utilities.logInfo(true, this.messageHandler.string("Remove_Home_Message", "#14abc9La casa  %homeName% fue eliminada con exito").replace("%homeName%", warpName));

    }
}
