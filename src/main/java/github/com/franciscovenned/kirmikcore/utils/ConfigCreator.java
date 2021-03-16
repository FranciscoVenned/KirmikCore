package github.com.franciscovenned.kirmikcore.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigCreator {
    private File config;

    private FileConfiguration customConfig;

    private final String fileName;

    public ConfigCreator(String fileName) {
        this.fileName = fileName;
    }

    public void createConfig() {
        this.config = new File(Utilities.getInstance().getDataFolder(), this.fileName);
        if (!this.config.exists()) {
            this.config.getParentFile().mkdirs();
            Utilities.getInstance().saveResource(this.fileName, false);
        }
        this.customConfig = (FileConfiguration)new YamlConfiguration();
        try {
            this.customConfig.load(this.config);
        } catch (InvalidConfigurationException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void reloadConfig() {
        try {
            this.customConfig.load(this.config);
        } catch (IOException|InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            this.customConfig.save(this.config);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.customConfig;
    }

    public File getFile() {
        return this.config;
    }

    public String getFileName() {
        return this.fileName;
    }
}