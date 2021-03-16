package github.com.franciscovenned.kirmikcore;

import github.com.franciscovenned.kirmikcore.commands.*;
import github.com.franciscovenned.kirmikcore.events.*;
import github.com.franciscovenned.kirmikcore.utils.ConfigCreator;
import github.com.franciscovenned.kirmikcore.utils.SpawnHandler;
import github.com.franciscovenned.kirmikcore.utils.Utilities;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public class KirmikCore extends JavaPlugin {

    private static KirmikCore instance;
    private KirmikCore plugin;
    private SpawnHandler spawnHandler;
    private static Economy econ = null;
    private final UUID uuid = null;
    private final ConfigCreator configFile = new ConfigCreator("config.yml");
    private final ConfigCreator messagesFile = new ConfigCreator("messages.yml");
    private final ConfigCreator warpsFile = new ConfigCreator("warps.yml");
    private final ConfigCreator homesFile = new ConfigCreator("homes.yml");
    private final ConfigCreator playerslist = new ConfigCreator("playerslist.yml");
    private final ConfigCreator entidades = new ConfigCreator("entidades.yml");
    private HashMap<OfflinePlayer, Integer> limitshome = new HashMap<OfflinePlayer, Integer>();
    private HashMap<UUID, String> playerDeath = new HashMap<>();
    private List<UUID> jugadoresNuevos = new ArrayList<>();
    private Map<Player, Player> jugadorSign = new HashMap<>();

    @Override
    public void onEnable() {
        this.plugin = this;
        initialSetup();
        setupConfigs();
        setupCommands();
        setupEconomy();
        setupEvent();

        Utilities.logInfo(true, "Sistemas activados con exito!");


    }

    @Override
    public void onDisable() {

        onReload();
        Utilities.logInfo(true, "Los sistemas fueron detenidos");

    }


    public void onReload() {
        getConfigFile().reloadConfig();
        getMessagesFile().reloadConfig();
        getWarpsFile().reloadConfig();
        getHomesFile().reloadConfig();
        getEntidades().createConfig();

    }

    private void initialSetup() {
        Utilities.setInstance(this);
        if (Bukkit.getPluginManager().getPlugin("Vault") == null)
            Utilities.logWarning(true, new String[]{"KirmikCore requiere el plugin de Vault, lo puedes encontrar en spigot."});
        int bstatsPluginId = 7492;
    }

    private void setupEvent(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerLeave(this), this);
        pluginManager.registerEvents(new DeathMessage(this), this);
        pluginManager.registerEvents(new JoinPlayersSpawn(this, spawnHandler), this);
        pluginManager.registerEvents(new DeathSpawn(this, spawnHandler), this);
        pluginManager.registerEvents(new KillEntity(this), this);
        pluginManager.registerEvents(new CommandInBlock(this), this);
//        pluginManager.registerEvents(new TPA(this), this);
    }

    private void setupCommands() {
        Utilities.logInfo(true, "Registrando los comandos...");
        Utilities.setCommand().put("kirmikcore", new MainCommand(this.plugin));
        Utilities.setCommand().put("setwarp", new SetWarp(this.plugin));
        Utilities.setCommand().put("warp", new Warp(this.plugin));
        Utilities.setCommand().put("delwarp", new RemoveWarp(this.plugin));
        Utilities.setCommand().put("listwarps", new WarpList(this.plugin));
        Utilities.setCommand().put("clearwarps", new ClearWarps(this.plugin));
        Utilities.setCommand().put("sethome", new SetHomeCommand(this.plugin));
        Utilities.setCommand().put("home", new Home(this.plugin));
        Utilities.setCommand().put("delhome", new RemoveHome(this.plugin));
        Utilities.setCommand().put("changeitem", new ChangeItemCommand());
        Utilities.setCommand().put("setspawn", new SetSpawn(this, spawnHandler));
        Utilities.setCommand().put("spawn", new SpawnCommand(this, spawnHandler));
        Utilities.setCommand().put("survival", new Survival(this));
        Utilities.setCommand().put("creative", new Creative(this));
        Utilities.setCommand().put("heal", new HealCommand(this));
        Utilities.setCommand().put("fly", new FlyCommand());
        Utilities.setCommand().put("hat", new HatCommand());
        Utilities.setCommand().put("invsee", new Invsee());
        Utilities.setCommand().put("enderchest", new EnderChestView());
        Utilities.setCommand().put("id", new IDCommand());
        Utilities.setCommand().put("commandblock", new CommandBlock(this));
        Utilities.setCommand().put("fixall", new RepairAll(this));
        Utilities.setCommand().put("homelist", new HomeList(this));
//        Utilities.setCommand().put("tpa", new TPA(this));
//        Utilities.setCommand().put("tpaccept", new TPA(this));
//        Utilities.setCommand().put("tpdeny", new TPA(this));
        Utilities.registerCommands();
        Utilities.logInfo(true, "Comandos registrados: " + Utilities.setCommand().size());
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        this.econ = (Economy) rsp.getProvider();
        return (this.econ != null);
    }

    private void setupConfigs() {
        getConfigFile().createConfig();
        getMessagesFile().createConfig();
        getWarpsFile().createConfig();
        getHomesFile().createConfig();
        getPlayerslist().createConfig();
        getEntidades().createConfig();
        getWarpsFile().getConfig().options().header(" -------------------------------------------------------------------------------------------\n \n Welcome to OmegaWarps warp file.\n \n This file stores all the warps that are created on the server.\n It will include the player who created it, the location & who it was created for.\n \n -------------------------------------------------------------------------------------------");
    }


    public ConfigCreator getConfigFile() {
        return this.configFile;
    }

    public ConfigCreator getMessagesFile() {
        return this.messagesFile;
    }

    public ConfigCreator getWarpsFile() {
        return this.warpsFile;
    }

    public ConfigCreator getHomesFile() {
        return homesFile;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static KirmikCore getInstance() {
        return instance;
    }


    public HashMap<OfflinePlayer, Integer> getLimitshome() {
        return limitshome;
    }

    public void setLimitshome(HashMap<OfflinePlayer, Integer> limitshome) {
        this.limitshome = limitshome;
    }

    public ConfigCreator getPlayerslist() {
        return playerslist;
    }

    public HashMap<UUID, String> getPlayerDeath() {
        return playerDeath;
    }

    public List<UUID> getJugadoresNuevos() {
        return jugadoresNuevos;
    }


    public ConfigCreator getEntidades() {
        return entidades;
    }

    public Map<Player, Player> getJugadorSign() {
        return jugadorSign;
    }
}
