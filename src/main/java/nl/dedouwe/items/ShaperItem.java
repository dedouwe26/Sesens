package nl.dedouwe.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.utils.Shape;

public class ShaperItem extends SesenItem {

    public ShaperItem() {
        super(0, Component.text("Shaper").color(NamedTextColor.GRAY), Material.PAINTING, Component.text("Makes shapes depending on your move."));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Try clicking with / without shift and dropping too,").append(Component.newline()).append(Component.text("without hitting entity's."));
    }
    
    public void onDeactivate(PlayerInteractEvent e) {
        if (e.getAction()==Action.RIGHT_CLICK_BLOCK)
            Shape.CreateSphere(3, 40).Draw(e.getClickedBlock().getLocation().add(0, 2, 0), Particle.SLIME);
    }
    public void onUse(PlayerInteractEvent e) {
        if (e.getAction()==Action.RIGHT_CLICK_BLOCK) {
            Shape.CreateSphere(3, 15).Draw(e.getClickedBlock().getLocation().add(0, 2, 0), Particle.SLIME);
        }
        else if (e.getAction()==Action.RIGHT_CLICK_AIR) {
            List<Block> a = new ArrayList<>();
            new BlockIterator(e.getPlayer().getEyeLocation(), 0, 4).forEachRemaining(a::add);
            
            Shape.CreateCircle(1.2, 14).Draw(a.get(a.size()-1).getLocation(), Particle.WAX_OFF);
        }
    }
    public void onActivate(PlayerInteractEvent e) {
        if (e.getAction()==Action.LEFT_CLICK_BLOCK) {
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.instance, new Runnable() {
                public void run() {
                    Shape.CreateSpiral(50, 8, .5).Draw(e.getClickedBlock().getLocation().add(0, 2, 0), Particle.VILLAGER_HAPPY);
                }
            }, 0, 20);
            e.setCancelled(true);
        }
    }
    public void onHit(EntityDamageByEntityEvent e) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.instance, new Runnable() {
            public void run() {
                Shape.CreateBeam(1.2, 30, 10).Draw(e.getEntity().getLocation(), Particle.WAX_ON);
                e.setCancelled(true);
            }
        }, 30);
    }
    public void onDrop(PlayerDropItemEvent e) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.instance, new Runnable() {
            public void run() {
                Shape.CreateBeam(.9, 50, 20).Draw(e.getItemDrop().getLocation(), Particle.WAX_ON);
                e.setCancelled(true);
            }
        }, 30);
    }
}
