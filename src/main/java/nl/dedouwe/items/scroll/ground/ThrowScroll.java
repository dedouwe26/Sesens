package nl.dedouwe.items.scroll.ground;

import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class ThrowScroll extends Scroll {

    public ThrowScroll() {
        super(Sources.Ground, "Throw", Component.text("Throwing stones, but better!").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift left-click for a destructive big rock, hold left-click for medium rocks, hold right-click for dart stones").color(NamedTextColor.GRAY);
    }
    public void onActivate(PlayerInteractEvent e) {}
    // TODO: Hold l-r click
}
