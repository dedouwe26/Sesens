package nl.dedouwe;

import org.bukkit.Location;
import org.bukkit.Nameable;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import nl.dedouwe.items.Items;
import nl.dedouwe.utils.ConfigUtil;

public class Sesens {
    private ConfigUtil config;
    public static Sesens instance;
    public Sesens(ConfigUtil c, Plugin pl) {
        instance = this;
        config = c;
    }
    public void StartCycle () {
        // TODO
    }
    public void showPlayerStorage (Player p, Player toSee) {
        if (config.getPlayerStorage(toSee.getUniqueId()) == null ) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4This player doesn't have a storage!"));
            return;
        }
        if (!(config.getPlayerStorage(toSee.getUniqueId()).getBlock() instanceof Container)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4This player doesn't have a storage!"));
            return;
        }
        p.openInventory(((Container)config.getPlayerStorage(toSee.getUniqueId()).getBlock()).getInventory());
    }

    public double GetLevel (Player p) {
        return config.GetPlayerLevel(p.getUniqueId());
    }
    public void SetLevel (Player p, double lvl) {
        config.SetLevel(p.getUniqueId(), lvl);
    }
    public void SetPlayerStorage(Player p, Location l) {
        config.SetPlayerStorage(p.getUniqueId(), l);

        p.sendMessage("null");

        ((Nameable)l.getBlock().getState()).customName(
            Component.text("Scroll ")
            .color(NamedTextColor.GRAY)
            .decorate(TextDecoration.BOLD)
            .append(
                Component.text("Storage")
                .color(TextColor.color(153,97,0))
            )
        );
    }
    public void GiveItem(Player p, String name) {
        if (Items.Items.get(name) == null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You must define a real Sesen Item..."));
            return;
        }
        p.getInventory().addItem(Items.Items.get(name).item);
    }
    public void Help(Player p, String item) {
        if (Items.Items.get(item) == null) {
            // NO itemMeta
            if (!p.getInventory().getItemInMainHand().hasItemMeta()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Hold a Sesen Item or type in the name of it in..."));
                return;
            }
            // No sesenItem
            if (p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Plugin.instance.key, PersistentDataType.STRING)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Hold a Sesen Item or type in the name of it in..."));
                return;
            }
            p.sendMessage(Items.Items.get(p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(Plugin.instance.key, PersistentDataType.STRING)).GetHelp());
        } else {
            p.sendMessage(Items.Items.get(item).GetHelp());
        }
    }
}
