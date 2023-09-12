package nl.dedouwe.items.scroll.air;

import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class BlowScroll extends Scroll {

    public BlowScroll() {
        super(Sources.Air, "Blow", Component.text("It's a bit windy outside.").color(NamedTextColor.GRAY), 
                                                Component.text("Go game some more").color(NamedTextColor.DARK_GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Use shift left-click for a big blast and without shift for a less big one.").color(NamedTextColor.GRAY);
    }
    public void onActivate(PlayerInteractEvent e) {}
    public void onHit(PlayerInteractEvent e) {}
    
}
