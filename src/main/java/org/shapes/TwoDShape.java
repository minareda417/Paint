package org.shapes;

import org.interfaces.Shape;

import java.awt.*;
import java.util.Map;

public abstract class TwoDShape implements Shape {
    Point position;
    Color color;
    Color fillColor;
    Map<String, Double> properties;
    String name;

    @Override
    public String toString() {
        return this.name;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Map<String, Double> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }

    @Override
    public void getFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
