package nl.dedouwe.utils;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.dedouwe.Sesens;

public class ScrollUtil {

    // Msg's
    public static void ShowProgressBar(Player p, float amount, float max, String name) {
        float progress = (amount/max)*10;

        p.sendActionBar(Component
            .text(name)
            .color(NamedTextColor.AQUA)
            .append(Component
                .text(" [")
                .color(NamedTextColor.WHITE)
            )
            .append(Component.text("=").color(progress >= 1 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 2 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 3 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 4 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 5 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 6 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 7 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 8 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 9 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component.text("=").color(progress >= 10 ? NamedTextColor.GREEN : NamedTextColor.WHITE))
            .append(Component
                .text("] ")
                .color(NamedTextColor.WHITE)
            )
            .append(Component
                .text(max)
                .color(NamedTextColor.AQUA)
            )
        );
    }
    /**
     * For level restricted scrolls.
     * @param p The player.
     * @param lvl The required level.
     * @return True if the player has the required lvl.
     */
    public static Boolean TestLvlRestriction(Player p, double lvl) {
        if (Sesens.instance.GetLevel(p) >= lvl) {
            return true;
        }
        else {
            p.sendActionBar(Component.text("Current level: ").color(NamedTextColor.GREEN)
            .append(Component.text(Sesens.instance.GetLevel(p)).color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)
            .append(Component.text(", required: ").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, false)
            .append(Component.text(lvl).color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)))));
            return false;
        }
    }

}
