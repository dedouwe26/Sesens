package nl.dedouwe.items;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.dedouwe.Sesens;


public class StorageWarper extends SesenItem {
    public StorageWarper() {
        super(0, 
            Component.text("Storage Warper")
                .color(NamedTextColor.DARK_BLUE)
                .decorate(TextDecoration.ITALIC)
                .decorate(TextDecoration.BOLD), 
            Material.SHULKER_SHELL, 
            Component.text("Use it to make a Scroll Storage")
            .color(NamedTextColor.GRAY)
        );
    }
    @Override
    public TextComponent GetHelp () {
        return 
            Component.text("Right click on a barrel.")
            .color(NamedTextColor.GRAY);
            
    }
    @Override
    public void onUse(PlayerInteractEvent e) {
        if (!(e.getAction()==Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.BARREL)) {
            return;
        }
        if (!(e.getPlayer().hasPermission("sesens.setStorage"))) {
            return;
        }
        e.setCancelled(true);
        Sesens.instance.SetPlayerStorage(e.getPlayer(), e.getClickedBlock().getLocation());
    }
}