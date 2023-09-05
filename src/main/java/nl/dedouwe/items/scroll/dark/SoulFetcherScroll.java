package nl.dedouwe.items.scroll.dark;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class SoulFetcherScroll extends Scroll {

    public SoulFetcherScroll() {
        super(Sources.Dark, "Soul Fetcher", Component.text("Soul gone.").color(NamedTextColor.GRAY), 
                                                Component.text("Life gone.").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("").color(NamedTextColor.GRAY);
    }
    
}
