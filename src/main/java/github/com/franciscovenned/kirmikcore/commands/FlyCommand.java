package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends PlayerCommand {

    @Override
    protected void execute(Player player, String[] strings) {
        if (!(player instanceof CommandSender)) {
            player.sendMessage("No se puede ejecutar esto en la consola.");
            return;
        }
        if (!Utilities.checkPermissions(player, true, "kirmikcore.fly", "kirmikcore.admin")){
            player.sendMessage("No tienes permisos");
            return;
        }
        if (strings.length < 1) {
            if (player.isFlying()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage("El modo volar fue desactivado");
                return;
            } else {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage("El Modo volar fue activado");
            }
        }

        if (strings.length == 1){
            Player target = Bukkit.getPlayer(strings[0]);
            if (target == null){
                player.sendMessage("Ese jugador no esta online");
                return;
            }
            if (target.isFlying()) {
                target.setAllowFlight(false);
                target.setFlying(false);
                target.sendMessage("Tu modo volar fue desactivado por " + player.getName());
            } else {
                target.setAllowFlight(true);
                target.setFlying(true);
                target.sendMessage("Tu modo volar fue activado");
            }
        }


    }
}
