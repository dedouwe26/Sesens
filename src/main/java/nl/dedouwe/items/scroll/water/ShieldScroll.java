package nl.dedouwe.items.scroll.water;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.SesenItem;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.ParticleUtil;
import nl.dedouwe.utils.Shape;

public class ShieldScroll extends Scroll implements Listener {

    public ShieldScroll() {
        super(Sources.Water, "Shield", Component.text("You need a cover?").color(NamedTextColor.GRAY), 
                                            Component.text("Gotchu.").color(NamedTextColor.GRAY));
        Plugin.instance.getServer().getPluginManager().registerEvents(this, Plugin.instance);
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift right-click for a longer shield, and right-click for a slower one.").color(NamedTextColor.GRAY);
    }
    private double Lerp(double a, double b, double i) {
        return i * (b - a);
    }
    private Vector GetVelocity (Vector from, Vector to) {
        Vector out = new Vector(Lerp(from.getX(), to.getX(), 0.5), Lerp(from.getY(), to.getY(), 0.5), Lerp(from.getZ(), to.getZ(), 0.5));
        return out;
    }
    public UUID CurrentProtected = null;

    public void onDeactivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 4, 0.05, 100)) {return;}
        CurrentProtected=e.getPlayer().getUniqueId();
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Plugin.instance, ()->{
            Shape.CreateSphere(4, 15).Make((v)->{
                ParticleUtil.createColoredParticle(e.getPlayer().getLocation().clone().add(v), Color.fromRGB(104, 126, 255), 1.6f);
            });
            for (LivingEntity entity : e.getPlayer().getLocation().getNearbyLivingEntities(4,4,4)) {
                if (entity instanceof Player) {
                    if (((Player)entity).getUniqueId().equals(CurrentProtected)) {
                        continue;
                    }
                }
                entity.setVelocity(GetVelocity(e.getPlayer().getLocation().toVector(), entity.getLocation().toVector()));
            }
            e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
        }, 0l, 5);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, () -> {
            Bukkit.getScheduler().cancelTask(id);
            CurrentProtected=null;
        }, 100);
    }
    public void onUse(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 2, 0.05, 70)) {return;}
        CurrentProtected=e.getPlayer().getUniqueId();
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Plugin.instance, ()->{
            Shape.CreateSphere(2.2, 15).Make((v)->{
                ParticleUtil.createColoredParticle(e.getPlayer().getLocation().clone().add(v), Color.fromRGB(104, 126, 255), 1.6f);
            });
            for (LivingEntity entity : e.getPlayer().getLocation().getNearbyLivingEntities(2.2,2.2,2.2)) {
                if (entity instanceof Player) {
                    if (((Player)entity).getUniqueId().equals(CurrentProtected)) {
                        continue;
                    }
                }
                entity.setVelocity(GetVelocity(e.getPlayer().getLocation().toVector(), entity.getLocation().toVector()));
            }
            e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1.8f);
        }, 0, 5);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, () -> {
            Bukkit.getScheduler().cancelTask(id);
            CurrentProtected=null;
        }, 70);
    }
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {return;}
        if (((Player)e.getEntity()).getUniqueId().equals(CurrentProtected)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    static void onDamageObserver(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {return;}
        SesenItem target = CustomEvent(((Player)e.getEntity()).getInventory().getItemInMainHand());
        if (target==null) {return;}
        if (!(target instanceof ShieldScroll)) {return;}
        ((ShieldScroll)target).onDamage(e);
    }
}
