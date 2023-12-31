package nl.dedouwe.items.scroll.dark;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.dedouwe.Plugin;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.ParticleUtil;

public class SoulFetcherScroll extends Scroll implements Listener {

    public SoulFetcherScroll() {
        super(Sources.Dark, "Soul Fetcher", Component.text("Soul gone.").color(NamedTextColor.GRAY), 
                                                Component.text("Life gone.").color(NamedTextColor.GRAY),
                                                Component.text(""),
                                                Component.text(""));
        Plugin.instance.getServer().getPluginManager().registerEvents(this, Plugin.instance);
    }
    @Override
    public TextComponent GetHelp() {
        return Component.text("Left-click to fetch someone's soul (Creepers and Players and Villagers), shift right-click to spit it out (-amount of enemy's*2). And drop it to summon a wither (-19) (that helps you).").color(NamedTextColor.GRAY);
    }
    public void onHit(EntityDamageByEntityEvent e) {
        if (!Test((Player)e.getDamager(), 12, 0, 110)) {return;}
        if (!(e.getEntity() instanceof Player||e.getEntity() instanceof Villager||e.getEntity() instanceof Creeper)) {return;}
        SetSouls(GetSouls()+1);
        UpdateItem(((Player)e.getDamager()).getInventory().getItemInMainHand());
        AddLevels((Player)e.getDamager(), 0.1, false);
        e.setDamage(25);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.PARTICLE_SOUL_ESCAPE, 5, 0);
        ParticleUtil.createVelocityParticle(Particle.SOUL, e.getEntity().getLocation().add(0, 2.05, 0), 0, 0.1, 0);
        ((Player)e.getDamager()).sendActionBar(Component.text("You now have ").color(NamedTextColor.GREEN).append(Component.text(GetSouls()).decorate(TextDecoration.BOLD).color(TextColor.color(41, 196, 200))).append(Component.text(" souls.").decoration(TextDecoration.BOLD, false).color(NamedTextColor.GREEN)));

        
    }
    public void onDeactivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 7, 0.02, 80)) {return;}
        List<Entity> en = e.getPlayer().getNearbyEntities(12, 12, 12);
        int cost = en.size();
        if (cost > GetSouls()) {e.getPlayer().sendActionBar(Component.text("You don't have enough souls").color(NamedTextColor.RED));return;}
        for (Entity entity :  en) {
            if (!(entity instanceof LivingEntity)) {continue;}
            ((LivingEntity)entity).damage(35);
            entity.getWorld().playSound(entity.getLocation(), Sound.PARTICLE_SOUL_ESCAPE, 5, 0);
            ParticleUtil.createVelocityParticle(Particle.SOUL, entity.getLocation().add(0, 2.3, 0), 0, -0.1, 0);
        }
        UpdateSouls(GetSouls()-cost, e.getItem());
    }
    public void onDrop(PlayerDropItemEvent e) {
        if (!Test(e.getPlayer(), 18, 0.03, 120)) {return;}
        if (19 > GetSouls()) {e.getPlayer().sendActionBar(Component.text("You don't have enough souls").color(NamedTextColor.RED));return;}
        UpdateSouls(GetSouls()-19, e.getItemDrop().getItemStack());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
            Location l = e.getItemDrop().getLocation();
            e.getPlayer().getInventory().addItem(e.getItemDrop().getItemStack());
            e.getItemDrop().remove();
            Wither w = (Wither)l.getWorld().spawnEntity(l, EntityType.WITHER);
            w.getPersistentDataContainer().set(Plugin.instance.SesenSouls, PersistentDataType.STRING, e.getPlayer().getUniqueId().toString());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.instance, ()->{
                w.remove();
            }, 300);
        }, 25);
    }

    @EventHandler
    static void onTarget(EntityTargetEvent e) {
        if (e.getTarget()==null) {return;}
        if (!e.getEntity().getPersistentDataContainer().has(Plugin.instance.SesenSouls, PersistentDataType.STRING)) {return;}
        if (e.getEntity().getPersistentDataContainer().get(Plugin.instance.SesenSouls, PersistentDataType.STRING).equals(e.getTarget().getUniqueId().toString())) {
            e.setCancelled(true);
        }
    }

    public void SetSouls(int souls) {
        ItemMeta item = this.item.getItemMeta();
        item.getPersistentDataContainer().set(Plugin.instance.SesenSouls, PersistentDataType.INTEGER, souls);
        List<Component> l = item.lore();
        l.set(5, Component.text("Soul Amount: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY).append(Component.text(souls).decorate(TextDecoration.BOLD).color(TextColor.color(41, 196, 200))));
        item.lore(l);
        this.item.setItemMeta(item);
    }
    public void UpdateSouls(int souls, ItemStack i) {
        SetSouls(souls);
        UpdateItem(i);
    }
    public int GetSouls() {
        if (this.item.getItemMeta().getPersistentDataContainer().has(Plugin.instance.SesenSouls, PersistentDataType.INTEGER)) {
            return this.item.getItemMeta().getPersistentDataContainer().get(Plugin.instance.SesenSouls, PersistentDataType.INTEGER);
        } else {
            SetSouls(0);
            return 0;
        }
    }

}
