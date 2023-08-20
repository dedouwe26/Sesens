package nl.dedouwe.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import nl.dedouwe.Plugin;

public abstract class SesenItem {

    public ItemStack item;

    public String name = "";

    public SesenItem(int customModelData, TextComponent name, Material m, TextComponent ... lore) {
        this(customModelData, name, m, Arrays.asList(lore));
    }
    public SesenItem (int customModelData, TextComponent name, Material m, List<TextComponent> lore) {
        item = new ItemStack(m, 1);
        ItemMeta a = item.getItemMeta();
        a.addItemFlags(
            ItemFlag.HIDE_ARMOR_TRIM,
            ItemFlag.HIDE_ATTRIBUTES,
            ItemFlag.HIDE_DESTROYS,
            ItemFlag.HIDE_DYE,
            ItemFlag.HIDE_ENCHANTS,
            ItemFlag.HIDE_ITEM_SPECIFICS,
            ItemFlag.HIDE_PLACED_ON,
            ItemFlag.HIDE_UNBREAKABLE
        );
        a.lore(lore);
        a.displayName(name);
        a.setCustomModelData(customModelData);
        this.name+=name.content().replace(" ", "_").toLowerCase();
        for (Component namePart : name.children()) {
            this.name += ((TextComponent)namePart).content().replace(" ", "_").toLowerCase();
        }
        a.getPersistentDataContainer().set(Plugin.instance.key, PersistentDataType.STRING, this.name);
        item.setItemMeta(a);
    }

    public abstract TextComponent GetHelp();

    public void onDeactivate(PlayerInteractEvent e) {}
    public void onUse(PlayerInteractEvent e) {}
    public void onActivate(PlayerInteractEvent e) {}
    public void onHit(PlayerInteractEvent e) {}
    public void onDrop(PlayerDropItemEvent e) {}
    public void onActivate(EntityDamageByEntityEvent e) {}
    public void onHit(EntityDamageByEntityEvent e) {}
}