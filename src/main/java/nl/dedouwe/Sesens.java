package nl.dedouwe;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Barrel;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import com.destroystokyo.paper.ParticleBuilder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import nl.dedouwe.items.Items;
import nl.dedouwe.items.SesenItem;
import nl.dedouwe.utils.ConfigUtil;
import nl.dedouwe.utils.ScrollUtil;

public class Sesens {
    public ConfigUtil config;
    public static Sesens instance;

    public Sesens(ConfigUtil c, Plugin pl) {
        instance = this;
        config = c;
    }

    /*
     * Level:
     * 1.00
     * 0-99
     */

    public void StartCycle() {
        // TODO
    }

    public void showPlayerStorage(Player p, Player toSee) {
        if (config.getPlayerStorage(toSee.getUniqueId()) == null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4This player doesn't have a storage!"));
            return;
        }
        if (!(config.getPlayerStorage(toSee.getUniqueId()).getBlock().getType() == Material.BARREL)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4This player doesn't have a storage!"));
            return;
        }
        p.openInventory(((Barrel) config.getPlayerStorage(toSee.getUniqueId()).getBlock().getState()).getInventory());
    }

    public void ShowLevel(Player p) {
        ScrollUtil.ShowProgressBar(p, (float)(GetLevel(p)-Math.floor(GetLevel(p))), 1f, "Level");
    }

    public double GetLevel(Player p) {
        return config.GetPlayerLevel(p.getUniqueId());
    }
    public void AddLevel(Player p, double xp) {
        SetLevel(p, GetLevel(p)+xp);
    }
    public void SetLevel(Player p, double lvl) {
        config.SetLevel(p.getUniqueId(), lvl);
    }

    public void SetPlayerStorage(Player p, Location l) {
        config.SetPlayerStorage(p.getUniqueId(), l);

        Barrel storage = (Barrel)l.getBlock().getState();
        storage.customName(
            Component.text("Scroll ")
            .color(NamedTextColor.GRAY)
            .decorate(TextDecoration.BOLD)
            .append(
                Component.text("Storage")
                .color(TextColor.color(153,97,0))
            )
        );

        storage.update(true);

        new ParticleBuilder(Particle.TOTEM).location(l.add(.5, 1.3, .5)).count(40).spawn();
    }

    public void Help(Player p, String item) {
        if (item == null || Items.ItemTypes.get(item) == null) {
            // NO itemMeta
            if (!p.getInventory().getItemInMainHand().hasItemMeta()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&4Hold a Sesen Item or type in the name of it in..."));
                return;
            }
            // No sesenItem
            if (!(p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                    .has(Plugin.instance.SesenType, PersistentDataType.STRING))) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&4Hold a Sesen Item or type in the name of it in..."));
                return;
            }
            p.sendMessage(SesenItem.getInstance(p.getInventory().getItemInMainHand().getItemMeta()
                    .getPersistentDataContainer().get(Plugin.instance.SesenInstance, PersistentDataType.STRING), p.getInventory().getItemInMainHand().getItemMeta()
                    .getPersistentDataContainer().get(Plugin.instance.SesenType, PersistentDataType.STRING)).GetHelp());
        } else {
            p.sendMessage(Items.ItemTypes.get(item).GetHelp());
        }
    }
}
