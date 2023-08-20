package nl.dedouwe.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class SunnyScroll extends Scroll {

    public SunnyScroll() {
        super(Sources.Light, "Sunny", 
            Component.text("This reminds me of my ").color(NamedTextColor.GRAY)
            .append(
                Component.text("Friend")
                .color(NamedTextColor.GOLD)
                .decorate(TextDecoration.BOLD)
            )
        );
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Right-click or shift-right-click for ").color(NamedTextColor.GRAY)
            .append(Component.text("Cool Powers").color(NamedTextColor.GOLD));
    }
    // TODO: funcionality
}
