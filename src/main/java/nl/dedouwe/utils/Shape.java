package nl.dedouwe.utils;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.function.Function;

import org.bukkit.Location;

public class Shape {
    public static Shape CreateSphere(double radius) {
        return new Shape((object a) -> {
            return new double[][];
        });
    }


    // Shape instance
    public Shape(Function<Object, Double[][]> point) {
        
    }

    public void Draw(Location l) {
        // TODO
    }
}
