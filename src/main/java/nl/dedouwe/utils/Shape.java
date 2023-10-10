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
    /**
     * Location in Draw must be 0, 0, 0 in the dimension
     * @param density between 0 and 1 with max 2 decimals
     */
    public static Shape CreateLine(Vector from, Vector to, double density) {
        return new Shape((Consumer<Vector> maker) -> {
            for (double i = 0d; i <= 1d; i+=density) {
                maker.accept(from.clone().add(to.clone().subtract(from).multiply(i)));
            }
            
        });
    }
    public static Shape CreateSpiral(double density, double height, double width) {
        return new Shape((Consumer<Vector> maker) -> {
            for (double i = 0; i <= height; i+=height/density) {
                maker.accept(new Vector(i*width*Math.sin(i), i, i*Math.cos(i)*width));
            }
        });
        
    }
    public static Shape CreateBeam(double size, double density, double height) {
        return new Shape((Consumer<Vector> maker) -> {
            for (double y = 0; y < height; y+=height/density) {
                for (double i = 0; i < Math.PI * 2; i+=Math.PI/density) {
                    maker.accept(new Vector(Math.cos(i) * size, y, Math.sin(i)*size));
                }
            }
        });
    }
    public static Shape CreateCircle (double radius, double density) {
        return new Shape((Consumer<Vector> maker) -> {
            for (double i = 0; i < Math.PI; i+=Math.PI/density) {
                double subRadius = i/Math.PI*radius;
                for (double j = 0; j < Math.PI * 2; j+=Math.PI/density) {
                    maker.accept(new Vector(Math.cos(j)*subRadius, 0, Math.sin(j)*subRadius));
                }
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
