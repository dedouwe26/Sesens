package nl.dedouwe.utils;

import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.ParticleBuilder;

public class Shape {
    public static Shape CreateSphere(double radius, int density) {
        return new Shape((Consumer<Vector> maker) -> {
            for (double i = 0; i <= Math.PI; i += Math.PI / density) {
                double subRadius = Math.sin(i);
                double y = Math.cos(i);
                for (double a = 0; a < Math.PI * 2; a+= Math.PI / density) {
                    maker.accept(new Vector(Math.cos(a) * subRadius * radius, y * radius, Math.sin(a) * subRadius * radius));
                }
             }
        });
    }

    public static Shape CreateSpiral(double density, double height) {
        return new Shape((Consumer<Vector> maker) -> {
            for (double i = 0; i <= height; i+=height/density) {
                
            }
        });
        
    }


    // Shape instance
    private Consumer<Consumer<Vector>> points;


    public Shape(Consumer<Consumer<Vector>> p) {
        this.points = p;
    }

    public void Draw(Location l, Particle p) {
        points.accept((Vector vec) -> {
            l.add(vec);
            new ParticleBuilder(p).location(l).count(1).spawn();
            l.subtract(vec);
        });
    }

    public void Make(Consumer<Vector> maker) {
        points.accept(maker);
    }
}
