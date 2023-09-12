package nl.dedouwe.items.scroll.dark;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class SoulFetcherScroll extends Scroll {

    public SoulFetcherScroll() {
        super(Sources.Dark, "Soul Fetcher", Component.text("Soul gone.").color(NamedTextColor.GRAY), 
                                                Component.text("Life gone.").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift right-click and hold to suck the energy out of the player, left-click to fetch their soul").color(NamedTextColor.GRAY);
    }
    public void onDeactivate(PlayerInteractEvent e) {}
    public void onHit(EntityDamageByEntityEvent e) {}

}
