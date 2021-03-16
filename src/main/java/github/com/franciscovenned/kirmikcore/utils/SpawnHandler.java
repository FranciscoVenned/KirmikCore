package github.com.franciscovenned.kirmikcore.utils;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnHandler {

    private final KirmikCore plugin;

    private final MessageHandler messageHandler;

    private final FileConfiguration spawnFile;

    private final Player player;


    public SpawnHandler(KirmikCore plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.spawnFile = plugin.getConfigFile().getConfig();
        this.messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnFile.createSection("spawn");
        this.spawnFile.set("spawn" + ".world", spawnLocation.getWorld().getName());
        this.spawnFile.set("spawn" +  ".x", Double.valueOf(spawnLocation.getX()));
        this.spawnFile.set("spawn" + ".y", Double.valueOf(spawnLocation.getY()));
        this.spawnFile.set("spawn" + ".z", Double.valueOf(spawnLocation.getZ()));
        this.spawnFile.set("spawn" + ".yaw", Float.valueOf(spawnLocation.getYaw()));
        this.spawnFile.set("spawn" + ".pitch", Float.valueOf(spawnLocation.getPitch()));
        this.plugin.getConfigFile().saveConfig();

    }


    public void getSpawnLocation() {
        double spawnLocationX = this.spawnFile.getDouble("spawn" + ".x");
        double spawnLocationY = this.spawnFile.getDouble("spawn" + ".y");
        double spawnLocationZ = this.spawnFile.getDouble("spawn" + ".z");
        float spawnLocationYaw = (float)this.spawnFile.getDouble("spawn" + ".yaw");
        float spawnLocationPitch = (float)this.spawnFile.getDouble("spawn" + ".pitch");
        String world = this.spawnFile.getString("spawn" + ".world");
        World spawnLocationWorld = Bukkit.getServer().getWorld(world);
        if (spawnLocationWorld == null)
            return;
        Location warpLocation = new Location(spawnLocationWorld, spawnLocationX, spawnLocationY, spawnLocationZ, spawnLocationYaw,spawnLocationPitch);
        this.player.teleport(warpLocation);
    }

    public void beforeSpawnEffects() {
        this.player.playSound(this.player.getLocation(), Sound.valueOf(this.plugin.getConfigFile().getConfig().getString("Sound_Effects.Before_Warp_Sound")), 1.0F, 1.0F);
        this.player.spawnParticle(
                Particle.valueOf(this.plugin
                        .getConfigFile().getConfig().getString("Warp_Particle_Effects.Before_Warp")), this.player
                        .getLocation().getX(), this.player
                        .getLocation().getY(), this.player
                        .getLocation().getZ(), 50);
    }

    public void postSpawnEffects() {
        (new BukkitRunnable() {
            public void run() {
                SpawnHandler.this.getSpawnLocation();
                Utilities.message((CommandSender)SpawnHandler.this.player, SpawnHandler.this.messageHandler.string("Spawn_Message", "&cHas sido teletransportado al spawn").replace("%player%", SpawnHandler.this.player.getDisplayName()));
                SpawnHandler.this.player.spawnParticle(Particle.valueOf(SpawnHandler.this.plugin.getConfigFile().getConfig().getString("Warp_Particle_Effects.After_Warp")), SpawnHandler.this.player.getLocation().getX(), SpawnHandler.this.player.getLocation().getY(), SpawnHandler.this.player.getLocation().getZ(), 50);
                SpawnHandler.this.player.playSound(SpawnHandler.this.player.getLocation(), Sound.valueOf(SpawnHandler.this.plugin.getConfigFile().getConfig().getString("Sound_Effects.After_Warp_Sound")), 1.0F, 1.0F);
            }
        }).runTaskLater((Plugin)this.plugin, 10L);
    }


}
