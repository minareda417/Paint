package org.interfaces;

import java.util.ArrayList;

public interface DrawingEngine {
    // Manage shape objects
    void addShape(Shape shape);

    void removeShape(Shape shape);

    // Return the created shape objects
    ArrayList<Shape> getShapes();

    // Redraw all shapes on the canvas
    void refresh(java.awt.Graphics canvas);
}
