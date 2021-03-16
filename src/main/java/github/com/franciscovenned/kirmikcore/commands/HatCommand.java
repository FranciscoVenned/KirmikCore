package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand extends PlayerCommand {

    @Override
    protected void execute(Player player, String[] strings) {
        if (!(player instanceof CommandSender)) {
            player.sendMessage("No se puede ejecutar esto en la consola.");
            return;
        }
        if (!Utilities.checkPermissions(player, true, "kirmikcore.hat", "kirmikcore.admin")){
            player.sendMessage("No tienes permisos");
            return;
        }
            final ItemStack hand = player.getItemInHand();
            final ItemStack head = player.getInventory().getHelmet();

            if (hand == null || hand.getType() == Material.AIR){
                player.sendMessage("No hay nada en la mano para colocarte en la cabeza");
                return;
            }
            else {
                player.getInventory().setHelmet(hand);
                player.getInventory().removeItem(hand);
            }

            if (head != null && head.getType() != Material.AIR ){
                player.getInventory().addItem(head);
            }



    }
}
