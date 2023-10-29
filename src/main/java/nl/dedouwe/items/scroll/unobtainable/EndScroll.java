package nl.dedouwe.items.scroll.unobtainable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
    
    public void onHit(PlayerInteractEvent e) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kick(Component.text(e.getPlayer().getName()).color(NamedTextColor.GOLD).append(Component.text(" ended the world using:").color(NamedTextColor.WHITE)).appendNewline()
            .append(Component.text("The End Scroll").color(NamedTextColor.DARK_PURPLE).decorate(TextDecoration.BOLD)));
        }
    }
}
