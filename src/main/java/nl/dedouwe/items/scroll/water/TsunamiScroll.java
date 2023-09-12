package nl.dedouwe.items.scroll.water;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class TsunamiScroll extends Scroll {

    public TsunamiScroll() {
        super(Sources.Water, "Tsunami", Component.text("Everything gone, guaranteed...").color(NamedTextColor.GRAY), 
                                                Component.text("(No insurance)").color(NamedTextColor.DARK_GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift left-click for a tsunami, drop the item for a water splash.").color(NamedTextColor.GRAY);
    }
    
    public void onActivate(PlayerInteractEvent e) {}
    public void onDrop(PlayerDropItemEvent e) {}
}
