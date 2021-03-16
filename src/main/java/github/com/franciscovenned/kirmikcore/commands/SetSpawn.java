package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.MessageHandler;
import github.com.franciscovenned.kirmikcore.utils.SpawnHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn extends PlayerCommand {

    private KirmikCore plugin;
    private SpawnHandler spawnHandler;
    private MessageHandler messageHandler;

    public SetSpawn(KirmikCore plugin, SpawnHandler spawnHandler) {
        this.plugin = plugin;
        plugin.getServer().getPluginCommand("setspawn").setExecutor(this);
        this.spawnHandler = spawnHandler;
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    @Override
    protected void execute(Player player, String[] strings) {
        if (!(player instanceof CommandSender)) {
            player.sendMessage("No puedes ejecutar eso en consola.");
            return;
        }
        if (!Utilities.checkPermissions((CommandSender)player, true, new String[] { "kirmikcore.spawn", "kirmikcore.*" })) {
            Utilities.message((CommandSender)player, this.messageHandler.string("No_Permission", "#ff4a4aNo tienes permisos."));
            return;
        }
        if (strings.length != 0 && strings[0].equalsIgnoreCase("reload")) {
            player.sendMessage("El plugin fue cargado con exito.");
            this.plugin.reloadConfig();
            return;
        }
        player.sendMessage("El spawn fue asignado con exito");
        SpawnHandler spawnHandler = new SpawnHandler(this.plugin, player);
        spawnHandler.setSpawnLocation(player.getLocation());
    }
}
