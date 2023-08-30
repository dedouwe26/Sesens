package nl.dedouwe.items;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import net.md_5.bungee.api.ChatColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.items.scroll.light.SunnyScroll;
import nl.dedouwe.items.scroll.nature.StingScroll;

public class Items {
    public static HashMap<String, SesenItem> ItemTypes = new HashMap<>();

    // Other
    public static final TestItem TEST_ITEM = (TestItem) AddItem(new TestItem());
    public static final ShaperItem SHAPER = (ShaperItem) AddItem(new ShaperItem());

    public static final StorageWarper STORAGE_WARPER = (StorageWarper) AddItem(new StorageWarper()); // TODO: add class

    // Nature
    public static final StingScroll STING_SCROLL = (StingScroll) AddItem(new StingScroll());
    // Dark
    // Light
    public static final SunnyScroll SUNNY_SCROLL = (SunnyScroll) AddItem(new SunnyScroll());
    // Fire
    // Ground
    // Air
    // Water
    // Unobtainable

    public static SesenItem AddItem(SesenItem sesenItem) {
        ItemTypes.put(sesenItem.name, sesenItem);
        return sesenItem;
    }

    private static SesenItem onEvent (ItemStack item, Player caller) {
        if (!item.hasItemMeta()) {
            return null;
        }
        if (item.getItemMeta().getPersistentDataContainer().has(Plugin.instance.SesenType, PersistentDataType.STRING)) {
            // Has scroll use permission
            if (SesenItem.getInstance(item.getItemMeta().getPersistentDataContainer().get(Plugin.instance.SesenInstance, PersistentDataType.STRING), item.getItemMeta().getPersistentDataContainer().get(Plugin.instance.SesenType, PersistentDataType.STRING)) instanceof Scroll && !(caller.hasPermission("sesens.canUseScroll"))) {
                caller.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You can't use scrolls!"));
                return null;
            }
            return SesenItem.getInstance(item.getItemMeta().getPersistentDataContainer().get(Plugin.instance.SesenInstance, PersistentDataType.STRING), item.getItemMeta().getPersistentDataContainer().get(Plugin.instance.SesenType, PersistentDataType.STRING));
        }
        return null;
    }

    public static void onDeactivate(ItemStack item, PlayerInteractEvent e) {
        SesenItem s = onEvent(item, e.getPlayer());
        if (s==null) {
            return;
        } else {
            s.onDeactivate(e);
        }
    }

    public static void onUse( ItemStack item, PlayerInteractEvent e) {
        SesenItem s = onEvent(item, e.getPlayer());
        if (s==null) {
            return;
        } else {
            s.onUse(e);
        }
    }

    public static void onActivate(ItemStack item, PlayerInteractEvent e) {
        SesenItem s = onEvent(item, e.getPlayer());
        if (s==null) {
            return;
        } else {
            s.onActivate(e);
        }
    }

    public static void onHit(ItemStack item, PlayerInteractEvent e) {
        SesenItem s = onEvent(item, e.getPlayer());
        if (s==null) {
            return;
        } else {
            s.onHit(e);
        }
    }

    public static void onDrop(ItemStack item, PlayerDropItemEvent e) {
        SesenItem s = onEvent(item, e.getPlayer());
        if (s==null) {
            return;
        } else {
            s.onDrop(e);
        }
    }

    public static void onActivate(ItemStack item, EntityDamageByEntityEvent e) {
        SesenItem s = onEvent(item, (Player)e.getDamager());
        if (s==null) {
            return;
        } else {
            s.onActivate(e);
        }
    }

    public static void onHit(ItemStack item, EntityDamageByEntityEvent e) {
        SesenItem s = onEvent(item, (Player)e.getDamager());
        if (s==null) {
            return;
        } else {
            s.onHit(e);
        }
    }
}