package io.github.galaxyvn.serverguard;

import io.github.galaxyvn.serverguard.command.ServerGuardCommand;
import io.github.galaxyvn.serverguard.listener.PlayerDropListener;
import io.github.galaxyvn.serverguard.listener.PlayerJoinListener;
import io.github.galaxyvn.serverguard.manager.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ServerGuard extends JavaPlugin {
    private static ServerGuard plugin;
    private static Logger logger = Logger.getLogger("ServerGuard");


    @Override
    public void onEnable() {
        plugin = this;

        FileManager.setup(this);

        getCommand("serverguard").setExecutor(new ServerGuardCommand());

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ServerGuard getPlugin() {
        return plugin;
    }

    public static Logger getPluginLogger() {
        return logger;
    }
}
