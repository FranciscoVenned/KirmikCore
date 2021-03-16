package github.com.franciscovenned.kirmikcore.events;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    private KirmikCore plugin;

    public PlayerLeave(KirmikCore plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerJoinEvent event){
        Player player = event.getPlayer();
        int number = plugin.getPlayerslist().getConfig().getInt(player.getUniqueId().toString() + ".Uso");
        plugin.getLimitshome().put(player, number);

    }
}
