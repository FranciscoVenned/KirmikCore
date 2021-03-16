package github.com.franciscovenned.kirmikcore.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    private static JavaPlugin instance;

    private static final HashMap<String, CommandExecutor> commands = new HashMap<>();

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static void setInstance(JavaPlugin instance) {
        Utilities.instance = instance;
    }

    public static String colourise(String message) {
        Pattern pattern = Pattern.compile("(#|&#)[a-fA-F0-9]{6}");
        if (Bukkit.getVersion().contains("1.16")) {
            Matcher matcher = pattern.matcher(message);
            while (matcher.find()) {
                String colourise = message.substring(matcher.start(), matcher.end());
                message = message.replace(colourise, ChatColor.of(colourise) + "");
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colourise(List<String> messages) {
        List<String> strings = new ArrayList<>();
        for (String string : messages)
            strings.add(colourise(string));
        return strings;
    }

    public static void message(CommandSender player, String message) {
        player.sendMessage(colourise(message));
    }

    public static void message(CommandSender player, String... messages) {
        for (String message : messages)
            message(player, message);
    }

    public static void broadcast(String message) {
        Bukkit.getServer().broadcastMessage(colourise(message));
    }

    public static void broadcast(String... messages) {
        for (String message : messages)
            broadcast(message);
    }

    public static void broadcast(List<String> messages) {
        for (String message : messages)
            broadcast(message);
    }

    public static boolean checkPermission(CommandSender player, boolean checkOp, String permission) {
        if (checkOp)
            return (player.hasPermission(permission) || player.isOp());
        return player.hasPermission(permission);
    }

    public static boolean checkPermissions(CommandSender player, boolean checkOp, String... permissions) {
        String[] arrayOfString = permissions;
        int i = arrayOfString.length;
        byte b = 0;
        if (b < i) {
            String permission = arrayOfString[b];
            return checkPermission(player, checkOp, permission);
        }
        return false;
    }

    public static void logInfo(boolean prefix, String message) {
        if (prefix == true) {
            Bukkit.getLogger().info(colourise("[" + getInstance().getDescription().getName() + "] " + message));
        } else {
            Bukkit.getLogger().info(colourise(message));
        }
    }

    public static void logInfo(boolean prefix, String... messages) {
        for (String message : messages) {
            if (prefix == true) {
                logInfo(true, message);
            } else {
                logInfo(false, message);
            }
        }
    }

    public static void logWarning(boolean prefix, String message) {
        if (prefix == true) {
            Bukkit.getLogger().warning(colourise("[" + getInstance().getDescription().getName() + "] " + message));
        } else {
            Bukkit.getLogger().warning(colourise(message));
        }
    }

    public static void logWarning(boolean prefix, String... messages) {
        for (String message : messages) {
            if (prefix == true) {
                logWarning(true, message);
            } else {
                logWarning(false, message);
            }
        }
    }

    public static void logSevere(boolean prefix, String message) {
        if (prefix == true)
            Bukkit.getLogger().severe(colourise("[" + getInstance().getDescription().getName() + "] " + message));
        Bukkit.getLogger().severe(colourise(message));
    }

    public static void logSevere(boolean prefix, String... messages) {
        for (String message : messages) {
            if (prefix == true) {
                logSevere(true, message);
            } else {
                logSevere(false, message);
            }
        }
    }

    public static void sendActionBar(Player player, String message) {
        try {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(colourise(message)));
        } catch (Throwable throwable) {
            message((CommandSender)player, message);
        }
    }

    public static void sendActionBar(Player player, String message, boolean sendMessage) {
        try {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(colourise(message)));
        } catch (Throwable throwable) {
            if (sendMessage == true)
                message((CommandSender)player, message);
        }
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(colourise(title), colourise(subtitle), 20, 60, 10);
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadein, int stay, int fadeout) {
        player.sendTitle(colourise(title), colourise(subtitle), 20 * fadein, 20 * stay, 20 * fadeout);
    }

    public static void addPotionEffect(Player player, PotionEffectType effect, int durationInSeconds, int amplifier) {
        player.addPotionEffect(new PotionEffect(effect, durationInSeconds * 20, amplifier));
    }

    public static void addPotionEffect(Player player, PotionEffectType effect, int durationInSeconds, int amplifier, boolean ambient, boolean particles, boolean icon) {
        player.addPotionEffect(new PotionEffect(effect, durationInSeconds * 20, amplifier, ambient, particles, icon));
    }

    public static void removePotionEffect(Player player, PotionEffectType effect) {
        player.removePotionEffect(effect);
    }

    public static boolean isPluginPresent(String plugin) {
        return (Bukkit.getServer().getPluginManager().getPlugin(plugin) != null);
    }

    public static void registerCommand(String command, CommandExecutor commandExecutor) {
        getInstance().getCommand(command).setExecutor(commandExecutor);
    }

    public static void registerMapCommand(Command command) {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap)commandMapField.get(Bukkit.getServer());
            commandMap.register(command.getLabel(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, (Plugin)getInstance());
    }

    public static void registerCommands() {
        for (String s : commands.keySet())
            getInstance().getCommand(s).setExecutor(commands.get(s));
    }

    public static void registerMapCommands(Command... commands) {
        for (Command command : commands)
            registerMapCommand(command);
    }

    public static void registerEvents(Listener... listeners) {
        for (Listener listener : listeners)
            registerEvent(listener);
    }

    public static HashMap<String, CommandExecutor> setCommand() {
        return commands;
    }
}

