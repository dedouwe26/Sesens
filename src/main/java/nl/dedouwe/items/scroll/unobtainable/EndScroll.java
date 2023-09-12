package nl.dedouwe.items.scroll.unobtainable;

import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class EndScroll extends Scroll {

    public EndScroll() {
        super(Sources.Unobtainable, "End", Component.text("A powerfull spell that no one can handle,").color(NamedTextColor.GRAY), 
                                                Component.text("not even you.").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Just left-click to end the world...").color(NamedTextColor.GRAY);
    }
    
    public void onUse(PlayerInteractEvent e) {}
}
