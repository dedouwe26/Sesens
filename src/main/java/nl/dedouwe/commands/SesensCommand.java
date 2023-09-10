package nl.dedouwe.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;
import nl.dedouwe.Sesens;
import nl.dedouwe.items.Items;
import nl.dedouwe.items.SesenItem;

public class SesensCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be a player");
            return true;
        }
        if (args.length > 3 || args.length <= 0)
            return false;
        if (!(sender.hasPermission("sesens.command.admin"))
                && Arrays.asList("startcycle", "give", "setlvl").contains(args[0])) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You can't use this sub-command!"));
            return true;
        }
        switch (args[0]) {
            case "storage":
                if (!(sender.hasPermission("sesens.command.admin"))) {
                    Sesens.instance.showPlayerStorage((Player) sender, (Player) sender);
                } else {
                    if (args.length != 2) {
                        return false;
                    }
                    if (Bukkit.getPlayer(args[1]) == null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&4You have admin privileges, so You must define real online player..."));
                    } else {
                        Sesens.instance.showPlayerStorage((Player) sender, Bukkit.getPlayer(args[1]));
                    }
                }
                break;
            case "lvl":
                if (args.length == 2 && Bukkit.getPlayer(args[1]) != null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&b" + ((TextComponent) Bukkit.getPlayer(args[1]).name()).content()
                                    + "&a's current level is &r&e&l"
                                    + Sesens.instance.GetLevel(Bukkit.getPlayer(args[1]))));
                } else {
                    Sesens.instance.ShowLevel((Player)sender);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&aYour current level is &r&e&l" + Sesens.instance.GetLevel((Player) sender)));
                }
                break;
            case "startcycle":
                Sesens.instance.StartCycle();
                break;
            case "give":
                if (args.length == 2)
                    if (Items.ItemTypes.containsKey(args[1])) {
                        SesenItem.CreateInstance(args[1]).GiveTo((Player)sender);
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&4Please put a valid item..."));
                    }
                break;
            case "setlvl":
                if (args.length != 3) {
                    return false;
                }
                if (args[2]=="" && Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "&4You must define real online player..."));
                } else {
                    Sesens.instance.SetLevel(Bukkit.getPlayer(args[1]), Double.parseDouble(args[2]));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aLevel is set to &l&e" + args[2]));
                }
                break;
            case "help":
                if (args.length == 2) {
                    Sesens.instance.Help((Player) sender, args[1]);
                } else {
                    Sesens.instance.Help((Player) sender, null);
                }
                break;
            default:
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4" + command.getUsage()));
                return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabs = new ArrayList<>();

        if (args.length == 1) {
            tabs.add("storage");
            tabs.add("lvl");
            tabs.add("help");
            if (sender.hasPermission("sesens.command.admin")) {
                tabs.add("setlvl");
                tabs.add("give");
                tabs.add("startcycle");
            }
        } else if (args.length == 2) {
            switch (args[0]) {
                case "storage":
                    if (sender.hasPermission("sesens.command.admin"))
                        // Returns all players
                        return null;
                    break;
                case "lvl":
                    // Returns all players to show their level
                    return null;
                case "setlvl":
                    if (sender.hasPermission("sesens.command.admin"))
                        return null;
                    break;
                case "give":
                    if (sender.hasPermission("sesens.command.admin"))
                        tabs.addAll(Items.ItemTypes.keySet());
                    break;
                case "help":
                    tabs.addAll(Items.ItemTypes.keySet());
                    break;
                default:
                    break;
            }
        } else if (args.length == 3) {
            if (sender.hasPermission("sesens.command.admin")) {
                tabs.add("1.0");
                tabs.add("10.0");
                tabs.add("100.0");
            }
        }
        return tabs;

    }

}
