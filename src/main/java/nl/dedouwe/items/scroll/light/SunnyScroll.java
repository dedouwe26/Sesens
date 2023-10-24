package nl.dedouwe.items.scroll.light;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.ParticleUtil;
import nl.dedouwe.utils.Shape;

public class SunnyScroll extends Scroll {

    boolean isActive=false;
    private int taskID;

    public SunnyScroll() {
        super(Sources.Light, "Sunny", 
            Component.text("This reminds me of my ").color(NamedTextColor.GRAY)
            .append(
                Component.text("Friend")
                .color(NamedTextColor.GOLD)
            )
        );
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Right-click to summon a ball or to shoot lasers, shift right-click to fire the ball.").color(NamedTextColor.GRAY);
    }
    public void onUse(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 10, 0.2, 10)) {return;}
        if (isActive) {
            Location position = e.getPlayer().getEyeLocation();
            Vector direction = e.getPlayer().getLocation().getDirection().multiply(.9);
            for (int i = 0; i < 27; i++) {
                position.add(direction);
                if (position.getBlock().getType()!=Material.AIR) {
                    break;
                }
            }
            for (double i = 0d; i <= 1d; i+=0.05) {
                Location loc = e.getPlayer().getLocation().clone().add(0, 5, 0).clone().add(position.clone().subtract(e.getPlayer().getLocation().clone().add(0, 5, 0)).multiply(i));
                ParticleUtil.createColoredParticle(loc, Color.fromRGB(255, 172, 28), 2.7f);
                loc.getWorld().playSound(loc, Sound.BLOCK_BEACON_AMBIENT, 2f, 1f);
                for (LivingEntity entity : loc.getNearbyLivingEntities(2, 2, 2)) {
                    if (entity instanceof Player) {
                        if (((Player)entity).getUniqueId().equals(e.getPlayer().getUniqueId())) {
                            continue;
                        }
                    }
                    entity.setVelocity(direction.add(new Vector(0, 0.1, 0)));
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1, false));
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 20, false));
                    entity.damage(15);
                }
            }
        } else {
            isActive = true;
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Plugin.instance, ()->{
                Shape.CreateSphere(2.5, 16).Make((v)->{
                    ParticleUtil.createColoredParticle(e.getPlayer().getLocation().clone().add(0, 5, 0).add(v), Color.fromRGB(255, 172, 28), 2.5f);
                });
            }, 0, 5);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
                Bukkit.getScheduler().cancelTask(taskID);
                isActive = false;
            }, 100);
        }
    }
    public void onDeactivate(PlayerInteractEvent e) {
        if (!isActive) {
            e.getPlayer().sendActionBar(Component.text("First summon the ball.").color(TextColor.color(255, 160, 28)));
            return;
        }
        if (!Test(e.getPlayer(), 15, 1, 80)) {return;}
        Bukkit.getScheduler().cancelTask(taskID);
        isActive = false;
        Location position = e.getPlayer().getEyeLocation();
        Vector direction = e.getPlayer().getLocation().getDirection().multiply(.9);
        for (int i = 0; i < 30; i++) {
            position.add(direction);
            if (position.getBlock().getType()!=Material.AIR) {
                break;
            }
        }
        for (double i = 0d; i <= 1d; i+=0.1) {
            Plugin.instance.getLogger().info(String.valueOf(i));
            Plugin.instance.getLogger().info(String.valueOf(i>.9));
            Location loc = e.getPlayer().getLocation().clone().add(0, 5, 0).clone().add(position.clone().subtract(e.getPlayer().getLocation().clone().add(0, 5, 0)).multiply(i));
            loc.getWorld().playSound(loc, Sound.BLOCK_BEACON_AMBIENT, 2f, 1f);
            Shape.CreateSphere(2.2, 15).Make((v)->{
                ParticleUtil.createColoredParticle(loc.clone().add(v), Color.fromRGB(255, 172, 28), 2.7f);
            });
            if (i>.9) {
                loc.getWorld().createExplosion(loc, 7f, true);
                CreateTempRepeatingTask(()->{
                    Shape.CreateSphere(2.5, 16).Make((v)->{
                        ParticleUtil.createColoredParticle(loc.clone().add(v), Color.fromRGB(255, 172, 28), 2.7f);
                    });
                }, 40, 5);
            }
            for (LivingEntity entity : loc.getNearbyLivingEntities(4, 4, 4)) {
                if (entity instanceof Player) {
                    if (((Player)entity).getUniqueId().equals(e.getPlayer().getUniqueId())) {
                        continue;
                    }
                }
                entity.setVelocity(direction.add(new Vector(0, 0.1, 0)));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1, false));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 255, false));
                entity.damage(40);
            }
        }
    }

}
