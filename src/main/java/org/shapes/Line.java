package org.shapes;

import org.interfaces.Shape;

import java.awt.*;

public class Line implements Shape {
    Point position1;
    Point position2;
    Color color;
    String name;

    public Point getPosition1() {
        return this.position1;
    }

    public void setPosition1(Point position) {
        this.position1 = position;
    }

    public Point getPosition2() {
        return this.position2;
    }

    public void setPosition2(Point position) {
        this.position2 = position;
    }

    @Override
    public void setName(int number) {
        this.name = number < 10 ? "Line0" + number : "Line" + number;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void getFillColor(Color color) {
        setColor(color);
    }

    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(this.getColor());
        canvas.drawLine(this.getPosition1().x, this.getPosition1().y, this.getPosition2().x, this.getPosition2().y);
    }

    @Override
    public String getData() {
        return "Line" + "," + this.getPosition1().x + "," + this.getPosition1().y + "," + this.getPosition2().x + "," + this.getPosition2().y;
    }
}
