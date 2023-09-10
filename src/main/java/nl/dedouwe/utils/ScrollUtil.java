package nl.dedouwe.utils;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.dedouwe.Plugin;
import nl.dedouwe.Sesens;

public class ScrollUtil {
    // TODO: actionbar msg's and lvl restriction

    // Msg's
    public static void ShowProgressBar(Player p, float amount, float max, String name) {
        float progress = (amount/max)*10;
        TextComponent bar = Component.text("");
        // TODO: Make it work: input are full and not part of progress
        for (int i = 1; i <= 10; i++) {
            Plugin.instance.getLogger().info(String.valueOf(i));
            bar.append(Component.text("|").color(progress >= i ? NamedTextColor.GREEN : NamedTextColor.WHITE));
        }
        p.sendActionBar(Component
            .text(name)
            .color(NamedTextColor.AQUA)
            .append(Component
                .text(" [")
                .color(NamedTextColor.WHITE)
            )
            .append(
                bar
            )
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
