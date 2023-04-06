package io.github.galaxyvn.serverguard.command;

import io.github.galaxyvn.serverguard.ServerGuard;
import io.github.galaxyvn.serverguard.manager.FileManager;
import io.github.galaxyvn.serverguard.util.HexUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class ServerGuardCommand implements CommandExecutor {

    static FileConfiguration message = FileManager.getFileConfig(FileManager.Files.MESSAGES);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("serverguard.help")) {
                sender.sendMessage(HexUtils.colorify(message.getString("prefix") + message.getString("no-permission")));
                return false;
            }
            sender.sendMessage(Component.text(HexUtils.colorify(message.getString("usage"))));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("serverguard.admin")) {
                sender.sendMessage(HexUtils.colorify(message.getString("prefix") + message.getString("no-permission")));
                return false;
            }

            sender.sendMessage(Component.text(HexUtils.colorify(message.getString("prefix" + "&7 Đang tải lại..."))));
            try {
                FileManager.setup(ServerGuard.getPlugin());
                sender.sendMessage(Component.text(HexUtils.colorify(message.getString("prefix" + "&7 Đã tải lại thành công!"))));
            } catch (Exception ex) {
                ex.printStackTrace();
                sender.sendMessage(Component.text(HexUtils.colorify(message.getString("prefix" + "&7 Đã xảy ra lỗi! Vui lòng kiểm tra lại...."))));
            }
        }
        return false;
    }
}
