package nl.dedouwe.items.scroll.air;

import org.bukkit.event.Listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class DashScroll extends Scroll implements Listener {

    public DashScroll() {
        super(Sources.Air, "Dash", Component.text("Elytra's are useless now!").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("While holding it, press space to jump. Use shift + WASD to dash.").color(NamedTextColor.GRAY);
    }
    // TODO: custom... 
}
