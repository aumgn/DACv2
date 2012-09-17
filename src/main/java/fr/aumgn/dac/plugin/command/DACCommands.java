package fr.aumgn.dac.plugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.dac.api.config.DACMessage;

public class DACCommands implements Commands {

    public void success(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.GREEN + message);
    }

    public void success(CommandSender sender, DACMessage message) {
        success(sender, message.getContent());
    }
}
