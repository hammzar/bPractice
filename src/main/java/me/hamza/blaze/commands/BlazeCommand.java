package me.hamza.blaze.commands;

import me.hamza.blaze.Blaze;
import me.hamza.blaze.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
public class BlazeCommand implements CommandExecutor {

    private String author = Blaze.getINSTANCE().getDescription().getAuthors().get(0).toUpperCase();
    private String version = Blaze.getINSTANCE().getDescription().getVersion();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(CC.translate(""));
        sender.sendMessage(CC.translate("&4&lBlaze Practice &7» &fInformation"));
        sender.sendMessage(CC.translate("&f&m----------------------------"));
        sender.sendMessage(CC.translate("&7Author: &f" + author));
        sender.sendMessage(CC.translate("&7Version: &f" + version));
        sender.sendMessage(CC.translate("&7Discord: &fdiscord.gg/hammzar"));
        sender.sendMessage(CC.translate("&f&m----------------------------"));
        sender.sendMessage(CC.translate(""));

        return true;
    }

}
