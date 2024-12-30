package org.shapes;

import java.awt.*;

public class Square extends TwoDShape {

    @Override
    public void setName(int number) {
        this.name = number < 10 ? "Square0" + number : "Square" + number;
    }

    @Override
    public void draw(Graphics canvas) {
        int length = this.getProperties().get("Length").intValue();
        canvas.setColor(fillColor);
        canvas.fillRect(this.getPosition().x, this.getPosition().y, length, length);
        canvas.setColor(this.getColor());
        canvas.drawRect(this.getPosition().x, this.getPosition().y, length, length);
    }

    @Override
    public String getData() {
        return "Square" + "," + this.getPosition().x + "," + this.getPosition().y + "," + this.getProperties().get("Length");
    }
}
