package github.com.franciscovenned.kirmikcore.utils;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Homes {
    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    private final FileConfiguration homeFile;

    private final Player player;

    private final String homeName;



    public Homes(KirmikCore plugin, Player player, String warpName) {
        this.plugin = plugin;
        this.player = player;
        this.homeName = warpName;
        this.homeFile = plugin.getHomesFile().getConfig();
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    public void getHomeLocation() {

        double warpLocationX = this.homeFile.getDouble(player.getUniqueId() + "." + this.homeName + ".Home Location.X");
        double warpLocationY = this.homeFile.getDouble(player.getUniqueId() + "." + this.homeName + ".Home Location.Y");
        double warpLocationZ = this.homeFile.getDouble(player.getUniqueId() + "." + this.homeName + ".Home Location.Z");
        float warpLocationYaw = (float)this.homeFile.getDouble(player.getUniqueId() + "." + this.homeName + ".Home Location.Yaw");
        float warpLocationPitch = (float)this.homeFile.getDouble(player.getUniqueId() + "." + this.homeName + ".Home Location.Pitch");
        String world = this.homeFile.getString(player.getUniqueId() + "." + this.homeName + ".Home Location.World");
        World warpLocationWorld = Bukkit.getServer().getWorld(world);
        if (warpLocationWorld == null)
            return;
        Location warpLocation = new Location(warpLocationWorld, warpLocationX, warpLocationY, warpLocationZ, warpLocationYaw, warpLocationPitch);
        this.player.teleport(warpLocation);
    }

    public void createHome(Location warpLocation) {
        if (this.homeFile.getConfigurationSection(player.getUniqueId().toString()) != null){
            for (String path : this.homeFile.getConfigurationSection(player.getUniqueId().toString()).getKeys(false)) {
                if (this.homeName.equalsIgnoreCase(path)) {
                    Utilities.message(player, this.messageHandler.string("Home_Existe", "Ese Home ya existe"));
                    return;
                }
            }
        }
        if (plugin.getLimitshome().get(this.player) > 2 && !Utilities.checkPermissions((CommandSender)player, true, new String[] { "kirmikcore.home.4", "kirmikcore.*" })){

            Utilities.message(player, this.messageHandler.string("Home_Limite", "Ya has alcanzado al limite maximo"));
            return;
        } else if (plugin.getLimitshome().get(this.player) > 4 && !Utilities.checkPermissions((CommandSender)player, true, new String[] { "kirmikcore.home.4", "kirmikcore.*" })){
            Utilities.message(player, this.messageHandler.string("Home_Limite", "Ya has alcanzado al limite maximo"));
            return;
        } else if (plugin.getLimitshome().get(this.player) > 7){
            Utilities.message(player, this.messageHandler.string("Home_Limite", "Ya has alcanzado al limite maximo"));
            return;
        } else if (plugin.getLimitshome().get(this.player) > 9) {
            Utilities.message(player, this.messageHandler.string("Home_Limite", "Ya has alcanzado al limite maximo"));
            return;
        } else if (plugin.getLimitshome().get(this.player) > 12) {
            player.sendMessage("La revivis scooby");
            return;
        }
        this.homeFile.createSection(player.getUniqueId() + "." + this.homeName);
        this.homeFile.set(player.getUniqueId() + "." + this.homeName + ".Set By", this.player.getName());
        this.homeFile.set(player.getUniqueId() + "." + this.homeName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        this.homeFile.set(player.getUniqueId() + "." + this.homeName + ".Home Location.World", this.player.getWorld().getName());
        this.homeFile.set(player.getUniqueId() + "." + this.homeName + ".Home Location.X", Double.valueOf(warpLocation.getX()));
        this.homeFile.set(player.getUniqueId() + "." + this.homeName + ".Home Location.Y", Double.valueOf(warpLocation.getY()));
        this.homeFile.set(player.getUniqueId() + "." + this.homeName + ".Home Location.Z", Double.valueOf(warpLocation.getZ()));
        this.homeFile.set(player.getUniqueId() + "." + this.homeName + ".Home Location.Yaw", Float.valueOf(warpLocation.getYaw()));
        this.homeFile.set(player.getUniqueId() + "." + this.homeName + ".Home Location.Pitch", Float.valueOf(warpLocation.getPitch()));
        final int newAmount = plugin.getLimitshome().merge(player, 1, Integer::sum);
        this.homeFile.set(player.getUniqueId() + "." + this.homeName + ".Home Number", plugin.getLimitshome().get(player));
        this.plugin.getHomesFile().saveConfig();
        Utilities.message((CommandSender)this.player, this.messageHandler.string("Setwarp_Message.Without_Owner", "&bAcabas de crear un home %warpName%.").replace("%warpName%", this.homeName));
    }

    public void createHomeOthers(Player target, Location warpLocation, Double warpCost) {
        if (this.homeFile.isSet(this.homeName)) {
            Utilities.message((CommandSender)this.player, this.messageHandler.string("Home_Existe", "&cEse Home Ya existe"));
            return;
        }
        if (!this.plugin.getConfigFile().getConfig().getBoolean("Warp_Cost.Enabled") || !Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            this.homeFile.createSection(this.homeName);
            this.homeFile.set(this.homeName + ".Set By", this.player.getName());
            this.homeFile.set(this.homeName + ".Set For", target.getName());
            this.homeFile.set(this.homeName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            this.homeFile.set(this.homeName + ".Home Location.World", this.player.getWorld().getName());
            this.homeFile.set(this.homeName + ".Home Location.X", Double.valueOf(warpLocation.getX()));
            this.homeFile.set(this.homeName + ".Home Location.Y", Double.valueOf(warpLocation.getY()));
            this.homeFile.set(this.homeName + ".Home Location.Z", Double.valueOf(warpLocation.getZ()));
            this.homeFile.set(this.homeName + ".Home Location.Yaw", Float.valueOf(warpLocation.getYaw()));
            this.homeFile.set(this.homeName + ".Home Location.Pitch", Float.valueOf(warpLocation.getPitch()));
            this.plugin.getWarpsFile().saveConfig();
            Utilities.message((CommandSender)this.player, this.messageHandler.string("Setwarp_Message.With_Owner", "&bYou have created the warp %warpName% for %warpOwner%!").replace("%warpName%", this.homeName).replace("%warpOwner%", target.getName()));
            return;
        }
        if (!Utilities.checkPermissions((CommandSender)target, true, new String[] { "kirmikcore.cost.bypass", "kirmikcore.admin" })) {
            double warpOwnerBalance = this.plugin.getEconomy().getBalance((OfflinePlayer)target);
            if (warpOwnerBalance < warpCost.doubleValue()) {
                Utilities.message((CommandSender)this.player, this.messageHandler.string("Payment_Failed", "&bThe player %player% does not have enough money to pay for the warp.").replace("%player%", target.getName()));
                return;
            }
            this.homeFile.createSection(this.homeName);
            this.homeFile.set(this.homeName + ".Set By", this.player.getName());
            this.homeFile.set(this.homeName + ".Set For", target.getName());
            this.homeFile.set(this.homeName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            this.homeFile.set(this.homeName + ".Home Location.World", this.player.getWorld().getName());
            this.homeFile.set(this.homeName + ".Home Location.X", Double.valueOf(warpLocation.getX()));
            this.homeFile.set(this.homeName + ".Home Location.Y", Double.valueOf(warpLocation.getY()));
            this.homeFile.set(this.homeName + ".Home Location.Z", Double.valueOf(warpLocation.getZ()));
            this.homeFile.set(this.homeName + ".Home Location.Yaw", Float.valueOf(warpLocation.getYaw()));
            this.homeFile.set(this.homeName + ".Home Location.Pitch", Float.valueOf(warpLocation.getPitch()));
            this.plugin.getWarpsFile().saveConfig();
            this.plugin.getEconomy().withdrawPlayer((OfflinePlayer)target, warpCost.doubleValue());
            Utilities.message((CommandSender)target, this.messageHandler.string("Warp_Cost_Taken", "&bThe amount of price &c$%warpCost% &bhas been taken from your account for the warp.").replace("%warpCost%", warpCost.toString()));
            Utilities.message((CommandSender)this.player, this.messageHandler.string("Setwarp_Message.With_Owner", "&bYou have created the warp %warpName% for %warpOwner%!").replace("%warpName%", this.homeName).replace("%warpOwner%", target.getName()));
        }
    }

    public void beforeHomeEffects() {
        this.player.playSound(this.player.getLocation(), Sound.valueOf(this.plugin.getConfigFile().getConfig().getString("Sound_Effects.Before_Warp_Sound")), 1.0F, 1.0F);
        this.player.spawnParticle(
                Particle.valueOf(this.plugin
                        .getConfigFile().getConfig().getString("Warp_Particle_Effects.Before_Warp")), this.player
                        .getLocation().getX(), this.player
                        .getLocation().getY(), this.player
                        .getLocation().getZ(), 50);
    }

    public void postHomeEffects() {

        boolean find = false;
        if (this.homeFile.getConfigurationSection(player.getUniqueId().toString()) != null) {
            for (String path : this.homeFile.getConfigurationSection(player.getUniqueId().toString()).getKeys(false)) {
                if (homeName.equalsIgnoreCase(path)) {
                    find = true;
                    break;
                }
            }
        }
        if (!find) {
            player.sendMessage("invalid");
            return;
        }

        (new BukkitRunnable() {
            public void run() {
                Homes.this.getHomeLocation();
                Utilities.message((CommandSender)Homes.this.player, Homes.this.messageHandler.string("Home_Teleport", "&cYou have warped to %homeName%").replace("%homeName%", Homes.this.homeName).replace("%player%", Homes.this.player.getDisplayName()));
                Homes.this.player.spawnParticle(Particle.valueOf(Homes.this.plugin.getConfigFile().getConfig().getString("Warp_Particle_Effects.After_Warp")), Homes.this.player.getLocation().getX(), Homes.this.player.getLocation().getY(), Homes.this.player.getLocation().getZ(), 50);
                Homes.this.player.playSound(Homes.this.player.getLocation(), Sound.valueOf(Homes.this.plugin.getConfigFile().getConfig().getString("Sound_Effects.After_Warp_Sound")), 1.0F, 1.0F);
            }
        }).runTaskLater((Plugin)this.plugin, 10L);
    }
}
