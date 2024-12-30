package org.GUI;

import javax.swing.*;
import java.awt.*;

public class MyCanva extends JFrame {
    MyCanva() {
        super("canvas");
        setPreferredSize(new Dimension(1600, 1000));
        new Canvas() {
            public void paint(Graphics graphics) {
            }
        };
    }
}
