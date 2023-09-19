package nl.dedouwe.items.scroll.air;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerKickEvent.Cause;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.dedouwe.Plugin;
import nl.dedouwe.items.Scroll;
import nl.dedouwe.items.SesenItem;
import nl.dedouwe.items.Sources;
import nl.dedouwe.utils.Shape;

public class DashScroll extends Scroll implements Listener {

    public DashScroll() {
        super(Sources.Air, "Dash", Component.text("Elytra's are useless now!").color(NamedTextColor.GRAY));
        Plugin.instance.getServer().getPluginManager().registerEvents(this, Plugin.instance);
    }

    @Override
    public TextComponent GetHelp() {
        return Component.text("While holding it, use shift + look to dash, (HOLD SHIFT WHEN YOU LAND).").color(NamedTextColor.GRAY);
    }
    // TODO: custom... 

    void onMove (PlayerMoveEvent e) {
        if (!e.getPlayer().isSneaking()) {return;}
        if (!Test(e.getPlayer(), 3, 0, 10)) {return;}
        Shape.CreateCircle(1.2, 10).Draw(e.getPlayer().getLocation().clone(), Particle.WAX_OFF);
        e.getPlayer().getWorld().playSound(e.getPlayer().getEyeLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 0.5f);
        e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.3).add(new Vector(0, .5, 0)));
    }
    void onDamage (EntityDamageEvent e) {
        if (e.getCause()!=DamageCause.FALL) {return;}
        e.setCancelled(true);
    }
    void onPlayerKick(PlayerKickEvent e) {
        if (e.getCause() == Cause.FLYING_PLAYER) {
            if (e.getPlayer().isSneaking()) {
                e.setCancelled(true);
                Plugin.instance.getLogger().info("Sike! Player was using the Dash scroll!");
            }
        }
    }

    @EventHandler
    static void onMoveObserver(PlayerMoveEvent e) {
        SesenItem target = CustomEvent(e.getPlayer().getInventory().getItemInMainHand());
        if (target==null) {return;}
        if (!(target instanceof DashScroll)) {return;}
        ((DashScroll)target).onMove(e);
    }
    @EventHandler
    static void onDamageObserver(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {return;}
        SesenItem target = CustomEvent(((Player)e.getEntity()).getInventory().getItemInMainHand());
        if (target==null) {return;}
        if (!(target instanceof DashScroll)) {return;}
        ((DashScroll)target).onDamage(e);
    }
    @EventHandler
    static void onPlayerKickObserver(PlayerKickEvent e) {
        SesenItem target = CustomEvent(((Player)e.getPlayer()).getInventory().getItemInMainHand());
        if (target==null) {return;}
        if (!(target instanceof DashScroll)) {return;}
        ((DashScroll)target).onPlayerKick(e);
    }
}
