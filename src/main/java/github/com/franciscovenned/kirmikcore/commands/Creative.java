package github.com.franciscovenned.kirmikcore.commands;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import github.com.franciscovenned.kirmikcore.utils.SpawnHandler;
import github.com.franciscovenned.kirmikcore.utils.commands.PlayerCommand;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Creative extends PlayerCommand {

    private KirmikCore plugin;

    public Creative(KirmikCore plugin){
        this.plugin = plugin;
    }

    @Override
    protected void execute(Player player, String[] strings) {
        if (!(player instanceof CommandSender)) {
            player.sendMessage("No se puede ejecutar esto en la consola.");
            return;
        }
        if (!player.hasPermission("kirmikcore.gamemode")) return;
        if (strings.length != 0 && strings[0].equalsIgnoreCase("reload")) {
            player.sendMessage("[KirmikCore] Fue relodeado el sistema de Cambio de Modo.");
            this.plugin.reloadConfig();
            return;
        }
        player.setGameMode(GameMode.CREATIVE);
    }
}
