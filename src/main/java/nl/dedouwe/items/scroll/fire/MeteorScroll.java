package nl.dedouwe.items.scroll.fire;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.structure.Structure;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.ParticleBuilder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.Sesens;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.Shape;

public class MeteorScroll extends Scroll {

    public MeteorScroll() {
        super(Sources.Fire, "Meteor", Component.text("A rock from outer-space,").color(NamedTextColor.GRAY), 
                                                Component.text("for Steve to mine...").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift right-click to summon one at crosshair, shift left-click to summon at nearest player").color(NamedTextColor.GRAY);
    }
    public void onDeactivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 12, 0, 220)) {return;}
        Vector direction = e.getPlayer().getLocation().getDirection().clone();
        Location pos = e.getPlayer().getEyeLocation().clone();
        for (int i = 0; i < 50; i++) {
            pos.add(direction);
            if (pos.getBlock().getType()==Material.AIR) {continue;}
            Structure s = Sesens.instance.GetStructure("SesensMeteor.nbt");
            s.place(pos.subtract(direction).subtract(8, 4, 6).getBlock().getLocation(), false, StructureRotation.NONE, Mirror.NONE, 0, 1, new Random());
            for (LivingEntity entity : pos.getNearbyLivingEntities(15, 15, 15)) {
                entity.damage(20);
                entity.setFireTicks(70);
            }

            Shape.CreateLine(pos.clone().add(-3, -1, 3).toVector(), pos.clone().add(-7, 3, 8).toVector(), 0.05).Make((v)->{
                // TODO
                new ParticleBuilder(Particle.FLAME).location(new Location(pos.getWorld(), v.getX(), v.getY(), v.getZ())).force(true).count(0).spawn();
            });;
            break;
        }
    }
    public void onActivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 18, 0, 200)) {return;}
    }
    
}
