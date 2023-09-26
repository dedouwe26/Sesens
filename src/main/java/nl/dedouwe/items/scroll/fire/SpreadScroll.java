package nl.dedouwe.items.scroll.fire;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class SpreadScroll extends Scroll {

    public SpreadScroll() {
        super(Sources.Fire, "Spread", Component.text("Nothing more nice,").color(NamedTextColor.GRAY), 
                                                Component.text("than shooting stuff!").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("hold right-click to summon a laser beam with fire, hold left-click for a more destructive one").color(NamedTextColor.GRAY);
    }
    public void onUse(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 1, 0.01, 0)) {return;}
        BlockIterator iterator = new BlockIterator(e.getPlayer().getEyeLocation(), 0, 10);
        while (iterator.hasNext()) {
            Block b = iterator.next();
            if (b.getType()==Material.AIR) {continue;}
            
        }
    }
    public void onHit(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 2, 0.01, 0)) {return;}
    }
}
