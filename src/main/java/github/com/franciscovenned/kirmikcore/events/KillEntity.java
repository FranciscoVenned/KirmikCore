package github.com.franciscovenned.kirmikcore.events;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillEntity implements Listener {


    private KirmikCore plugin;
    private MessageHandler messageHandler;

    public KillEntity(KirmikCore plugin){
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    @EventHandler
    public void rewardKillEntity(EntityDeathEvent event){

        Player player = event.getEntity().getKiller();

        Entity entity = event.getEntity();

        EntityType entityType = entity.getType();

            for (String string : plugin.getEntidades().getConfig().getStringList("Entidades")) {
                if (entityType != EntityType.valueOf(string.toUpperCase())) return;
                double dinero = 20.0;
                KirmikCore.getEconomy().depositPlayer(player, dinero);

                Utilities.message((CommandSender)player, this.messageHandler.string("Money_Add", "&c&lSe te acaba de agregar a tu cuenta " + dinero).replace("%money%", String.valueOf(dinero)));
            }
    }

}