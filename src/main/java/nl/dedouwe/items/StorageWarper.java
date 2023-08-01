package nl.dedouwe.items;

import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.dedouwe.Sesens;
import nl.dedouwe.events.ItemEvent;


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
            Component.text("Right click on a chest or barrel.")
            .color(NamedTextColor.GRAY);
            
    }
    @Override
    public void onEvent(PlayerEvent e, ItemEvent type) {
        e.getPlayer().sendMessage("ok-3");
        if (!(e instanceof PlayerInteractEvent)) {
            return;
        }
        e.getPlayer().sendMessage("ok-4");
        PlayerInteractEvent ev = (PlayerInteractEvent) e;
        if (!(ev.getAction()==Action.RIGHT_CLICK_BLOCK && ev.getClickedBlock() instanceof InventoryHolder && ev.getClickedBlock() instanceof Nameable)) {
            return;
        }
        e.getPlayer().sendMessage("ok-5");
        if (ev.getPlayer().hasPermission("sesens.setStorage")) {
            return;
        }
        e.getPlayer().sendMessage("ok6");
        ev.setCancelled(true);
        Sesens.instance.SetPlayerStorage(e.getPlayer(), ev.getClickedBlock().getLocation());
    }
}