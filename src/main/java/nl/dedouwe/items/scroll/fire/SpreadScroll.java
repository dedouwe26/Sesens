package nl.dedouwe.items.scroll.fire;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.ParticleBuilder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class SpreadScroll extends Scroll {

    public SpreadScroll() {
        super(Sources.Fire, "Spread", Component.text("Nothing more nice,").color(NamedTextColor.GRAY), 
                                            Component.text("than shooting stuff!").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("hold right-click to summon a laser beam with fire, hold left-click for a more destructive one").color(NamedTextColor.GRAY);
    }
    public void onUse(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 1, 0, 0)) {return;}
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        for (int i = 0; i < 23; i++) {
            position.add(direction);
            new ParticleBuilder(Particle.FLAME).count(0).location(position).spawn();
            if (position.getBlock().getType()==Material.AIR) {continue;}
            for (Entity entity : position.getNearbyEntities(1, 1, 1)) {
                if (!(entity instanceof LivingEntity)&&!(entity.getUniqueId().equals(((LivingEntity)e.getPlayer()).getUniqueId()))) {continue;}
                ((LivingEntity)entity).damage(2);
                ((LivingEntity)entity).setFireTicks(50);
            }
            position.clone().subtract(direction).getBlock().setType(Material.FIRE);
            break;
        }
        
    }
    public void onHit(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 2, 0.01, 0)) {return;}
        e.setCancelled(true);
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        for (int i = 0; i < 27; i++) {
            position.add(direction);
            new ParticleBuilder(Particle.FLAME).count(0).location(position).spawn();
            new ParticleBuilder(Particle.CRIT).count(0).location(position.clone().subtract(direction.clone().multiply(0.4))).spawn();
            if (position.getBlock().getType()==Material.AIR) {continue;}
            for (LivingEntity entity : position.getNearbyLivingEntities(2.5, 2.5, 2.5)) {
                entity.damage(4);
                entity.setFireTicks(120);
            }
            position.clone().subtract(direction).getBlock().setType(Material.FIRE);
            break;
        }
    }
}
