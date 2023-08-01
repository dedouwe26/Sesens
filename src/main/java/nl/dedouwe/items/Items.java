package nl.dedouwe.items;

import java.util.HashMap;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.ChatColor;
import nl.dedouwe.events.ItemEvent;

public class Items {
    public static HashMap<String, SesenItem> Items = new HashMap<>();
    public static HashMap<ItemStack, SesenItem> ItemsByItemStack = new HashMap<>();

    public static final StorageWarper STORAGE_WARPER = (StorageWarper)AddItem(new StorageWarper());
    public static final SunnyScroll SUNNY_SCROLL = (SunnyScroll)AddItem(new SunnyScroll());
    public static final StingScroll STING_SCROLL = (StingScroll)AddItem(new StingScroll());

    

    public static SesenItem AddItem(SesenItem sesenItem) {
        Items.put(sesenItem.name, sesenItem);
        ItemsByItemStack.put(sesenItem.item, sesenItem);
        return sesenItem;
    }

    public static SesenItem GetItem(String name) {
        return Items.get(name);
    }

    public static void onEvent (ItemStack item, PlayerEvent e, ItemEvent type) {
        e.getPlayer().sendMessage("ok-1");
        if (ItemsByItemStack.containsKey(item))
            if (ItemsByItemStack.get(item) instanceof Scroll && !(e.getPlayer().hasPermission("sesens.canUseScroll"))) {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You can't use scrolls!"));
                return;
            }
            e.getPlayer().sendMessage("ok-2");
            ItemsByItemStack.get(item).onEvent(e, type);
            
    }
}