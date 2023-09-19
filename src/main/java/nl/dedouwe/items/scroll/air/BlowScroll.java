package nl.dedouwe.items.scroll.air;

import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.ParticleUtil;

public class BlowScroll extends Scroll {
    public BlowScroll() {
        super(Sources.Air, "Blow", Component.text("It's a bit windy outside.").color(NamedTextColor.GRAY), 
                                                Component.text("(Go game some more)").color(NamedTextColor.DARK_GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Use shift left-click for a big blast and without shift for a less big one.").color(NamedTextColor.GRAY);
    }
    public void onActivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 5, .1, 10)) return;

        e.getPlayer().getWorld().playSound(e.getPlayer().getEyeLocation(), Sound.BLOCK_BEEHIVE_ENTER, 1, 0);
        for (Entity entity : e.getPlayer().getNearbyEntities(8, 8, 8)) {
            CreateTempRepeatingTask(()->{
                ParticleUtil.createColoredParticle(entity.getLocation(), Color.WHITE, 1.2f);
            }, 1, 48);
            entity.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2.1));
        }
    }
    public void onHit(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 0, .1, 10)) return;
        e.getPlayer().getWorld().playSound(e.getPlayer().getEyeLocation(), Sound.BLOCK_BEEHIVE_ENTER, 1, 0);
        for (Entity entity : e.getPlayer().getNearbyEntities(5, 5, 5)) {
            CreateTempRepeatingTask(()->{
                ParticleUtil.createColoredParticle(entity.getLocation(), Color.WHITE, 1.2f);
            }, 1, 48);
            entity.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.4));
        }
    }
    
}
