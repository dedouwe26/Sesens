package nl.dedouwe.items.scroll.water;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class TsunamiScroll extends Scroll implements Listener {

    public TsunamiScroll() {
        super(Sources.Water, "Tsunami", Component.text("Everything gone, guaranteed...").color(NamedTextColor.GRAY), 
                                             Component.text("(No insurance)").color(NamedTextColor.DARK_GRAY));
        Plugin.instance.getServer().getPluginManager().registerEvents(this, Plugin.instance);
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift left-click for a tsunami, drop the item for a water splash.").color(NamedTextColor.GRAY);
    }
    public Location NoWaterFlowPlace=null;
    private HashMap<Location, Material> previousBlocks = new HashMap<>();
    private void SetFlooded(Location l) {
        previousBlocks.put(l, l.getBlock().getType());
        Plugin.instance.getLogger().info("add");
        l.getBlock().setType(Material.WATER);
    }
    private int tsunamiCount = 0;
    private void AddTsunami(Location l) {
        if (tsunamiCount>=200) {return;}
        Plugin.instance.getLogger().info(l.getBlock().getType().toString());
        if(l.getBlock().getType()==Material.WATER) {return;}
        SetFlooded(l);
        tsunamiCount++;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            AddTsunami(l.clone().add(1, 0, 0));
        });
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            AddTsunami(l.clone().add(-1, 0, 0));
        });
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            AddTsunami(l.clone().add(0, 0, 1));
        });
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            AddTsunami(l.clone().add(0, 0, -1));
        });
    }
    public void onActivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 8, 0.05, 200)) {return;}
        
        AddTsunami(e.getPlayer().getLocation().clone().add(0, 5, 0));
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            Plugin.instance.getLogger().info((String.valueOf(previousBlocks.size())));
            for (Entry<Location, Material> pos : previousBlocks.entrySet()) {
                pos.getKey().getBlock().setType(pos.getValue());
            }
            previousBlocks.clear();
            tsunamiCount=0;
        }, 100);
    }
    public void onDrop(PlayerDropItemEvent e) {
        if (!Test(e.getPlayer(), 5, 0.08, 80)) {return;}
        e.setCancelled(true);
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        for (int i = 0; i < 26; i++) {
            position.add(direction);
            if (position.getBlock().getType()!=Material.AIR) {break;}
        }
        
        NoWaterFlowPlace = position;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            position.add(0, 1, 0);
            SetFlooded(position);
        }, 5);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            SetFlooded(position.clone().add(0, 1, 0));
        }, 10);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            SetFlooded(position.clone().add(0, 2, 0));
        }, 15);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            SetFlooded(position.clone().add(0, 3, 0));
            SetFlooded(position.clone().add(1, 3, 0));
            SetFlooded(position.clone().add(-1, 3, 0));
            SetFlooded(position.clone().add(0, 3, 1));
            SetFlooded(position.clone().add(0, 3, -1));
        }, 20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            SetFlooded(position.clone().add(0, 4, 0));
            SetFlooded(position.clone().add(1, 4, 0));
            SetFlooded(position.clone().add(-1, 4, 0));
            SetFlooded(position.clone().add(0, 4, 1));
            SetFlooded(position.clone().add(0, 4, -1));

            SetFlooded(position.clone().add(2, 4, 0));
            SetFlooded(position.clone().add(-2, 4, 0));
            SetFlooded(position.clone().add(0, 4, 2));
            SetFlooded(position.clone().add(0, 4, -2));

            SetFlooded(position.clone().add(-1, 4, -1));
            SetFlooded(position.clone().add(1, 4, -1));
            SetFlooded(position.clone().add(-1, 4, 1));
            SetFlooded(position.clone().add(1, 4, 1));
            SetFlooded(position.clone().add(-2, 4, -1));
        }, 25);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            SetFlooded(position.clone().add(0, 5, 0));
        }, 30);
        int Cooldown = 30;
        for (Entry<Location, Material> pos : previousBlocks.entrySet()) {
            Plugin.instance.getLogger().info("a");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
                pos.getKey().getBlock().setType(pos.getValue());
            }, Cooldown);
            Cooldown+=2;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            previousBlocks.clear();
            NoWaterFlowPlace=null;
        }, 75);
    }
    @EventHandler
    void onWaterFlow(BlockFromToEvent e) {
        if (e.getBlock().getType()!=Material.WATER) {return;}
        if (NoWaterFlowPlace==null) {return;}
        double dist = Math.sqrt(Math.pow(e.getBlock().getLocation().getX()-NoWaterFlowPlace.getX(), 2)+Math.pow(e.getBlock().getLocation().getY()-NoWaterFlowPlace.getY(), 2)+Math.pow(e.getBlock().getLocation().getZ()-NoWaterFlowPlace.getZ(), 2));
        if (dist <= 10) {
            e.setCancelled(true);
        }
    }
}
