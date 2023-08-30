package nl.dedouwe.items.scroll.light;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class SunnyScroll extends Scroll {

    public SunnyScroll() {
        super(Sources.Light, "Sunny", 
            Component.text("This reminds me of my ").color(NamedTextColor.GRAY)
            .append(
                Component.text("Friend")
                .color(NamedTextColor.GOLD)
            )
        );
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Right-click or shift-right-click for ").color(NamedTextColor.GRAY)
            .append(Component.text("Cool Powers").color(NamedTextColor.GOLD));
    }
    // TODO: functionality
}
