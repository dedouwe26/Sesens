package nl.dedouwe;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Barrel;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.structure.Structure;
import com.destroystokyo.paper.ParticleBuilder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.ChatColor;
import nl.dedouwe.items.Items;
import nl.dedouwe.items.SesenItem;
import nl.dedouwe.utils.ConfigUtil;

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

     public static void ShowProgressBar(Player p, float amount, float max, String name) {
        float progress = (amount/max)*10;

        p.sendActionBar(Component
            .text(name)
            .color(NamedTextColor.AQUA)
            .append(Component
                .text(" [")
                .color(NamedTextColor.WHITE)
            )
            .append(Component.text("=").color(progress >= 1 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 2 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 3 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 4 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 5 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 6 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 7 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 8 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 9 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 10 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component
                .text("] ")
                .color(NamedTextColor.WHITE)
            )
            .append(Component
                .text(max)
                .color(NamedTextColor.AQUA)
            )
        );
    }

    public Structure GetStructure (String path) {
        Structure s = Bukkit.getStructureManager().createStructure();
        try {
            s = Bukkit.getStructureManager().loadStructure(getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            Plugin.instance.getLogger().log(Level.SEVERE, "File could not be read from!");
        }
        return s;
    }

    public void SpawnTreasure (Location l, Random r) {
        Structure s = GetStructure("SesensTreasure.nbt");
        s.place(l, false, StructureRotation.NONE, Mirror.NONE, 0, 1, r);
        Plugin.instance.getLogger().info("There is a Treasure at " + l.toString());
        l.add(2, 3, 1);
        if (l.getBlock().getType()!=Material.BARREL) {
            Plugin.instance.getLogger().info("Barrel not on the estimated place.");
            return;
        }
        Barrel b = ((Barrel)l.getBlock().getState());
        for (int j = 0; j < 4; j++) {
            b.getInventory().setItem(r.nextInt(26), Items.Findables.get(r.nextInt(Items.Findables.size()-1)).item);
        }
        l.getBlock().getState().update(true);
    }

    public void SpawnTomb (Location l, Random r) {
        Structure s = GetStructure("SesensTomb.nbt");
        s.place(l, false, StructureRotation.NONE, Mirror.NONE, 0, 1, r);
        Plugin.instance.getLogger().info("There is a Tomb at " + l.toString());
        l.add(1, 5, 8);
        if (l.getBlock().getType()!=Material.BARREL) {
            Plugin.instance.getLogger().info("Barrel not on the estimated place.");
            return;
        }
        Barrel b = ((Barrel)l.getBlock().getState());
        b.getInventory().setItem(13, SesenItem.CreateInstance(Items.SUMMONERS_STONE.name).item);
        l.getBlock().getState().update(true);
    }

    public void StartCycle(World w) {
        Bukkit.getServer().showTitle(Title.title(Component.text("Start the hunt,").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD), Component.text("The Treasures are on the radar!").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD)));
        Random r = new Random();
        for (int i = 0; i < 7; i++) {
            Location l = Bukkit.getWorlds().get(0).getHighestBlockAt(r.nextInt(-300, 300), r.nextInt(-300, 300)).getLocation().subtract(0, 2, 0);
            SpawnTreasure(l.clone(), new Random());
            config.SaveTreasure(l, i);
        }
        Location l = Bukkit.getWorlds().get(0).getHighestBlockAt(r.nextInt(-400, 400), r.nextInt(-400, 400)).getLocation().subtract(0, 2, 0);
        SpawnTomb(l.clone(), new Random());
        config.SaveTomb(l);
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
        ShowProgressBar(p, (float)(GetLevel(p)-Math.floor(GetLevel(p))), 1f, "Level");
    }

    public double GetLevel(Player p) {
        return config.GetPlayerLevel(p.getUniqueId());
    }
    public void AddLevel(Player p, double xp) {
        SetLevel(p, (double) Math.round((GetLevel(p)+xp) * 100) / 100);
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
            if (!(p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Plugin.instance.SesenType, PersistentDataType.STRING))) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&4Hold a Sesen Item or type in the name of it in..."));
                return;
            }
            p.sendMessage(SesenItem.getInstance(p.getInventory().getItemInMainHand()).GetHelp());
        } else {
            p.sendMessage(Items.ItemTypes.get(item).GetHelp());
        }
    }
}
