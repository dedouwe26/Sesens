package nl.dedouwe.items.scroll.dark;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.ParticleUtil;
import nl.dedouwe.utils.Shape;

public class ShadowScroll extends Scroll {
    
    public ShadowScroll() {
        super(Sources.Dark, "Shadow", Component.text("Now they're scared of the shadows.").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Drop the item for an BLACK HOLE, right-click to make an Shadow (Temporary Turret), with shift to make a Shadow Portal.").color(NamedTextColor.GRAY);
    }
    public void onDrop(PlayerDropItemEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            if (!Test(e.getPlayer(), 21, .25, 100)) {return;}
            Location l = e.getItemDrop().getLocation().clone();
            e.getPlayer().getInventory().addItem(e.getItemDrop().getItemStack());
            e.getItemDrop().remove();
            l.createExplosion(7, true);
            CreateTempRepeatingTask(()->{
                Shape.CreateSphere(5, 28).Make((v)->{
                    l.add(v);
                    ParticleUtil.createColoredParticle(l, Color.BLACK, 2);
                    l.subtract(v);
                });
                for (Entity entity : l.getNearbyEntities(10, 10, 10)) {
                    if (entity instanceof LivingEntity) {
                        l.getWorld().playSound(l, Sound.ITEM_TRIDENT_RIPTIDE_1, 3, 0);
                        ((LivingEntity)entity).damage(30);
                    }
                }
            }, 10, 100);

        }, 60);
    }
    public void onUse(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 10, 0.10, 80)) {return;}
        Location l = e.getPlayer().getLocation().clone().add(0, 1, 0);
        CreateTempRepeatingTask(()->{
                Shape.CreateSphere(2, 25).Make((v)->{
                    v=v.divide(new Vector(2, 1, 2));
                    l.add(v);
                    ParticleUtil.createColoredParticle(l, Color.BLACK, 1.3f);
                    l.subtract(v);
                });
                for (Entity entity : l.getNearbyEntities(14, 14, 14)) {
                    if (entity instanceof LivingEntity) {
                        if (entity.getUniqueId()!=e.getPlayer().getUniqueId()) {
                            l.getWorld().playSound(l, Sound.ENTITY_ENDERMAN_HURT, 1, 0.5f);
                            ((LivingEntity)entity).damage(7);
                            Shape.CreateLine(l.toVector(), entity.getLocation().toVector().add(new Vector(0, 0.6, 0)), 0.1).Make((v)->{
                            ParticleUtil.createColoredParticle(new Location(l.getWorld(), v.getX(), v.getY(), v.getZ()), Color.PURPLE, 1.2f);
                            });
                        }
                        
                    }
                }
            }, 10, 220);
    }
    public void onDeactivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 6, 0.15, 60)) {return;}
        e.getPlayer().getFacing();
        int directionX = e.getPlayer().getFacing() == BlockFace.EAST ? 1 : e.getPlayer().getFacing() == BlockFace.WEST ? -1 : 0;
        int directionY = e.getPlayer().getFacing() == BlockFace.DOWN ? -1 : e.getPlayer().getFacing() == BlockFace.UP ? 1 : 0;
        int directionZ = e.getPlayer().getFacing() == BlockFace.NORTH ? -1 : e.getPlayer().getFacing() == BlockFace.SOUTH ? 1 : 0;
        Location start = e.getPlayer().getLocation().clone();
        Location end = start.clone();
        for (int i = 0; i < 100; i++) {
            end.add(directionX, directionY, directionZ);
            if (end.getBlock().getType()==Material.AIR && end.clone().add(0, 1, 0).getBlock().getType()==Material.AIR) {
                start.add(0, 1, 0);
                Shape.CreateSphere(1.9, 20).Make((v)->{
                    v=v.divide(new Vector(2, 1, 2));
                    start.add(v);
                    ParticleUtil.createColoredParticle(start, Color.PURPLE, 1.2f);
                    start.subtract(v);
                });
                start.getWorld().playSound(start, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                end.getWorld().playSound(start, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                e.getPlayer().sendActionBar(Component.text("Warped...").color(NamedTextColor.GREEN));
                e.getPlayer().teleport(end);
                end.add(0, 1, 0);
                Shape.CreateSphere(1.9, 20).Make((v)->{
                    v=v.divide(new Vector(2, 1, 2));
                    end.add(v);
                    ParticleUtil.createColoredParticle(end, Color.PURPLE, 1.2f);
                    end.subtract(v);
                });
                return;
            }
        }
        e.getPlayer().sendActionBar(Component.text("No place to warp...").color(NamedTextColor.RED));
    }
}
