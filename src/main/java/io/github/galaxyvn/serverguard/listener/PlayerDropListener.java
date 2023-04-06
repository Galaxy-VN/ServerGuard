package io.github.galaxyvn.serverguard.listener;

import io.github.galaxyvn.serverguard.ServerGuard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PlayerDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        File file = new File(ServerGuard.getPlugin().getDataFolder(), "logs/" + player.getName() + ".yml");
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            Files.write(Paths.get(file.getPath()), ("[" + currentTime.format(formatter) + "] " + player.getName() + " drop item: " + event.getItemDrop().getItemStack().getType().name() + " x" + event.getItemDrop().getItemStack().getAmount() + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
