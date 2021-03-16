package github.com.franciscovenned.kirmikcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChangeItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof CommandSender){
            Player player = (Player) sender;
            Bukkit.getConsoleSender().sendMessage("No se puede ejecutar este comando en la consola");
        }
        Player player = (Player) sender;
        Material material = player.getItemInHand().getType();
        ItemStack itemStack = player.getItemInHand();
        Material changeItem = Material.BEDROCK;
        player.getInventory().getItemInMainHand().setType(changeItem);

        return false;
    }
}
