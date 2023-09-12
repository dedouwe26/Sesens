package nl.dedouwe.items.scroll.nature;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class StingScroll extends Scroll {

    public StingScroll() {
        super(Sources.Nature, "Sting", 
            Component.text("'The poison through our veins, ")
                .color(NamedTextColor.GRAY),
            Component.text("Stops us...'")
                .color(NamedTextColor.GRAY),
            Component.text("(Can heal)")
        );
    }

    @Override
    public TextComponent GetHelp() {
        return Component
            .text("To use this left-click on a player, or shift left-click in the ground for a healing area.")
            .color(NamedTextColor.GRAY);

    }

    public void onHit(EntityDamageByEntityEvent e) {}
    public void onActivate(PlayerInteractEvent e) {}
}
