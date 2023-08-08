package nl.dedouwe.utils;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustTransition;
import org.bukkit.block.Block;

import com.destroystokyo.paper.ParticleBuilder;

public class ParticleUtil {
    public static void createColoredParticle (Location l, Color c, float size) {
        new ParticleBuilder(Particle.REDSTONE)
            .location(l)
            .count(0)
            .extra(1)
            .color(c, size)
            .force(true)
            .spawn();
    }
    public static void createBlockParticle (Location l, Block b) {
        new ParticleBuilder(Particle.BLOCK_DUST)
            .location(l)
            .data(b.getBlockData())
            .force(true)
            .spawn();
    }
    public static void createBlockParticle (Location l, Material b) {
        new ParticleBuilder(Particle.BLOCK_DUST)
            .location(l)
            .data(b.createBlockData())
            .force(true)
            .spawn();
    }
    public static void createAnimatedParticle (Location l, Color from, Color to, float size) {
        new ParticleBuilder(Particle.DUST_COLOR_TRANSITION)
            .location(l)
            .data(new DustTransition(from, to, size))
            .force(true)
            .spawn();
    }
    /**
     * 
     * @param p Must be a directional particle
     */
    public static void createVelocityParticle (Particle p, Location l, double x, double y, double z) {
        new ParticleBuilder(p)
            .location(l)
            .count(0)
            .offset(x, y, z)
            .force(true)
            .spawn();
    }
}
