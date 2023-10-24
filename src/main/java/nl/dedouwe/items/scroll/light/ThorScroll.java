package nl.dedouwe.items.scroll.light;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
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

public class ThorScroll extends Scroll {

    public ThorScroll() {
        super(Sources.Light, "Thor", Component.text("The light falls down...").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Use right-click for a lightning, and drop it for a lightning dome.").color(NamedTextColor.GRAY);
    }
    public void onUse(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 1, 0.05, 30)) {return;}
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        for (int i = 0; i < 24; i++) {
            position.add(direction);
            if (position.getBlock().getType()!=Material.AIR) {break;}
        }
        Shape.CreateCircle(1.8, 11).Make((v)->{
            ParticleUtil.createColoredParticle(position.clone().add(v), Color.fromRGB(46, 140, 255), .8f);
        });
        position.getWorld().spawn(position, LightningStrike.class);
    }
    public void onDrop(PlayerDropItemEvent e) {
        if (!Test(e.getPlayer(), 3, 0.01, 70)) {return;}
        e.setCancelled(true);
        Location position = e.getPlayer().getLocation().clone();
        CreateTempRepeatingTask(()->{
            Shape.CreateCircle(5, 10).Make((v)->{
                ParticleUtil.createColoredParticle(position.clone().add(v), Color.fromRGB(46, 140, 255), .8f);
            });
            for (LivingEntity entity : position.getNearbyLivingEntities(5, 5, 5)) {
                if (entity instanceof Player) {
                    if (((Player)entity).getUniqueId().equals(e.getPlayer().getUniqueId())) {
                        continue;
                    }
                }
                entity.getWorld().spawn(entity.getLocation(), LightningStrike.class);
            }
        }, 10, 70);
    }

}
