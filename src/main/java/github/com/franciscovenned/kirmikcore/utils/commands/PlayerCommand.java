package github.com.franciscovenned.kirmikcore.utils.commands;

import github.com.franciscovenned.kirmikcore.utils.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utilities.message(sender, "&4You must be a player to use this command!");
            return false;
        }
        execute((Player)sender, args);
        return true;
    }

    protected abstract void execute(Player paramPlayer, String[] paramArrayOfString);
}