package nl.dedouwe.items;

import org.bukkit.event.player.PlayerEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.dedouwe.events.ItemEvent;

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
    public void onEvent(PlayerEvent e, ItemEvent type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEvent'");
    }

    @Override
    public TextComponent GetHelp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetHelp'");
    }
    
}
