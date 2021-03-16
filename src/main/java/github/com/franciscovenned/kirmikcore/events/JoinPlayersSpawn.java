package github.com.franciscovenned.kirmikcore.events;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.commands.SpawnCommand;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.SpawnHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinPlayersSpawn implements Listener {

    private KirmikCore plugin;

    private SpawnHandler spawnHandler;
    private final MessageHandler messageHandler;

    public JoinPlayersSpawn(KirmikCore plugin, SpawnHandler spawnHandler){
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
        this.spawnHandler = spawnHandler;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = (Player) event.getPlayer();
            if (!plugin.getPlayerslist().getConfig().isSet(player.getUniqueId().toString())) {
                this.plugin.getPlayerslist().getConfig().set(player.getUniqueId().toString(), "");
                this.plugin.getPlayerslist().saveConfig();
                playerSpawn(player);
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "kit give inicio " + player.getName();
                String command2 = "lp user " + player.getName() + " parent set guerrero";
                Bukkit.dispatchCommand(console, command);
                Bukkit.dispatchCommand(console, command2);
                KirmikCore.getEconomy().createPlayerAccount(player);
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
