package nl.dedouwe.items.scroll.fire;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class FloorScroll extends Scroll {

    public FloorScroll() {
        super(Sources.Fire, "Floor", Component.text("Are you ready,").color(NamedTextColor.GRAY), 
                                                Component.text("To play the floor is lava?").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("drop the item to summon the lava floor, left click to stop it.").color(NamedTextColor.GRAY);
    }
    
}
