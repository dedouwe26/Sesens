package nl.dedouwe.items.scroll.nature;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.ParticleUtil;
import nl.dedouwe.utils.Shape;

public class StingScroll extends Scroll implements Listener {

    public List<UUID> frozens = new ArrayList<UUID>();

    public StingScroll() {
        super(Sources.Nature, "Sting", 
            Component.text("'The poison through our veins, ")
                .color(NamedTextColor.GRAY),
            Component.text("Stops us...'")
                .color(NamedTextColor.GRAY),
            Component.text("(Can heal)").color(NamedTextColor.DARK_GRAY)
        );
        Plugin.instance.getServer().getPluginManager().registerEvents(this, Plugin.instance);
    }

    @Override
    public TextComponent GetHelp() {
        return Component
            .text("To use this left-click on a player, or shift left-click in the ground for a healing area.")
            .color(NamedTextColor.GRAY);

    }

    public void onHit(EntityDamageByEntityEvent e) {
        if (!Test((Player)e.getDamager(), 0, 0.05, 35)) {return;}
        e.setCancelled(true);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_CREEPER_PRIMED, 3, -2f);
        Shape.CreateBeam(0.5, 20, 2).Make((v)->{
                ParticleUtil.createVelocityParticle(Particle.FALLING_SPORE_BLOSSOM, e.getEntity().getLocation().clone().add(v), 0, 0, 0);
        });
        frozens.add(e.getEntity().getUniqueId());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            frozens.remove(e.getEntity().getUniqueId());
        }, 40);
        
    }
    public void onActivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 1, 0.05, 50)) {return;}
        Location l = e.getPlayer().getLocation();
        CreateTempRepeatingTask(()->{
            Shape.CreateCircle(2, 15).Make((v)->{
                ParticleUtil.createVelocityParticle(Particle.VILLAGER_HAPPY, l.clone().add(v), 0, 2, 0);
            });
            for (LivingEntity entity : l.getNearbyLivingEntities(2, 2, 2)) {
                entity.setHealth(Math.min(entity.getHealth()+4, entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
            }
            l.getWorld().playSound(l, Sound.ENTITY_CREEPER_PRIMED, .5f, 0);
        }, 15, 80);
    }
    @EventHandler
    void onMoveObserver(EntityMoveEvent e) {
        if (frozens.contains(e.getEntity().getUniqueId())) {
            e.setCancelled(true);
        }
    }

}
