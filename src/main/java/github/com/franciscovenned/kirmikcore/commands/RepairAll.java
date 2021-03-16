package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairAll extends PlayerCommand {

    private final MessageHandler messageHandler;
    private KirmikCore plugin;

    public RepairAll(KirmikCore plugin){
        this.plugin = plugin;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    @Override
    protected void execute(Player player, String[] strings) {
        if (!(player instanceof CommandSender)) {
            player.sendMessage("No se puede ejecutar esto en la consola.");
            return;
        }
        if(!Utilities.checkPermissions((CommandSender)player, true, "kirmikcore.repairall", "kirmikcore.admin")){
            Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aSorry, no tienes permisos para esto"));
            return;
        }
        if (strings.length == 0){
            fixall(player);
            Utilities.message((CommandSender)player, this.messageHandler.string("Repair_all", "#ff4a4aTus items se repararon con exito"));

        }
        if (strings.length == 1){
            String target = strings[0];
            Player target_player = Bukkit.getPlayer(target);

            if (target_player == null){
                player.sendMessage("El jugador no esta conectado");
            } else{
                fixall(target_player);
                target_player.sendMessage("Tus items fueron reparados por " + player);
            }
        }



    }

    public static void fixall(Player player) {

        ItemStack[] items = player.getInventory().getContents();
        ItemStack[] armor = player.getInventory().getArmorContents();
        ItemStack[] secondhand = player.getInventory().getExtraContents();

        for (ItemStack item : items) {
            if (!(item == null)) {
                item.setDurability((short) 0);
            }
        }

        for (ItemStack item : armor) {
            if (!(item == null)) {
                item.setDurability((short) 0);
            }
        }
    }
}
