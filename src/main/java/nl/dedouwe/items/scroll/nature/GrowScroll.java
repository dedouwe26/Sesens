package nl.dedouwe.items.scroll.nature;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.ParticleBuilder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.Shape;

public class GrowScroll extends Scroll {

    private final Material[] Blocks = new Material[] {
        Material.OAK_LEAVES,
        Material.AZALEA_LEAVES,
        Material.FLOWERING_AZALEA_LEAVES,
        Material.MOSS_BLOCK,
        Material.MOSS_CARPET,
        Material.AZALEA,
        Material.FLOWERING_AZALEA_LEAVES,
        Material.VINE
    };

    public GrowScroll() {
        super(Sources.Nature, "Grow", Component.text("The nature is angry!").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Use shift right-click to make a spiral shaped grow cast, use right-click to make something surrounded.").color(NamedTextColor.GRAY);
    }
    public void onDeactivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 1.5, 0.05, 50)) {return;}
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        for (int i = 0; i < 26; i++) {
            position.add(direction);
            if (position.getBlock().getType()!=Material.AIR) {break;}
        }
        Shape.CreateSpiral(19, 7, 0.45).Make((v)->{
            position.clone().add(v).getBlock().setType(Blocks[new Random().nextInt(Blocks.length)]);
            new ParticleBuilder(Particle.VILLAGER_HAPPY).count(1).location(position.clone().add(v)).spawn();
        });
    }
    public void onUse(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 0, 0.02, 50)) {return;}
        Vector direction = e.getPlayer().getLocation().getDirection().clone().multiply(.7);
        Location position = e.getPlayer().getEyeLocation().clone();
        for (int i = 0; i < 26; i++) {
            position.add(direction);
            if (position.getBlock().getType()!=Material.AIR) {break;}
        }
        Shape.CreateSphere(2, 15).Make((v)->{
            position.clone().add(v).getBlock().setType(Blocks[new Random().nextInt(Blocks.length)]);
        });

    }
}
