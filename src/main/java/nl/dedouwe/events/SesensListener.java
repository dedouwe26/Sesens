package nl.dedouwe.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import nl.dedouwe.Plugin;
import nl.dedouwe.items.Items;

public class SesensListener implements Listener {
    public SesensListener(Plugin pl) {
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }
    
    @EventHandler
    void onItemUse(PlayerInteractEvent e) {
        // if (e.getHand() == EquipmentSlot.HAND)
        //     return;
        
        // e.getPlayer().sendMessage(e.getPlayer().getInventory().getItemInMainHand().displayName());
        if (!e.hasItem()||e.getItem().getType()==Material.AIR) 
            return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().isSneaking())
                Items.onDeactivate(e.getItem(), e);
            else {
                Items.onUse(e.getItem(), e);
            }
        }
        else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getPlayer().isSneaking())
                Items.onActivate(e.getItem(), e);
            else
                Items.onHit(e.getItem(), e);
        }
    }
    @EventHandler
    void onItemDrop(PlayerDropItemEvent e) {
        Items.onDrop(e.getItemDrop().getItemStack(), e);
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().setResourcePack("https://github.com/dedouwe26/dedouwe26.github.io/raw/main/SesensPack.zip", "C08758D9F2FC4D0C41F541FECC3189123C4C3909", true);
    }

    @EventHandler
    void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || !(e.getCause().equals(DamageCause.ENTITY_ATTACK))) {
            return;
        }
        if (e.getDamager().isSneaking()) {
            Items.onActivate(((Player)e.getDamager()).getInventory().getItemInMainHand(), e);
        } else {
            Items.onHit(((Player)e.getDamager()).getInventory().getItemInMainHand(), e);
        }
    }
}
