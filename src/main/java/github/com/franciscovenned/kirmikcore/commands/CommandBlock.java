package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.events.CommandInBlock;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBlock extends PlayerCommand {

    private KirmikCore plugin;

    public CommandBlock(KirmikCore plugin){
        this.plugin = plugin;
    }

    @Override
    protected void execute(Player player, String[] strings) {
        if (!(player instanceof CommandSender)) {
            player.sendMessage("No se puede ejecutar esto en la consola.");
            return;
        }
        player.sendMessage("Has entrado en modo colocar comandos en blques");
        plugin.getJugadorSign().put(player, player);

    }

}
