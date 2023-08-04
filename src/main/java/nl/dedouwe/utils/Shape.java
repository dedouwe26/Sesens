package nl.dedouwe.utils;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class Shape {
    public static Shape CreateSphere(double radius) {
        return new Shape((Consumer)() -> {
            return;
        });
    }


    // Shape instance
    public Shape(Consumer<Double[][]> point) {
        
    }

    public void Draw(Location l) {

    }
}
