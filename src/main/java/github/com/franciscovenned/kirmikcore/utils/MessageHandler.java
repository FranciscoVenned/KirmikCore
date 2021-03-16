package github.com.franciscovenned.kirmikcore.utils;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageHandler {
    private final KirmikCore plugin;

    private final FileConfiguration messagesConfig;

    private final String configName;

    public MessageHandler(KirmikCore plugin, FileConfiguration messagesConfig) {
        this.plugin = plugin;
        this.messagesConfig = messagesConfig;
        this.configName = plugin.getMessagesFile().getFileName();
    }

    public String string(String message, String fallbackMessage) {
        if (this.messagesConfig.getString(message) == null) {
            getErrorMessage(message);
            return getPrefix() + fallbackMessage;
        }
        return getPrefix() + this.messagesConfig.getString(message);
    }

    public String console(String message, String fallbackMessage) {
        if (this.messagesConfig.getString(message) == null) {
            getErrorMessage(message);
            return fallbackMessage;
        }
        return this.messagesConfig.getString(message);
    }

    public String getPrefix() {
        if (this.messagesConfig.getString("Prefix") == null) {
            getErrorMessage("Prefix");
            return "&7&l[&aKirmikCore&7&l] ";
        }
        return this.messagesConfig.getString("Prefix") + " ";
    }

    private void getErrorMessage(String message) {
        Utilities.logInfo(true, new String[] { "There was an error getting the " + message + " message from the " + this.configName + ".", "I have set a fallback message to take it's place until the issue is fixed.", "To resolve this, please locate " + message + " in the " + this.configName + " and fix the issue." });
    }
}