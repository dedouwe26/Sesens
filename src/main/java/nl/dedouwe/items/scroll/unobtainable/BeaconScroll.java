package nl.dedouwe.items.scroll.unobtainable;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.ParticleUtil;
import nl.dedouwe.utils.Shape;

public class BeaconScroll extends Scroll {

    public BeaconScroll() {
        super(Sources.Unobtainable, "Beacon", Component.text("A deadly beam from the sky,").color(NamedTextColor.GRAY), 
                                                Component.text("is coming to get you!").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift left-click to make an beam from the sky.").color(NamedTextColor.GRAY);
    }
    
    public void onActivate(PlayerInteractEvent e) {
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        for (int i = 0; i < 30; i++) {
            position.add(direction);
            if (position.getBlock().getType()!=Material.AIR) {break;}
        }
        Shape.CreateBeam(2, 25, 10).Make((v)->{
            ParticleUtil.createColoredParticle(e.getPlayer().getLocation().clone().add(0, 2, 0).add(v), Color.PURPLE, 1.3f);
            
        });
        Shape.CreateBeam(2, 25, 10).Make((v)->{
            ParticleUtil.createColoredParticle(position.clone().add(v), Color.PURPLE, 1.3f);
            
        });
        position.createExplosion(20, true);
    }
}
