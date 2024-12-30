package org.shapes;

import java.awt.*;

public class Rectangle extends TwoDShape {

    @Override
    public void setName(int number) {
        this.name = number < 10 ? "Rectangle0" + number : "Rectangle" + number;
    }

    @Override
    public void draw(Graphics canvas) {
        int width = this.getProperties().get("Width").intValue();
        int height = this.getProperties().get("Height").intValue();
        canvas.setColor(this.fillColor);
        canvas.fillRect(this.getPosition().x, this.getPosition().y, width, height);
        canvas.setColor(this.getColor());
        canvas.drawRect(this.getPosition().x, this.getPosition().y, width, height);
    }

    @Override
    public String getData() {
        return "Rectangle" + "," + this.getPosition().x + "," + this.getPosition().y + "," + this.getProperties().get("Height") + "," + this.getProperties().get("Width");
    }
}
