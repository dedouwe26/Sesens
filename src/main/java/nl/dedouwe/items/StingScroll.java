package nl.dedouwe.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class StingScroll extends Scroll {

    public StingScroll() {
        super(Sources.Nature, "Sting", 
            Component.text("'The poison through our veins, ")
                .color(NamedTextColor.GRAY)
                .decorate(TextDecoration.ITALIC), 
            Component.text("Stops us...'")
                .color(NamedTextColor.GRAY)
                .decorate(TextDecoration.ITALIC)
        );
    }

    @Override
    public TextComponent GetHelp() {
        return Component
            .text("To use this right-click on a player")
            .color(NamedTextColor.GRAY);

    }

    // TODO: funcionality
}
