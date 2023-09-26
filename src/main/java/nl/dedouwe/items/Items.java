package nl.dedouwe.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.Plugin;

import nl.dedouwe.items.scroll.air.BlowScroll;
import nl.dedouwe.items.scroll.air.DashScroll;
import nl.dedouwe.items.scroll.dark.ShadowScroll;
import nl.dedouwe.items.scroll.dark.SoulFetcherScroll;
import nl.dedouwe.items.scroll.fire.SpreadScroll;
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
    public static List<SesenItem> Findables = new ArrayList<>();

    // TODO: for all: use LVL

    // Other
    public static final TestItem TEST_ITEM = (TestItem) AddItem(new TestItem(), false);
    public static final ShaperItem SHAPER = (ShaperItem) AddItem(new ShaperItem(), false);

    public static final StorageWarper STORAGE_WARPER = (StorageWarper) AddItem(new StorageWarper());
    public static final SummonersStone SUMMONERS_STONE = (SummonersStone) AddItem(new SummonersStone(), false);

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
    public static final SpreadScroll FLOOR_SCROLL = (SpreadScroll) AddItem(new SpreadScroll());
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
    public static final BeaconScroll BEACON_SCROLL = (BeaconScroll) AddItem(new BeaconScroll(), false);
    public static final EndScroll END_SCROLL = (EndScroll) AddItem(new EndScroll(), false);

    public static SesenItem AddItem(SesenItem sesenItem, boolean findable) {
        ItemTypes.put(sesenItem.name, sesenItem);
        if (findable) {
            Findables.add(sesenItem);
        }
        return sesenItem;
    }
    /**
     * This means it's also findable, is it not? See {@link #AddItem(SesenItem, boolean)}.
     * @param sesenItem
     * @return
     */
    public static SesenItem AddItem(SesenItem sesenItem) {
        ItemTypes.put(sesenItem.name, sesenItem);
        Findables.add(sesenItem);
        return sesenItem;
    }

    private static SesenItem onEvent (ItemStack item, Player caller) {
        if (!item.hasItemMeta()) {
            return null;
        }
        if (item.getItemMeta().getPersistentDataContainer().has(Plugin.instance.SesenType, PersistentDataType.STRING)) {
            // Has scroll use permission
            if (SesenItem.getInstance(item) instanceof Scroll && !(caller.hasPermission("sesens.canUseScroll"))) {
                caller.sendMessage(Component.text("You can't use scrolls!").color(NamedTextColor.RED));
                return null;
            }
            return SesenItem.getInstance(item);
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