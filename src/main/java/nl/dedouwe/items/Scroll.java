package nl.dedouwe.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Material;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public abstract class Scroll extends SesenItem {
    public Sources source = Sources.Unobtainable;

    private static List<TextComponent> CreateLore (Sources s) {
        TextColor c =   s == Sources.Nature ? NamedTextColor.GREEN : 
                        s == Sources.Dark ? NamedTextColor.BLACK : 
                        s == Sources.Light ? NamedTextColor.YELLOW : 
                        s == Sources.Fire ? NamedTextColor.RED : 
                        s == Sources.Ground ? TextColor.color(153,97,0) : 
                        s == Sources.Air ? NamedTextColor.WHITE :
                        s == Sources.Unobtainable ? NamedTextColor.LIGHT_PURPLE : NamedTextColor.GRAY;
        List<TextComponent> a = new ArrayList<>();
        a.add(Component.text(s.toString()).color(c).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        a.add(Component.empty());
        return a;
    }

    public Scroll(Sources s, String name, TextComponent ... lore) {
        super(
            s == Sources.Nature ? 48360 : 
            s == Sources.Dark ? 48361 : 
            s == Sources.Light ? 48362 : 
            s == Sources.Fire ? 48363 : 
            s == Sources.Ground ? 48364 : 
            s == Sources.Air ? 48365 :
            s == Sources.Unobtainable ? 48366 : null, 
            Component.text(name).color(
                s == Sources.Nature ? NamedTextColor.GREEN : 
                s == Sources.Dark ? NamedTextColor.BLACK : 
                s == Sources.Light ? NamedTextColor.YELLOW : 
                s == Sources.Fire ? NamedTextColor.RED : 
                s == Sources.Ground ? TextColor.color(153,97,0) : 
                s == Sources.Air ? NamedTextColor.WHITE :
                s == Sources.Unobtainable ? NamedTextColor.LIGHT_PURPLE : NamedTextColor.GRAY
            ).decorate(TextDecoration.BOLD)
            .append(
                Component.text(" Scroll")
                .color(NamedTextColor.GRAY)
                .decorate(TextDecoration.BOLD)
                .decoration(TextDecoration.ITALIC, false)
            ), 
            Material.GLOBE_BANNER_PATTERN, 
            Stream.concat(CreateLore(s).stream(), Arrays.asList(lore).stream()).toList()
        );
    }
}
