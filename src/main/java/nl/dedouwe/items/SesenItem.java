package nl.dedouwe.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
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
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.dedouwe.Plugin;
import nl.dedouwe.Sesens;

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

    /**
     * Shift right-click
     * @param e
     */
    public void onDeactivate(PlayerInteractEvent e) {}
    /**
     * right-click
     * @param e
     */
    public void onUse(PlayerInteractEvent e) {}
    /**
     * shift left-click
     * @param e
     */
    public void onActivate(PlayerInteractEvent e) {}
    /**
     * left-click
     * @param e
     */
    public void onHit(PlayerInteractEvent e) {}
    /**
     * Drop (q)
     * @param e
     */
    public void onDrop(PlayerDropItemEvent e) {}
    /**
     * Shift left-click HIT
     * @param e
     */
    public void onActivate(EntityDamageByEntityEvent e) {}
    /**
     * left-click HIT
     * @param e
     */
    public void onHit(EntityDamageByEntityEvent e) {}

    // Utilities
    protected long cooldown = 0;

    /**
     * level restriction
     * Checks cooldown
     * adds XP
     * Sets cooldown
     * @param p The player
     * @param lvlRestriction If 0 then nothing happens
     * @param XP If 0 then nothing happens
     * @param cooldownDuration In ticks, if 0 then nothing happens
     * @return Can run code
     */
    protected boolean Test(Player p, double lvlRestriction, double XP, long cooldownDuration) {
        if (!TestLvlRestriction(p, lvlRestriction)) {
            p.sendActionBar(Component.text("Restricted, required lvl: ").color(NamedTextColor.RED).append(Component.text(lvlRestriction).color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)));
            return false;
        } else if (cooldown <= 0) {
            if (!(XP==.0)) {AddLevels(p, XP);}
            if (!(cooldownDuration==0)) {ApplyCooldown(cooldownDuration);}
            return true;
        }
        return cooldown == 0 && TestLvlRestriction(p, lvlRestriction);
    }

    protected static SesenItem CustomEvent (ItemStack item) {
        if (!item.hasItemMeta()) {return null;}
        if (!item.getItemMeta().getPersistentDataContainer().has(Plugin.instance.SesenInstance, PersistentDataType.STRING)) {return null;}
        if (!item.getItemMeta().getPersistentDataContainer().has(Plugin.instance.SesenType, PersistentDataType.STRING)) {return null;}
        return getInstance(item.getItemMeta().getPersistentDataContainer().get(Plugin.instance.SesenInstance, PersistentDataType.STRING), item.getItemMeta().getPersistentDataContainer().get(Plugin.instance.SesenType, PersistentDataType.STRING));
    }

    protected void CreateTempRepeatingTask(Runnable task, long delay, long cooldown) {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Plugin.instance, task, 0l, delay);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, () -> {
            Bukkit.getScheduler().cancelTask(id);
        }, cooldown);
    }

    /**
     * @param duration In ticks
     */
    protected void ApplyCooldown(long duration) {
        cooldown=duration;
        CreateTempRepeatingTask(() -> {
            cooldown--;
        }, 1, duration);
    }

    protected ItemStack UpdateItem(ItemStack i) {
        i.setItemMeta(this.item.getItemMeta());
        return i;
    }

    protected void AddLevels(Player p, double amount) {
        Sesens.instance.AddLevel(p, amount);
        p.sendActionBar(Component.text("Just gained ").color(NamedTextColor.GREEN).append(Component.text(String.valueOf(amount)).color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text(" Levels .").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, false)));
    }
    protected void AddLevels(Player p, double amount, boolean actionbar) {
        Sesens.instance.AddLevel(p, amount);
        if (!actionbar) {return;}
        p.sendActionBar(Component.text("Just gained ").color(NamedTextColor.GREEN).append(Component.text(String.valueOf(amount)).color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text(" Levels .").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, false)));
    }
    /**
     * For level restricted scrolls.
     * @param p The player.
     * @param lvl The required level.
     * @return True if the player has the required lvl.
     */
    protected Boolean TestLvlRestriction(Player p, double lvl) {
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