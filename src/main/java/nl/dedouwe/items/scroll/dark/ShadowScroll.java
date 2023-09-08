package nl.dedouwe.items.scroll.dark;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;

public class ShadowScroll extends Scroll {
    
    public ShadowScroll() {
        super(Sources.Dark, "Shadow", Component.text("Now they're scared of the shadows.").color(NamedTextColor.GRAY));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Drop the item for an BLACK HOLE, right-click to make an Shadow, with shift to make a Shadow Portal.").color(NamedTextColor.GRAY);
    }
    
}
