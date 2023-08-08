package nl.dedouwe.utils;

import java.util.function.Consumer;

public class Shape {
    public static Shape CreateSphere(double radius) {
        return new Shape((Consumer<double[]> a) -> {
        });
    }

    public static Shape CreateSpiral(double size, double height) {
        return new Shape((Consumer<double[]> a) -> {
            
        });
        
    }


    // Shape instance
    private Consumer<Consumer<double[]>> points;


    public Shape(Consumer<Consumer<double[]>> p) {
        this.points = p;
    }


    public void Make(Consumer<double[]> maker) {
        points.accept(maker);
    }
}
