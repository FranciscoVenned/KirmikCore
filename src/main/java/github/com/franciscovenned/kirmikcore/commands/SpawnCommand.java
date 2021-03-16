package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.SpawnHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.Warps;
import github.com.franciscovenned.kirmikcore.utils.commands.GlobalCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends GlobalCommand {

    KirmikCore plugin;
    private SpawnHandler spawnHandler;
    private final MessageHandler messageHandler;

    public SpawnCommand(KirmikCore plugin, SpawnHandler spawnHandler) {
        this.plugin = plugin;
        this.spawnHandler = spawnHandler;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    protected void execute(CommandSender sender, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (strings.length == 0) {
                playerSpawn(player);
                return;
            }
            if (strings.length == 1) {
                Player target = Bukkit.getPlayer(strings[0]);
                if (Bukkit.getOnlinePlayers().contains(target)) {
                    playerSpawn(target);
                } else {
                    player.sendMessage("No esta el jugador");
                }
                return;
            }
        }

        if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            if (strings.length != 1) {
                Utilities.logInfo(true, "Warp Others /spawn <playername> - Send another player to a warp location");
                return;
            }
            Player target = Bukkit.getPlayer(strings[0]);
        }
    }

    private void playerSpawn(Player player) {
        SpawnHandler spawnHandler = new SpawnHandler(this.plugin, player);
        if (!this.plugin.getConfigFile().getConfig().isSet("spawn")) {
            player.sendMessage("No hay por el momento ningun spawn");
            return;
        }
            spawnHandler.beforeSpawnEffects();
            spawnHandler.postSpawnEffects();
    }
}
