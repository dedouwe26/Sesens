package nl.dedouwe.items;

import java.util.HashMap;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.events.ItemEvent;

public class Items {
    public static HashMap<String, SesenItem> Items = new HashMap<>();

    public static final StorageWarper STORAGE_WARPER = (StorageWarper)AddItem(new StorageWarper());
    public static final SunnyScroll SUNNY_SCROLL = (SunnyScroll)AddItem(new SunnyScroll());
    public static final StingScroll STING_SCROLL = (StingScroll)AddItem(new StingScroll());

    

    public static SesenItem AddItem(SesenItem sesenItem) {
        Items.put(sesenItem.name, sesenItem);
        return sesenItem;
    }

    public static void onEvent (ItemStack item, PlayerEvent e, ItemEvent type) {
        e.getPlayer().sendMessage("ok-1");

        if (!item.hasItemMeta()) {
            return;
        }
        if (item.getItemMeta().getPersistentDataContainer().has(Plugin.instance.key, PersistentDataType.STRING)) {
            // Has scroll use permission
            if (Items.get(item.getItemMeta().getPersistentDataContainer().get(Plugin.instance.key, PersistentDataType.STRING)) instanceof Scroll && !(e.getPlayer().hasPermission("sesens.canUseScroll"))) {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You can't use scrolls!"));
                return;
            }

            e.getPlayer().sendMessage("ok-2");
            
            Items.get(item.getItemMeta().getPersistentDataContainer().get(Plugin.instance.key, PersistentDataType.STRING)).onEvent(e, type);
        }
    }
}