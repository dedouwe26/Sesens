package nl.dedouwe.items.scroll.dark;

import java.util.List;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

public class SoulFetcherScroll extends Scroll {

    public SoulFetcherScroll() {
        super(Sources.Dark, "Soul Fetcher", Component.text("Soul gone.").color(NamedTextColor.GRAY), 
                                                Component.text("Life gone.").color(NamedTextColor.GRAY),
                                                Component.text(""),
                                                Component.text(""));
    }
    @Override
    public TextComponent GetHelp() {
        return Component.text("Left-click to fetch someone's soul, shift right-click to spit it out (-amount of enemy's*2). And drop it to summon a wither (-22) (that helps you).").color(NamedTextColor.GRAY);
    }
    public void onHit(EntityDamageByEntityEvent e) {
        if (!Test((Player)e.getDamager(), 12, 0, 110)) {return;}
        if (!(e.getEntity() instanceof Player||e.getEntity() instanceof Villager||e.getEntity() instanceof Creeper)) {return;}
        SetSouls(GetSouls()+1);
        UpdateItem(((Player)e.getDamager()).getInventory().getItemInMainHand());
        AddLevels((Player)e.getDamager(), 0.1, false);
        e.setDamage(20);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.PARTICLE_SOUL_ESCAPE, 4, 0);
        ParticleUtil.createVelocityParticle(Particle.SOUL, e.getEntity().getLocation().add(0, 2.05, 0), 0, 0.1, 0);
        ((Player)e.getDamager()).sendActionBar(Component.text("You now have ").color(NamedTextColor.GREEN).append(Component.text(GetSouls()).decorate(TextDecoration.BOLD).color(TextColor.color(41, 196, 200))).append(Component.text(" souls.").decoration(TextDecoration.BOLD, false).color(NamedTextColor.GREEN)));

        
    }
    public void onDeactivate(PlayerInteractEvent e) {
        if (!Test(e.getPlayer(), 7, 0.02, 80)) {return;}
        List<Entity> en = e.getPlayer().getNearbyEntities(12, 12, 12);
        int cost = en.size()*2;
        if (cost > GetSouls()) {e.getPlayer().sendActionBar(Component.text("You don't have enough souls").color(NamedTextColor.RED));return;}
        for (Entity entity :  en) {
            if (!(entity instanceof LivingEntity)) {continue;}
            cost += 2;
            ((LivingEntity)entity).damage(20);
            ParticleUtil.createVelocityParticle(Particle.SOUL, entity.getLocation().add(0, 2.2, 0), 0, -0.1, 0);
        }
        SetSouls(GetSouls()-cost);
    }
    public void onDrop(PlayerDropItemEvent e) {

    }
    public void SetSouls(int souls) {
        ItemMeta item = this.item.getItemMeta();
        item.getPersistentDataContainer().set(Plugin.instance.SesenSouls, PersistentDataType.INTEGER, souls);
        List<Component> l = item.lore();
        l.set(5, Component.text("Soul Amount: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY).append(Component.text(souls).decorate(TextDecoration.BOLD).color(TextColor.color(41, 196, 200))));
        item.lore(l);
        this.item.setItemMeta(item);
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
