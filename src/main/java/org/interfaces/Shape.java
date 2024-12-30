package org.interfaces;

import java.awt.*;

public interface Shape {

    void setName(int number);

    Color getColor();

    void setColor(Color color);

    void getFillColor(Color color);

    void draw(Graphics canvas);

    String getData();
}
