package github.com.franciscovenned.kirmikcore.events;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.Coins;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public class DeathMessage implements Listener {

    private KirmikCore plugin;

    public DeathMessage(KirmikCore plugin){
        this.plugin = plugin;
    }

    public void onDeathMessage(PlayerDeathEvent event){

        Player player = (Player) event.getEntity();

        if (!plugin.getPlayerDeath().containsKey(player.getUniqueId())) {

            plugin.getPlayerDeath().put(player.getUniqueId(), "Ah Muerto");

            player.sendMessage("F en el chat");

        }


    }


}
