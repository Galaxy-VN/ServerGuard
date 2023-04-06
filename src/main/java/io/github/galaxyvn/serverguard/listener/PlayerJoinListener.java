package io.github.galaxyvn.serverguard.listener;

import io.github.galaxyvn.serverguard.ServerGuard;
import io.github.galaxyvn.serverguard.manager.FileManager;
import io.github.galaxyvn.serverguard.util.HexUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class PlayerJoinListener implements Listener {

    static Logger logger = ServerGuard.getPluginLogger();
    static FileConfiguration config = FileManager.getFileConfig(FileManager.Files.CONFIG);
    static FileConfiguration message = FileManager.getFileConfig(FileManager.Files.MESSAGES);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws Exception {
        Player player = event.getPlayer();

        // Check if not in block country
        blockCountry(player);
    }

    public static void blockCountry(Player player) throws Exception {
        String ipAddress = player.getAddress().getHostName();

        URL url = new URL("https://www.cloudflare.com/cdn-cgi/trace?ip=" + ipAddress);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        String countryCode = null;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("loc=")) {
                countryCode = inputLine.substring(4);
                break;
            }
        }
        in.close();

        for (String countryName : config.getStringList("block-country")) {
            if (countryName.equals(countryCode)) {
                player.kick(Component.text(HexUtils.colorify(message.getString("kick-block-country"))));
                return;
            } else continue;
        }
    }
}
