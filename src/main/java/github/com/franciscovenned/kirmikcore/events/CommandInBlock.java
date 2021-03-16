package github.com.franciscovenned.kirmikcore.events;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class CommandInBlock implements Listener {

    private static Map<Player, Location> locationSign = new HashMap<>();
    private KirmikCore plugin;

    public CommandInBlock(KirmikCore plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void InteractBlock(PlayerInteractEvent event){
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getMaterial() != Material.CHEST) return;
        if (!plugin.getJugadorSign().containsKey(event.getPlayer())) return;
        Player player = (Player) plugin.getJugadorSign().get(event.getPlayer());
        player.sendMessage("Acabas de setear un comando al bloque" + "En " + event.getClickedBlock().getLocation().getWorld() + event.getClickedBlock().getLocation().getX() + event.getClickedBlock().getY() + event.getClickedBlock().getZ() );
        locationSign.put(player, event.getClickedBlock().getLocation());
        plugin.getJugadorSign().remove(player);
    }

    @EventHandler
    public void signCommand(PlayerInteractEvent event){
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getMaterial() != Material.CHEST) return;
        if (!locationSign.containsValue(event.getClickedBlock().getLocation())) return;
        event.getPlayer().performCommand("me soy gay");
        event.getPlayer().performCommand("/me xd");
    }
}
