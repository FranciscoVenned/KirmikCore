package github.com.franciscovenned.kirmikcore.events;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.SpawnHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathSpawn implements Listener {
    private KirmikCore plugin;

    private SpawnHandler spawnHandler;
    private final MessageHandler messageHandler;

    public DeathSpawn(KirmikCore plugin, SpawnHandler spawnHandler){
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
        this.spawnHandler = spawnHandler;
    }

    @EventHandler
    public void onJoin(PlayerRespawnEvent event){

        Player player = (Player) event.getPlayer();
        playerSpawn(player);
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
