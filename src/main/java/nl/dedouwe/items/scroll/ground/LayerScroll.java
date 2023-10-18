package nl.dedouwe.items.scroll.ground;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.ParticleUtil;
import nl.dedouwe.utils.Shape;

public class LayerScroll extends Scroll {

    public LayerScroll() {
        super(Sources.Ground, "Layer", Component.text("'Legend says the layers").color(NamedTextColor.GRAY), 
                                                Component.text("still stand today.'").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Drop item to make a circle barrier, left-click to smash other mobs nearby, shift right-click to push up ground at crosshair.").color(NamedTextColor.GRAY);
    }
    private void MoveUp (Location l, int height) {
        for (int i = 1; i <= height; i++) {
            if (!l.clone().add(0, i-height, 0).getBlock().getType().isSolid()) {break;}
            l.clone().add(0, i, 0).getBlock().setType(l.clone().add(0, i-height, 0).getBlock().getType());

        }
    }
    public void onDrop(PlayerDropItemEvent e) {
        if (!Test(e.getPlayer(), 6, 0.2, 40)) {return;}
        for (double j = 0; j < Math.PI * 2; j+=Math.PI/8) {
            MoveUp(e.getItemDrop().getLocation().clone().add(new Vector(Math.cos(j)*4, -2, Math.sin(j)*3)), 3);
        }
        for (double j = 0; j < Math.PI * 2; j+=Math.PI/8) {
            MoveUp(e.getItemDrop().getLocation().clone().add(new Vector(Math.cos(j)*3.8, -2, Math.sin(j)*2.8)), 1);
        }
        Shape.CreateCircle(3, 13).Make((v)->{
            ParticleUtil.createVelocityParticle(Particle.CRIT, e.getItemDrop().getLocation().clone().add(v.add(new Vector(0, 0.1, 0))), 0, -0.2 , 0);
        });
        e.getItemDrop().getLocation().getWorld().playSound(e.getItemDrop().getLocation(), Sound.ENTITY_TURTLE_EGG_CRACK, 3, 0.4f);
        e.setCancelled(true);
    }
    public void onHit(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 8, 0.1, 70)) {return;}
        for (LivingEntity entity : e.getPlayer().getLocation().getNearbyLivingEntities(20, 20, 20)) {
            if (entity instanceof Player) {
                if (((Player)entity).getUniqueId().equals(e.getPlayer().getUniqueId())) {continue;}
            }
            Location l = entity.getLocation().subtract(0, 1, 0).clone();
            Shape.CreateCircle(1, 10).Make((v)->{
                MoveUp(l.clone().add(v), 4);
            });
            l.getWorld().playSound(l, Sound.ENTITY_TURTLE_EGG_CRACK, 3, 0.2f);
            Shape.CreateCircle(2, 13).Make((v)->{
                ParticleUtil.createVelocityParticle(Particle.CRIT, l.clone().add(v.add(new Vector(0, 0.1, 0))), 0, -0.2 , 0);
            });
        }
    }
    public void onDeactivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 7, .1, 18)) {return;}
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        for (int i = 0; i < 27; i++) {
            position.add(direction);
            if (position.getBlock().getType()==Material.AIR) {continue;}
            for (LivingEntity entity : position.getNearbyLivingEntities(1.5, 1.5, 1.5)) {
                entity.damage(4);
            }
            MoveUp(position.clone(), 4);
            MoveUp(position.clone().add(-1, 0, 0), 2);
            MoveUp(position.clone().add(0, 0, -1), 2);
            MoveUp(position.clone().add(0, 0, 1), 1);
            MoveUp(position.clone().add(1, 0, 0), 3);
            position.getWorld().playSound(position, Sound.ENTITY_TURTLE_EGG_CRACK, 3, 0.2f);
            break;
        }
    }
}
