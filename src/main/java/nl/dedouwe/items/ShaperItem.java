package nl.dedouwe.items;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.utils.Shape;

public class ShaperItem extends SesenItem {

    public ShaperItem() {
        super(0, Component.text("Shaper").color(NamedTextColor.GRAY), Material.PAINTING, Component.text("Makes shapes depending on your move."));
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("Try clicking with / without shift and dropping too,").append(Component.newline()).append(Component.text("without hitting entity's."));
    }
    
    public void onDeactivate(PlayerInteractEvent e) {
        if (e.getAction()==Action.RIGHT_CLICK_BLOCK)
            Shape.CreateSpiral(3, 3).Draw(e.getClickedBlock().getLocation().add(0, 2, 0), Particle.ASH);
    }
    public void onUse(PlayerInteractEvent e) {
        if (e.getAction()==Action.RIGHT_CLICK_BLOCK)
            Shape.CreateSphere(3, 15).Draw(e.getClickedBlock().getLocation().add(0, 2, 0), Particle.SLIME);
    }
    public void onActivate(PlayerInteractEvent e) {}
    public void onHit(PlayerInteractEvent e) {}
    public void onDrop(PlayerDropItemEvent e) {}
}
