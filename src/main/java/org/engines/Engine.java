package org.engines;

import org.interfaces.DrawingEngine;
import org.interfaces.Shape;

import java.awt.*;
import java.util.ArrayList;

public class Engine implements DrawingEngine {

    private final ArrayList<Shape> shapes;

    public Engine() {
        this.shapes = new ArrayList<>();
    }

    @Override
    public void addShape(Shape shape) {
        this.getShapes().add(shape);
    }

    @Override
    public void removeShape(Shape shape) {
        this.getShapes().remove(shape);
    }

    @Override
    public ArrayList<Shape> getShapes() {
        return this.shapes;
    }

    @Override
    public void refresh(Graphics canvas) {
        for (Shape shape : this.getShapes())
            shape.draw(canvas);
    }
}
