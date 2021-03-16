package github.com.franciscovenned.kirmikcore.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class GlobalCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        execute(sender, args);
        return true;
    }

    protected abstract void execute(CommandSender paramCommandSender, String[] paramArrayOfString);
}
