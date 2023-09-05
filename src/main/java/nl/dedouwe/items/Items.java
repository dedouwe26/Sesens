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

import nl.dedouwe.items.scroll.air.BlowScroll;
import nl.dedouwe.items.scroll.air.DashScroll;
import nl.dedouwe.items.scroll.dark.ShadowScroll;
import nl.dedouwe.items.scroll.dark.SoulFetcherScroll;
import nl.dedouwe.items.scroll.fire.FloorScroll;
import nl.dedouwe.items.scroll.fire.MeteorScroll;
import nl.dedouwe.items.scroll.ground.LayerScroll;
import nl.dedouwe.items.scroll.ground.ThrowScroll;
import nl.dedouwe.items.scroll.light.SunnyScroll;
import nl.dedouwe.items.scroll.light.ThorScroll;
import nl.dedouwe.items.scroll.nature.GrowScroll;
import nl.dedouwe.items.scroll.nature.StingScroll;
import nl.dedouwe.items.scroll.unobtainable.BeaconScroll;
import nl.dedouwe.items.scroll.unobtainable.EndScroll;
import nl.dedouwe.items.scroll.water.ShieldScroll;
import nl.dedouwe.items.scroll.water.TsunamiScroll;

public class Items {
    public static HashMap<String, SesenItem> ItemTypes = new HashMap<>();

    // Other
    public static final TestItem TEST_ITEM = (TestItem) AddItem(new TestItem());
    public static final ShaperItem SHAPER = (ShaperItem) AddItem(new ShaperItem());

    public static final StorageWarper STORAGE_WARPER = (StorageWarper) AddItem(new StorageWarper());

    // Nature
    public static final StingScroll STING_SCROLL = (StingScroll) AddItem(new StingScroll());
    public static final GrowScroll GROW_SCROLL = (GrowScroll) AddItem(new GrowScroll());
    // Dark
    public static final ShadowScroll SHADOW_SCROLL = (ShadowScroll) AddItem(new ShadowScroll());
    public static final SoulFetcherScroll SOUL_FETCHER_SCROLL = (SoulFetcherScroll) AddItem(new SoulFetcherScroll());
    // Light
    public static final ThorScroll THOR_SCROLL = (ThorScroll) AddItem(new ThorScroll());
    public static final SunnyScroll SUNNY_SCROLL = (SunnyScroll) AddItem(new SunnyScroll());
    // Fire
    public static final FloorScroll FLOOR_SCROLL = (FloorScroll) AddItem(new FloorScroll());
    public static final MeteorScroll METEOR_SCROLL = (MeteorScroll) AddItem(new MeteorScroll());
    // Ground
    public static final LayerScroll LAYER_SCROLL = (LayerScroll) AddItem(new LayerScroll());
    public static final ThrowScroll THROW_SCROLL = (ThrowScroll) AddItem(new ThrowScroll());
    // Air
    public static final BlowScroll BLOW_SCROLL = (BlowScroll) AddItem(new BlowScroll());
    public static final DashScroll DASH_SCROLL = (DashScroll) AddItem(new DashScroll());
    // Water
    public static final ShieldScroll SHIELD_SCROLL = (ShieldScroll) AddItem(new ShieldScroll());
    public static final TsunamiScroll TSUNAMI_SCROLL = (TsunamiScroll) AddItem(new TsunamiScroll());
    // Unobtainable
    public static final BeaconScroll BEACON_SCROLL = (BeaconScroll) AddItem(new BeaconScroll());
    public static final EndScroll END_SCROLL = (EndScroll) AddItem(new EndScroll());

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