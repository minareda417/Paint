package org.shapes;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Circle extends TwoDShape {

    @Override
    public void setName(int number) {
        this.name = number < 10 ? "Circle0" + number : "Circle" + number;
    }

    @Override
    public void draw(@NotNull Graphics canvas) {
        Double radius = this.getProperties().get("radius");
        canvas.setColor(this.fillColor);
        canvas.fillOval(this.getPosition().x, this.getPosition().y, (int) (radius * 2), (int) (radius * 2));
        canvas.setColor(this.getColor());
        canvas.drawOval(this.getPosition().x, this.getPosition().y, (int) (radius * 2), (int) (radius * 2));
    }

    @Override
    public String getData() {
        return "Circle" + "," + this.getPosition().x + "," + this.getPosition().y + "," + this.getProperties().get("radius");
    }
}
