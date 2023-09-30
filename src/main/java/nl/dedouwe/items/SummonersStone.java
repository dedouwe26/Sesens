package nl.dedouwe.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.dedouwe.Plugin;
import nl.dedouwe.Sesens;

public class SummonersStone extends SesenItem {

    public SummonersStone() {
        super(445467, Component.text("Summoner's ").color(TextColor.color(0, 67, 122)).decorate(TextDecoration.BOLD).append(Component.text("Stone").color(TextColor.color(82, 82, 82))), Material.CHORUS_FRUIT, Component.text("The mythical item with the power of Summoning.").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Use this at the center of the TOMB to summon new treasures in the world (hold right-click)...").color(NamedTextColor.GRAY);
    }
    public void onUse(PlayerInteractEvent e) {
        if (!e.getPlayer().getLocation().getBlock().getLocation().clone().equals(Plugin.instance.config.GetTomb().add(7, 2, 7))) {
            e.getPlayer().sendMessage(Component.text("Go to the Center of the Tomb!").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
            e.setCancelled(true);
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            e.getPlayer().getInventory().remove(e.getItem());
            Bukkit.broadcast(Component.newline().append(Component.text("The Summoner's Stone has spoken!").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD)).appendNewline());
            Sesens.instance.StartCycle(e.getPlayer().getWorld());
            Wipe();
        }, 10);
    }
    public void onDeactivate(PlayerInteractEvent e) {
        e.setCancelled(true);
    }
}
