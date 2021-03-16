package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class HealCommand extends PlayerCommand {
    private KirmikCore plugin;

    public HealCommand(KirmikCore plugin){
        this.plugin = plugin;
    }

    @Override
    protected void execute(Player player, String[] strings) {
        if (!(player instanceof CommandSender)) {
            player.sendMessage("No se puede ejecutar esto en la consola.");
            return;
        }
        if(!Utilities.checkPermissions((CommandSender)player, true, "kirmikcore.heal", "kirmikcore.admin")){
            player.sendMessage("No tienes permisos para ello");
            return;
        }
        if (strings.length != 0 && strings[0].equalsIgnoreCase("reload")) {
            player.sendMessage("[KirmikCore] Fue recargado todos los sitemas.");
            this.plugin.reloadConfig();
            return;
        }
        if (player.getHealth() == 20 && player.getFoodLevel() == 20) {
            player.sendMessage("Ya estas todo curado no puedes curarte mas");
            return;
        }
        player.setFoodLevel(20);
        player.setHealth(20);
        player.sendMessage("Fuiste curado con exito!");

    }
}
