package nl.dedouwe.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.kyori.adventure.text.TextComponent;
import nl.dedouwe.events.ItemEvent;

public abstract class SesenItem {

    public ItemStack item;

    public String name;

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
        item.setItemMeta(a);
        this.name = name.content().replace(" ", "_").toLowerCase();
    }
    public abstract void onEvent(PlayerEvent e, ItemEvent type);
    public abstract TextComponent GetHelp();
    
}