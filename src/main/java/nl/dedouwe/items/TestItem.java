package nl.dedouwe.items;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class TestItem extends SesenItem {

    public TestItem() {
        super(0, Component.text("Test Item").color(NamedTextColor.GRAY), Material.STICK, new TextComponent[]{});
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("The help of an test item");
    }
    public void onDeactivate(PlayerInteractEvent e) {
        e.getPlayer().sendMessage("Deactivate type of event...");
        e.getPlayer().sendMessage(String.valueOf(TestLvlRestriction(e.getPlayer(), -1)));
    }
    public void onUse(PlayerInteractEvent e) {
        e.getPlayer().sendMessage("Use type of event...");
    }
    public void onActivate(PlayerInteractEvent e) {
        e.getPlayer().sendMessage("Activate type of event...");
        e.getPlayer().sendMessage(String.valueOf(TestLvlRestriction(e.getPlayer(), 200)));
        
    }
    public void onHit(PlayerInteractEvent e) {
        e.getPlayer().sendMessage("Hit type of event...");
    }
    public void onDrop(PlayerDropItemEvent e) {
        e.getPlayer().sendMessage("Drop type of event...");
    }
    public void onActivate(EntityDamageByEntityEvent e) {
        e.getDamager().sendMessage("Player Activates someone type of event...");
    }
    public void onHit(EntityDamageByEntityEvent e) {
        e.getDamager().sendMessage("Player Hits someone type of event...");
    }

}
