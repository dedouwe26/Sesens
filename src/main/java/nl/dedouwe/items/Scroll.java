package nl.dedouwe.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public abstract class Scroll extends SesenItem {
    public Sources source = Sources.Unobtainable;

    private static List<TextComponent> CreateLore (Sources s) {
        List<TextComponent> a = new ArrayList<>();
        a.add(Component.text(s.toString()).color(s.GetColor()).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        a.add(Component.empty());
        return a;
    }
    /**
     * @param cooldown In seconds
     */
    public Scroll(Sources s, String name, TextComponent ... lore) {
        super(
            s.GetCustomModelData(), 
            Component.text(name)
            .color(s.GetColor())
            .decorate(TextDecoration.BOLD)
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
    @Override
    public void onActivate(EntityDamageByEntityEvent e) {e.setCancelled(true);}
    @Override
    public void onHit(EntityDamageByEntityEvent e) {e.setCancelled(true);}

}
