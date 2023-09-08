package nl.dedouwe.items.scroll.ground;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class LayerScroll extends Scroll {

    public LayerScroll() {
        super(Sources.Ground, "Layer", Component.text("'Legend says the layers").color(NamedTextColor.GRAY), 
                                                Component.text("still stand today.'").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Drop item to make a circle barrier, left click to smash other players nearby, shift right click to make a wall").color(NamedTextColor.GRAY);
    }
    
}
