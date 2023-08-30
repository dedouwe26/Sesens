package nl.dedouwe.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    public static HashMap<String, SesenItem> Instances = new HashMap<String, SesenItem>();

    public ItemStack item;

    public String name = "";

    public String InstanceID;

    public static SesenItem getInstance(String instance, String type) {
        if (Instances.containsKey(instance)) {
            return Instances.get(instance);
        } else {
            return CreateInstance(type, instance);
        }
    }

    public static SesenItem CreateInstance (String type) {
        if (Items.ItemTypes.containsKey(type)) {
            try {
                SesenItem inst = Items.ItemTypes.get(type).getClass().getConstructor(new Class[] {}).newInstance(new Object[] {});
                Instances.put(inst.InstanceID, inst);
                return inst;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static SesenItem CreateInstance (String type, String instance) {
        if (Items.ItemTypes.containsKey(type)) {
            try {
                SesenItem inst = Items.ItemTypes.get(type).getClass().getConstructor(new Class[] {}).newInstance(new Object[] {});
                inst.InstanceID = instance;
                ItemMeta a = inst.item.getItemMeta();
                a.getPersistentDataContainer().set(Plugin.instance.SesenInstance, PersistentDataType.STRING, instance);
                inst.item.setItemMeta(a);
                Instances.put(inst.InstanceID, inst);
                return inst;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public SesenItem(int customModelData, TextComponent name, Material m, TextComponent ... lore) {
        this(customModelData, name, m, Arrays.asList(lore));
    }
    public SesenItem (int customModelData, TextComponent name, Material m, List<TextComponent> lore) {
        this(customModelData, makeName(name), name, m, lore);
    }
    public SesenItem (int customModelData, String name, TextComponent displayName, Material m, List<TextComponent> lore) {
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
        a.displayName(displayName);
        a.setCustomModelData(customModelData);
        this.name = name;
        this.InstanceID = makeUUID();
        a.getPersistentDataContainer().set(Plugin.instance.SesenType, PersistentDataType.STRING, this.name);
        a.getPersistentDataContainer().set(Plugin.instance.SesenInstance, PersistentDataType.STRING, this.InstanceID);
        item.setItemMeta(a);
        Instances.put(this.InstanceID, this);
    }

    private static String makeName(TextComponent displayName) {
        String name = displayName.content().replace(" ", "_").toLowerCase();
        for (Component namePart : displayName.children()) {
            name += ((TextComponent)namePart).content().replace(" ", "_").toLowerCase();
        }
        return name;
    }

    private static String makeUUID() {
        String uuid = UUID.randomUUID().toString();
        if (Instances.containsKey(uuid)) {
            return makeUUID();
        } else {
            return uuid;
        }
    }

    public void Wipe() {
        SesenItem.Instances.remove(this.InstanceID);
        this.item.getItemMeta().getPersistentDataContainer().remove(Plugin.instance.SesenInstance);
        this.item.getItemMeta().getPersistentDataContainer().remove(Plugin.instance.SesenType);
        this.item=new ItemStack(item.getType(), item.getAmount());
    }

    public void GiveTo(Player p) {
        p.getInventory().addItem(this.item);
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