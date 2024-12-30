package org.engines;

import org.interfaces.Shape;

import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel {
    public Engine engine = new Engine();
    int lineCounter;
    int circleCounter;
    int squareCounter;
    int rectangleCounter;

    public DrawingPanel() {
        setSize(600, 400);
        lineCounter = circleCounter = squareCounter = rectangleCounter = 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        engine.refresh(g);
    }

    public void addShape(Shape shape) {
        if (shape.getClass().getName().equals("org.shapes.Circle")) {
            shape.setName(circleCounter);
            circleCounter++;
        }
        if (shape.getClass().getName().equals("org.shapes.Line")) {
            shape.setName(lineCounter);
            lineCounter++;
        }
        if (shape.getClass().getName().equals("org.shapes.Rectangle")) {
            shape.setName(rectangleCounter);
            rectangleCounter++;
        }
        if (shape.getClass().getName().equals("org.shapes.Square")) {
            shape.setName(squareCounter);
            squareCounter++;
        }
        engine.addShape(shape);
        repaint();
    }

    public void removeShape(Shape shape) {
        engine.removeShape(shape);
        repaint();
    }

    public void changeColor() {
        repaint();
    }

    public void changeDimension() {
        repaint();
    }
}
