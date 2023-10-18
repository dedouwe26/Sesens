package nl.dedouwe.items.scroll.ground;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.ParticleUtil;

public class ThrowScroll extends Scroll {

    public ThrowScroll() {
        super(Sources.Ground, "Throw", Component.text("Throwing stones, but better!").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Left-click for a destructive big rock, hold shift right-click for medium rocks, hold right-click for dart stones").color(NamedTextColor.GRAY);
    }
    public void onHit(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 8, 0.02, 65)) {return;}
        e.setCancelled(true);
        Vector direction = e.getPlayer().getLocation().getDirection().clone();
        BlockDisplay display = (BlockDisplay)e.getPlayer().getWorld().spawn(e.getPlayer().getLocation(), BlockDisplay.class, (d)->{
            d.setTransformation(new Transformation(new Vector3f(-.65f,.65f,0), new AxisAngle4f(0,0,0,0), new Vector3f(1.3f, 1.3f, 1.3f), new AxisAngle4f(0,0,0,0)));
            d.setBillboard(Billboard.FIXED);
            d.setBlock(Material.COBBLESTONE.createBlockData());
        });
        Location loc = e.getPlayer().getEyeLocation().clone();
        loc.getWorld().playSound(display.getLocation(), Sound.ENTITY_ARROW_SHOOT, 4, .3f);
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Plugin.instance, ()->{
            display.setTransformation(new Transformation(new Vector3f(-.65f,.65f,display.getTransformation().getTranslation().z+0.8f), new AxisAngle4f(0,0,0,0), new Vector3f(1.3f, 1.3f, 1.3f), new AxisAngle4f(0,0,0,0)));
            loc.add(direction);
            ParticleUtil.createBlockParticle(loc, Material.DIRT);
            for (LivingEntity entity : loc.getNearbyLivingEntities(1.7, 1.7, 1.7)) {
                if (entity instanceof Player) {
                    if (((Player)entity).getUniqueId().toString().equals(e.getPlayer().getUniqueId().toString())) {
                        continue;
                    }
                }
                entity.damage(12);
                entity.setVelocity(direction.clone().multiply(2.4));
            }
        }, 0, 2);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            loc.getBlock().setType(Material.COBBLESTONE);
            Bukkit.getScheduler().cancelTask(id);
            display.remove();
        }, 40);
    }
    public void onDeactivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 2, 0.02, 20)) {return;}
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        position.getWorld().playSound(position, Sound.ENTITY_ARROW_SHOOT, 2, 1f);
        for (int i = 0; i < 27; i++) {
            position.add(direction);
            ParticleUtil.createColoredParticle(position, Color.fromRGB(128, 128, 128), 2.51f);
            boolean exit = false;
            for (LivingEntity entity : position.getNearbyLivingEntities(1.5, 1.5, 1.5)) {
                if (entity instanceof Player) {
                    if (((Player)entity).getUniqueId().toString().equals(e.getPlayer().getUniqueId().toString())) {
                        continue;
                    }
                }
                entity.damage(6);
                entity.setVelocity(direction.clone().multiply(1.7));
                exit=true;
            }
            if (exit) {break;}
        }
    }
    public void onUse(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 0, 0.01, 0)) {return;}
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        position.getWorld().playSound(position, Sound.ENTITY_ARROW_SHOOT, 2, 2f);
        for (int i = 0; i < 27; i++) {
            position.add(direction);
            ParticleUtil.createBlockParticle(position, Material.STONE);
            boolean exit = false;
            for (LivingEntity entity : position.getNearbyLivingEntities(1.2, 1.2, 1.2)) {
                if (entity instanceof Player) {
                    if (((Player)entity).getUniqueId().toString().equals(e.getPlayer().getUniqueId().toString())) {
                        continue;
                    }
                }
                entity.damage(3);
                entity.setVelocity(direction.clone().multiply(1.3));
                exit=true;
            }
            if (exit) {break;}
        }
    }
}
