package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Invsee extends PlayerCommand {



    @Override
    protected void execute(Player player, String[] strings) {
        if (!(player instanceof CommandSender)) {
            player.sendMessage("No se puede ejecutar esto en la consola.");
            return;
        }
        if(!Utilities.checkPermissions((CommandSender)player, true, "kirmikcore.invsee", "kirmikcore.admin")){
            player.sendMessage("No tienes permisos para ello");
            return;
        }
        Player target = Bukkit.getPlayer(strings[0]);
        if (target == null) {
            player.sendMessage("&cEse jugador no esta conectado");
        }
        player.openInventory(target.getInventory());
    }
}
