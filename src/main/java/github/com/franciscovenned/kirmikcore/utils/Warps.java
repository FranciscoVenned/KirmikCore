package github.com.franciscovenned.kirmikcore.utils;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Warps {
    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    private final FileConfiguration warpsFile;

    private final Player player;

    private final String warpName;

    public Warps(KirmikCore plugin, Player player, String warpName) {
        this.plugin = plugin;
        this.player = player;
        this.warpName = warpName;
        this.warpsFile = plugin.getWarpsFile().getConfig();
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    public void getWarpLocation() {
        if (!this.warpsFile.isSet(this.warpName))
            return;
        double warpLocationX = this.warpsFile.getDouble(this.warpName + ".Warp Location.X");
        double warpLocationY = this.warpsFile.getDouble(this.warpName + ".Warp Location.Y");
        double warpLocationZ = this.warpsFile.getDouble(this.warpName + ".Warp Location.Z");
        float warpLocationYaw = (float)this.warpsFile.getDouble(this.warpName + ".Warp Location.Yaw");
        float warpLocationPitch = (float)this.warpsFile.getDouble(this.warpName + ".Warp Location.Pitch");
        String world = this.warpsFile.getString(this.warpName + ".Warp Location.World");
        World warpLocationWorld = Bukkit.getServer().getWorld(world);
        if (warpLocationWorld == null)
            return;
        Location warpLocation = new Location(warpLocationWorld, warpLocationX, warpLocationY, warpLocationZ, warpLocationYaw, warpLocationPitch);
        this.player.teleport(warpLocation);
    }

    public void createWarp(Location warpLocation) {
        if (this.warpsFile.isSet(this.warpName)) {
            Utilities.message((CommandSender)this.player, this.messageHandler.string("Warp_Already_Exists", "&cSorry, but that warp already exists"));
            return;
        }
        this.warpsFile.createSection(this.warpName);
        this.warpsFile.set(this.warpName + ".Set By", this.player.getName());
        this.warpsFile.set(this.warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        this.warpsFile.set(this.warpName + ".Warp Location.World", this.player.getWorld().getName());
        this.warpsFile.set(this.warpName + ".Warp Location.X", Double.valueOf(warpLocation.getX()));
        this.warpsFile.set(this.warpName + ".Warp Location.Y", Double.valueOf(warpLocation.getY()));
        this.warpsFile.set(this.warpName + ".Warp Location.Z", Double.valueOf(warpLocation.getZ()));
        this.warpsFile.set(this.warpName + ".Warp Location.Yaw", Float.valueOf(warpLocation.getYaw()));
        this.warpsFile.set(this.warpName + ".Warp Location.Pitch", Float.valueOf(warpLocation.getPitch()));
        this.plugin.getWarpsFile().saveConfig();
        Utilities.message((CommandSender)this.player, this.messageHandler.string("Setwarp_Message.Without_Owner", "&bYou have created the warp %warpName%.").replace("%warpName%", this.warpName));
    }

    public void createWarpOthers(Player target, Location warpLocation, Double warpCost) {
        if (this.warpsFile.isSet(this.warpName)) {
            Utilities.message((CommandSender)this.player, this.messageHandler.string("Warp_Already_Exists", "&cSorry, but that warp already exists"));
            return;
        }
        if (!this.plugin.getConfigFile().getConfig().getBoolean("Warp_Cost.Enabled") || !Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            this.warpsFile.createSection(this.warpName);
            this.warpsFile.set(this.warpName + ".Set By", this.player.getName());
            this.warpsFile.set(this.warpName + ".Set For", target.getName());
            this.warpsFile.set(this.warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            this.warpsFile.set(this.warpName + ".Warp Location.World", this.player.getWorld().getName());
            this.warpsFile.set(this.warpName + ".Warp Location.X", Double.valueOf(warpLocation.getX()));
            this.warpsFile.set(this.warpName + ".Warp Location.Y", Double.valueOf(warpLocation.getY()));
            this.warpsFile.set(this.warpName + ".Warp Location.Z", Double.valueOf(warpLocation.getZ()));
            this.warpsFile.set(this.warpName + ".Warp Location.Yaw", Float.valueOf(warpLocation.getYaw()));
            this.warpsFile.set(this.warpName + ".Warp Location.Pitch", Float.valueOf(warpLocation.getPitch()));
            this.plugin.getWarpsFile().saveConfig();
            Utilities.message((CommandSender)this.player, this.messageHandler.string("Setwarp_Message.With_Owner", "&bYou have created the warp %warpName% for %warpOwner%!").replace("%warpName%", this.warpName).replace("%warpOwner%", target.getName()));
            return;
        }
        if (!Utilities.checkPermissions((CommandSender)target, true, new String[] { "kirmikcore.cost.bypass", "kirmikcore.admin" })) {
            double warpOwnerBalance = this.plugin.getEconomy().getBalance((OfflinePlayer)target);
            if (warpOwnerBalance < warpCost.doubleValue()) {
                Utilities.message((CommandSender)this.player, this.messageHandler.string("Payment_Failed", "&bThe player %player% does not have enough money to pay for the warp.").replace("%player%", target.getName()));
                return;
            }
            this.warpsFile.createSection(this.warpName);
            this.warpsFile.set(this.warpName + ".Set By", this.player.getName());
            this.warpsFile.set(this.warpName + ".Set For", target.getName());
            this.warpsFile.set(this.warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            this.warpsFile.set(this.warpName + ".Warp Location.World", this.player.getWorld().getName());
            this.warpsFile.set(this.warpName + ".Warp Location.X", Double.valueOf(warpLocation.getX()));
            this.warpsFile.set(this.warpName + ".Warp Location.Y", Double.valueOf(warpLocation.getY()));
            this.warpsFile.set(this.warpName + ".Warp Location.Z", Double.valueOf(warpLocation.getZ()));
            this.warpsFile.set(this.warpName + ".Warp Location.Yaw", Float.valueOf(warpLocation.getYaw()));
            this.warpsFile.set(this.warpName + ".Warp Location.Pitch", Float.valueOf(warpLocation.getPitch()));
            this.plugin.getWarpsFile().saveConfig();
            this.plugin.getEconomy().withdrawPlayer((OfflinePlayer)target, warpCost.doubleValue());
            Utilities.message((CommandSender)target, this.messageHandler.string("Warp_Cost_Taken", "&bThe amount of price &c$%warpCost% &bhas been taken from your account for the warp.").replace("%warpCost%", warpCost.toString()));
            Utilities.message((CommandSender)this.player, this.messageHandler.string("Setwarp_Message.With_Owner", "&bYou have created the warp %warpName% for %warpOwner%!").replace("%warpName%", this.warpName).replace("%warpOwner%", target.getName()));
        }
    }

    public void beforeWarpEffects() {
        this.player.playSound(this.player.getLocation(), Sound.valueOf(this.plugin.getConfigFile().getConfig().getString("Sound_Effects.Before_Warp_Sound")), 1.0F, 1.0F);
        this.player.spawnParticle(
                Particle.valueOf(this.plugin
                        .getConfigFile().getConfig().getString("Warp_Particle_Effects.Before_Warp")), this.player
                        .getLocation().getX(), this.player
                        .getLocation().getY(), this.player
                        .getLocation().getZ(), 50);
    }

    public void postWarpEffects() {
        if (!this.warpsFile.isSet(this.warpName)) {
            Utilities.message((CommandSender)this.player, this.messageHandler.string("Invalid_Warp_Name", "&cSorry, that warp does not exist."));
            return;
        }
        (new BukkitRunnable() {
            public void run() {
                Warps.this.getWarpLocation();
                Utilities.message((CommandSender)Warps.this.player, Warps.this.messageHandler.string("Warp_Message", "&cYou have warped to %warpName%").replace("%warpName%", Warps.this.warpName).replace("%player%", Warps.this.player.getDisplayName()));
                Warps.this.player.spawnParticle(Particle.valueOf(Warps.this.plugin.getConfigFile().getConfig().getString("Warp_Particle_Effects.After_Warp")), Warps.this.player.getLocation().getX(), Warps.this.player.getLocation().getY(), Warps.this.player.getLocation().getZ(), 50);
                Warps.this.player.playSound(Warps.this.player.getLocation(), Sound.valueOf(Warps.this.plugin.getConfigFile().getConfig().getString("Sound_Effects.After_Warp_Sound")), 1.0F, 1.0F);
            }
        }).runTaskLater((Plugin)this.plugin, 10L);
    }
}

