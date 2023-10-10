package nl.dedouwe.items.scroll.ground;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
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
        if (!Test(e.getPlayer(), 4, 0.02, 80)) {return;}
    }
    public void onDeactivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 2, 0.02, 20)) {return;}
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        position.getWorld().playSound(position, Sound.ENTITY_ARROW_SHOOT, 2, 2f);
        for (int i = 0; i < 27; i++) {
            position.add(direction);
            ParticleUtil.createColoredParticle(position, Color.fromRGB(128, 128, 128), 2f);
            boolean exit = false;
            for (LivingEntity entity : position.getNearbyLivingEntities(1.5, 1.5, 2.5)) {
                entity.damage(6);
                entity.setVelocity(direction.multiply(1.7));
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
            for (LivingEntity entity : position.getNearbyLivingEntities(1.2, 1.2, 2.2)) {
                entity.damage(3);
                entity.setVelocity(direction.multiply(1.3));
                exit=true;
            }
            if (exit) {break;}
        }
    }
}
