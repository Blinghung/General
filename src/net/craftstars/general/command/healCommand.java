
package net.craftstars.general.command;

import net.craftstars.general.CommandBase;
import net.craftstars.general.General;
import net.craftstars.general.util.Messaging;
import net.craftstars.general.util.Toolbox;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class healCommand extends CommandBase {

    @Override
    public boolean fromConsole(General plugin, CommandSender sender, Command command,
            String commandLabel, String[] args) {
        double amount = 10;
        Player who = null;
        switch(args.length) {
        case 1: // /heal <amount> OR /heal <player>
            who = Toolbox.getPlayer(args[0], sender);
        break;
        case 2: // /heal <player> <amount>
            try {
                amount = Double.valueOf(args[1]);
                who = Toolbox.getPlayer(args[0], sender);
            } catch(NumberFormatException x) {
                Messaging.send(sender, "&rose;Invalid number.");
                return true;
            }
        break;
        default:
            return Toolbox.USAGE;
        }
        if(who == null) return true;
        doHeal(who, amount);
        Messaging.send(sender, "&e" + who.getName() + "&f has been "
                + (amount < 0 ? "hurt" : "healed") + " by &e" + Math.abs(amount) + "&f hearts.");
        return true;
    }

    @Override
    public boolean fromPlayer(General plugin, Player sender, Command command, String commandLabel,
            String[] args) {
        if(Toolbox.lacksPermission(plugin, sender, "general.heal")) return true;
        double amount = 10;
        Player who = null;
        switch(args.length) {
        case 0: // /heal
            who = sender;
        break;
        case 1: // /heal <amount> OR /heal <player>
            try {
                who = sender;
                amount = Double.valueOf(args[0]);
            } catch(NumberFormatException x) {
                who = Toolbox.getPlayer(args[0], sender);
            }
        break;
        case 2: // /heal <player> <amount>
            try {
                amount = Double.valueOf(args[1]);
                who = Toolbox.getPlayer(args[0], sender);
            } catch(NumberFormatException x) {
                Messaging.send(sender, "&rose;Invalid number.");
                return true;
            }
        break;
        default:
            return Toolbox.USAGE;
        }
        if(who == null) return true;
        doHeal(who, amount);
        if(!sender.getName().equalsIgnoreCase(who.getName())) {
            Messaging.send(sender, "&e" + who.getName() + "&f has been "
                    + (amount < 0 ? "hurt" : "healed") + " by &e" + Math.abs(amount) + "&f hearts.");
        }
        return true;
    }

    private void doHeal(Player who, double amount) {
        int hp = who.getHealth();
        amount *= 2;
        hp += amount;
        if(hp > 20) hp = 20;
        else if(hp < 0) hp = 0;
        amount = hp - who.getHealth();
        amount /= 2.0;
        who.setHealth(hp);
        Messaging.send(who, "&fYou are " + (amount < 0 ? "hurt" : "healed") + " by &e"
                + Math.abs(amount) + "&f hearts.");
    }

}
