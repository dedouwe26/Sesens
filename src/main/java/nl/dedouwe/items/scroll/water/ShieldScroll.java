package nl.dedouwe.items.scroll.water;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class ShieldScroll extends Scroll {

    public ShieldScroll() {
        super(Sources.Water, "Shield", Component.text("You need a cover?").color(NamedTextColor.GRAY), 
                                            Component.text("Gotchu.").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift right-click for a longer shield, and right-click for a slower one.").color(NamedTextColor.GRAY);
    }
    
}
