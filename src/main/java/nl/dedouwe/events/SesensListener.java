package nl.dedouwe.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import nl.dedouwe.Plugin;
import nl.dedouwe.items.Items;

public class SesensListener implements Listener {
    public SesensListener(Plugin pl) {
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }
    // @EventHandler
    // void onClickInventory(InventoryClickEvent e) {
    //     if (!(((TextComponent)e.getView().title()).equals(Sesens.storageName))) {
    //         return;
    //     }
    // }
    @EventHandler
    void onItemUse(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.HAND)
            return;
        
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().isSneaking())
                Items.onEvent(e.getItem(), e, ItemEvent.Deactivate);
            else {
                Items.onEvent(e.getItem(), e, ItemEvent.Use);
            }
        }
        else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getPlayer().isSneaking())
                Items.onEvent(e.getItem(), e, ItemEvent.Activate);
            else
                Items.onEvent(e.getItem(), e, ItemEvent.Hit);
        }
    }
    @EventHandler
    void onItemDrop(PlayerDropItemEvent e) {
        Items.onEvent(e.getItemDrop().getItemStack(), e, ItemEvent.Drop);
    }

}
