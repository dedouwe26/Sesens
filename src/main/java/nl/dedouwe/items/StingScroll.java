package nl.dedouwe.items;

import org.bukkit.event.player.PlayerEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.dedouwe.events.ItemEvent;

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
    public void onEvent(PlayerEvent e, ItemEvent type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEvent'");
    }

    @Override
    public TextComponent GetHelp() {
        return Component
            .text("To use this right-click on a player")
            .color(NamedTextColor.GRAY);

    }
    
}
