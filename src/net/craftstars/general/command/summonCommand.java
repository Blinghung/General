
package net.craftstars.general.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.craftstars.general.CommandBase;
import net.craftstars.general.General;
import net.craftstars.general.util.Messaging;
import net.craftstars.general.util.Teleport;
import net.craftstars.general.util.Toolbox;

public class summonCommand extends CommandBase {

    @Override
    public boolean fromPlayer(General plugin, Player sender, Command command, String commandLabel,
            String[] args) {
        if(Toolbox.lacksPermission(plugin, sender, "general.teleport.other")) return true;
        if(args.length < 1) return Toolbox.USAGE;

        Player player = Toolbox.getPlayer(args[0], sender);
        if(player == null) return true;

        if(player.equals(sender)) {
            Messaging.send(sender, "&rose;You cannot summon yourself.");
            return true;
        }

        Teleport.teleportPlayerToPlayer(player, sender);
        Messaging.send(sender, "Teleported " + player.getName() + " to you!");
        return true;
    }

    @Override
    public boolean fromConsole(General plugin, CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Messaging.send(sender,"This command is not useable from the console.");
        return true;
    }

}
