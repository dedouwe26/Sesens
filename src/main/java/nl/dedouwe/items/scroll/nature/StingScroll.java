package nl.dedouwe.items.scroll.nature;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class StingScroll extends Scroll {

    public StingScroll() {
        super(Sources.Nature, "Sting", 
            Component.text("'The poison through our veins, ")
                .color(NamedTextColor.GRAY),
            Component.text("Stops us...'")
                .color(NamedTextColor.GRAY)
        );
    }

    @Override
    public TextComponent GetHelp() {
        return Component
            .text("To use this right-click on a player")
            .color(NamedTextColor.GRAY);

    }

    // TODO: functionality
}
