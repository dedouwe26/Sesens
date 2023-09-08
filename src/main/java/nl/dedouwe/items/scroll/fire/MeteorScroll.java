package nl.dedouwe.items.scroll.fire;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class MeteorScroll extends Scroll {

    public MeteorScroll() {
        super(Sources.Fire, "Meteor", Component.text("A rock from outer-space,").color(NamedTextColor.GRAY), 
                                                Component.text("for Steve to mine...").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Shift right click to summon one at crosshair, shift left click to summon at nearest player").color(NamedTextColor.GRAY);
    }
    
}
