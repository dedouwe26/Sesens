package nl.dedouwe.items.scroll.light;

import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class ThorScroll extends Scroll {

    public ThorScroll() {
        super(Sources.Light, "Thor", Component.text("The light falls down...").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Use right-click for a lightning, and shift left-click for a lightning dome.").color(NamedTextColor.GRAY);
    }
    public void onUse(PlayerInteractEvent e) {}
    public void onActivate(PlayerInteractEvent e) {}

}
