package nl.dedouwe.items.scroll.unobtainable;

import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class BeaconScroll extends Scroll {

    public BeaconScroll() {
        super(Sources.Unobtainable, "Beacon", Component.text("A deadly beam from the sky,").color(NamedTextColor.GRAY), 
                                                Component.text("is coming to get you!").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift left-click to make an beam from the sky.").color(NamedTextColor.GRAY);
    }
    
    public void onActivate(PlayerInteractEvent e) {}
}
