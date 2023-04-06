package io.github.galaxyvn.serverguard.manager;

import java.io.File;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileManager {

    private static HashMap<Files, File> file = new HashMap<Files, File>();
    private static HashMap<Files, FileConfiguration> configuration = new HashMap<Files, FileConfiguration>();

    public static void setup(Plugin plugin) {
        for(Files f : Files.values()) {
            File fl = new File(plugin.getDataFolder(), f.getLocation());
            if(!fl.exists()) {
                fl.getParentFile().mkdirs();
                plugin.saveResource(f.getLocation(), false);
            }
            FileConfiguration config = new YamlConfiguration();
            try {
                config.load(fl);
            } catch(Exception ex) {

            }
            file.put(f, fl);
            configuration.put(f, config);
        }
    }

    public static FileConfiguration getFileConfig(Files f) {
        return configuration.get(f);
    }

    public static void saveFileConfig(FileConfiguration data, Files f) {
        try {
            data.save(file.get(f));
        } catch(Exception ex) {

        }
    }



    public enum Files {

        CONFIG("config.yml"),
        MESSAGES("messages.yml");

        private String location;

        Files(String l) {
            this.location = l;
        }

        public String getLocation() {
            return this.location;
        }

    }

}
