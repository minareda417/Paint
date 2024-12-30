package org.GUI;

import org.engines.DrawingPanel;
import org.interfaces.Shape;
import org.shapes.Circle;
import org.shapes.Line;
import org.shapes.Rectangle;
import org.shapes.Square;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends MyCanva {
    private JPanel MainWindowPanel;
    private JButton rectangleButton;
    private JButton circleButton;
    private JButton lineSegmentButton;
    private JComboBox comboBox1;
    private JButton colorizeButton;
    private JButton deleteButton;
    private JButton squareButton;
    private JPanel CanvaPanel;
    private JPanel voidPanel;
    private JPanel voidPanel1;
    private JPanel voidPanel2;
    private JPanel voidPanel3;
    private JPanel voidPanel4;
    private JPanel voidPanel5;
    private JPanel voidPanel6;
    private JPanel voidPanel7;
    private JButton redoButton;
    private JButton undoButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton resizeButton;
    private JButton moveButton;


    public MainWindow() {
        setTitle("Vector Drawing Application");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //MainWindowPanel.setBackground(Color.LIGHT_GRAY);
        voidPanel.setBackground(Color.LIGHT_GRAY);
        voidPanel1.setBackground(Color.LIGHT_GRAY);
        voidPanel2.setBackground(Color.LIGHT_GRAY);
        voidPanel3.setBackground(Color.LIGHT_GRAY);
        voidPanel4.setBackground(Color.LIGHT_GRAY);
        voidPanel5.setBackground(Color.LIGHT_GRAY);
        voidPanel6.setBackground(Color.LIGHT_GRAY);
        voidPanel7.setBackground(Color.LIGHT_GRAY);
        setContentPane(MainWindowPanel);
        DrawingPanel drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(600, 400));
        JScrollPane scrollPane = new JScrollPane(drawingPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        CanvaPanel.setLayout(new BorderLayout());
        CanvaPanel.add(scrollPane, BorderLayout.CENTER);
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        CanvaPanel.setBorder(blackLine);
        setVisible(true);
        circleButton.addActionListener(_ -> showCircleInputDialog(drawingPanel));
        rectangleButton.addActionListener(_ -> showRectangleInputDialog(drawingPanel));
        squareButton.addActionListener(_ -> showSquareInputDialog(drawingPanel));
        lineSegmentButton.addActionListener(_ -> showLineInputDialog(drawingPanel));
        deleteButton.addActionListener(_ -> showDeleteInputDialog(drawingPanel));
        colorizeButton.addActionListener(_ -> showColorizeInputDialog(drawingPanel));
        resizeButton.addActionListener(_ -> showResizeInputDialog(drawingPanel));
        moveButton.addActionListener(_ -> showMoveInputDialog(drawingPanel));
        loadButton.addActionListener(_ -> {
            try {
                showLoadInputDialog(drawingPanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        saveButton.addActionListener(_ -> {
            try {
                showSaveInputDialog(drawingPanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    private void showSaveInputDialog(DrawingPanel drawingPanel) throws IOException {
        String path = "";
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setAcceptAllFileFilterUsed(false);
        j.setDialogTitle("Select saving directory");

        int r = j.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION)
            path = j.getSelectedFile().getAbsolutePath() + ".txt";
        FileWriter fw = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(fw);
        String data;
        for (Shape shape : drawingPanel.engine.getShapes()) {
            data = shape.getData();
            bw.write(data);
            System.out.println(data);
            bw.newLine();
        }
        bw.flush();
    }

    private void showLoadInputDialog(DrawingPanel drawingPanel) throws IOException {

        String path = "", shape, type;
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setAcceptAllFileFilterUsed(false);
        j.setDialogTitle("Select a .txt file");

        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt file", "txt");
        j.addChoosableFileFilter(restrict);

        int r = j.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION)
            path = j.getSelectedFile().getAbsolutePath();
        String[] data;
        FileReader fr = new FileReader(path);
        BufferedReader bf = new BufferedReader(fr);
        while ((shape = bf.readLine()) != null) {
            System.out.println(shape);
            data = shape.split(",");
            type = data[0];
            switch (type) {
                case "Line" -> {
                    try {
                        int x1 = Integer.parseInt(data[1]);
                        int y1 = Integer.parseInt(data[2]);
                        int x2 = Integer.parseInt(data[3]);
                        int y2 = Integer.parseInt(data[4]);
                        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for point coordinates",
                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else if (x1 == x2 && y1 == y2) {
                            JOptionPane.showMessageDialog(this, "Please enter different points", "Invalid Input",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        Point position1 = new Point(x1, y1);
                        Point position2 = new Point(x2, y2);
                        Line line = new Line();
                        line.setPosition1(position1);
                        line.setPosition2(position2);
                        line.setColor(Color.BLACK);
                        drawingPanel.addShape(line);
                        comboBox1.addItem(line);
                    } catch (IndexOutOfBoundsException e) {
                        JOptionPane.showMessageDialog(this, "Insufficient data for Line drawing in: " + shape, "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case "Circle" -> {
                    try {
                        int x = Integer.parseInt(data[1]);
                        int y = Integer.parseInt(data[2]);
                        double radius = Double.parseDouble(data[3]);
                        if (x < 0 || y < 0 || radius <= 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for X, Y, and Radius.",
                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Point position = new Point(x, y);
                        Map<String, Double> properties = new HashMap<>();
                        properties.put("radius", radius);
                        Circle circle = new Circle();
                        circle.setPosition(position);
                        circle.setProperties(properties);
                        circle.getFillColor(Color.WHITE);
                        circle.setColor(Color.BLACK);
                        drawingPanel.addShape(circle);
                        comboBox1.addItem(circle);
                    } catch (IndexOutOfBoundsException e) {
                        JOptionPane.showMessageDialog(this, "Insufficient data for Circle drawing in: " + shape, "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case "Square" -> {
                    try {
                        int x = Integer.parseInt(data[1]);
                        int y = Integer.parseInt(data[2]);
                        double length = Double.parseDouble(data[3]);
                        if (x < 0 || y < 0 || length <= 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for X, Y, and length.",
                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Point position = new Point(x, y);
                        Map<String, Double> properties = new HashMap<>();
                        properties.put("Length", length);
                        Square square = new Square();
                        square.setPosition(position);
                        square.setProperties(properties);
                        square.getFillColor(Color.WHITE);
                        square.setColor(Color.BLACK);
                        drawingPanel.addShape(square);
                        comboBox1.addItem(square);
                    } catch (IndexOutOfBoundsException e) {
                        JOptionPane.showMessageDialog(this, "Insufficient data for Square drawing in: " + shape, "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case "Rectangle" -> {
                    try {
                        int x = Integer.parseInt(data[1]);
                        int y = Integer.parseInt(data[2]);
                        double height = Double.parseDouble(data[3]);
                        double width = Double.parseDouble(data[4]);
                        if (x < 0 || y < 0 || height <= 0 || width <= 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for X, Y, height and width.",
                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Point position = new Point(x, y);
                        Map<String, Double> properties = new HashMap<>();
                        properties.put("Height", height);
                        properties.put("Width", width);
                        Rectangle rectangle = new Rectangle();
                        rectangle.setPosition(position);
                        rectangle.setProperties(properties);
                        rectangle.setColor(Color.BLACK);
                        rectangle.getFillColor(Color.WHITE);
                        drawingPanel.addShape(rectangle);
                        comboBox1.addItem(rectangle);
                    } catch (IndexOutOfBoundsException e) {
                        JOptionPane.showMessageDialog(this, "Insufficient data for Rectangle drawing in: " + shape, "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case null, default ->
                        JOptionPane.showMessageDialog(this, "Incorrect Shape", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showMoveInputDialog(DrawingPanel drawingPanel) {
        Shape shape = (Shape) comboBox1.getSelectedItem();
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        switch (shape) {
            case null ->
                    JOptionPane.showMessageDialog(this, "Please select a shape", "Null Input", JOptionPane.ERROR_MESSAGE);
            case Line line -> {
                try {
                    JTextField x2Field = new JTextField();
                    JTextField y2Field = new JTextField();

                    inputPanel.add(new JLabel("Delta X1:"));
                    inputPanel.add(x2Field);
                    inputPanel.add(new JLabel("Delta Y1:"));
                    inputPanel.add(y2Field);

                    int result = JOptionPane.showConfirmDialog(this, inputPanel,
                            "Enter Line Position", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        int deltaX = Integer.parseInt(x2Field.getText());
                        int deltaY = Integer.parseInt(y2Field.getText());
                        int newX1 = line.getPosition1().x + deltaX;
                        int newY1 = line.getPosition1().y + deltaY;
                        int newX2 = line.getPosition2().x + deltaX;
                        int newY2 = line.getPosition2().y + deltaY;
                        Point newPosition1 = new Point(newX1, newY1);
                        Point newPosition2 = new Point(newX2, newY2);
                        line.setPosition1(newPosition1);
                        line.setPosition2(newPosition2);
                        drawingPanel.changeDimension();
                    }
                }  catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
            case Circle circle -> {
                try {
                    JTextField xField = new JTextField();
                    JTextField yField = new JTextField();

                    inputPanel.add(new JLabel("New X:"));
                    inputPanel.add(xField);
                    inputPanel.add(new JLabel("New Y:"));
                    inputPanel.add(yField);

                    int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter new x and y", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        int x = Integer.parseInt(xField.getText());
                        int y = Integer.parseInt(yField.getText());

                        if (x < 0 || y < 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for centre coordinates", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Point position = new Point(x, y);
                            circle.setPosition(position);
                            drawingPanel.changeDimension();
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
            case Square square -> {
                try {
                    JTextField xField = new JTextField();
                    JTextField yField = new JTextField();

                    inputPanel.add(new JLabel("New X:"));
                    inputPanel.add(xField);
                    inputPanel.add(new JLabel("New Y:"));
                    inputPanel.add(yField);

                    int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter new x and y", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        int x = Integer.parseInt(xField.getText());
                        int y = Integer.parseInt(yField.getText());

                        if (x < 0 || y < 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for coordinates", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Point position = new Point(x, y);
                            square.setPosition(position);
                            drawingPanel.changeDimension();
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
            case Rectangle rectangle -> {
                try {
                    JTextField xField = new JTextField();
                    JTextField yField = new JTextField();

                    inputPanel.add(new JLabel("New X:"));
                    inputPanel.add(xField);
                    inputPanel.add(new JLabel("New Y:"));
                    inputPanel.add(yField);

                    int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter new x and y", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        int x = Integer.parseInt(xField.getText());
                        int y = Integer.parseInt(yField.getText());

                        if (x < 0 || y < 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for coordinates", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Point position = new Point(x, y);
                            rectangle.setPosition(position);
                            rectangle.getFillColor(Color.WHITE);
                            rectangle.setColor(Color.BLACK);
                            drawingPanel.changeDimension();
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + shape);
        }
    }

    private void showResizeInputDialog(DrawingPanel drawingPanel) {
        Shape shape = (Shape) comboBox1.getSelectedItem();
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        switch (shape) {
            case null ->
                    JOptionPane.showMessageDialog(this, "Please select a shape", "Null Input", JOptionPane.ERROR_MESSAGE);
            case Line line -> {
                JTextField x2Field = new JTextField();
                JTextField y2Field = new JTextField();

                inputPanel.add(new JLabel("New X2:"));
                inputPanel.add(x2Field);
                inputPanel.add(new JLabel("New Y2:"));
                inputPanel.add(y2Field);

                int result = JOptionPane.showConfirmDialog(this, inputPanel,
                        "Enter Line Properties", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int x2 = Integer.parseInt(x2Field.getText());
                        int y2 = Integer.parseInt(y2Field.getText());

                        if (x2 < 0 || y2 < 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for X, Y, and Radius.",
                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else if (x2 == line.getPosition1().x && y2 == line.getPosition1().y) {
                            JOptionPane.showMessageDialog(this, "Please enter different points", "Invalid Input",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            Point position2 = new Point(x2, y2);
                            line.setPosition2(position2);
                            drawingPanel.changeDimension();
                        }
                    }  catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            case Circle circle -> {
                JTextField radiusField = new JTextField();

                inputPanel.add(new JLabel("New radius:"));
                inputPanel.add(radiusField);

                int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter new radius", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        double radius = Double.parseDouble(radiusField.getText());

                        if (radius <= 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for radius", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Map<String, Double> properties = new HashMap<>();
                            properties.put("radius", radius);
                            circle.setProperties(properties);
                            drawingPanel.changeDimension();
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            case Square square -> {
                JTextField lengthField = new JTextField();

                inputPanel.add(new JLabel("New length:"));
                inputPanel.add(lengthField);

                int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter new length", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        double length = Double.parseDouble(lengthField.getText());

                        if (length <= 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for length", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Map<String, Double> properties = new HashMap<>();
                            properties.put("Length", length);
                            square.setProperties(properties);
                            drawingPanel.changeDimension();
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            case Rectangle rectangle -> {
                JTextField heightField = new JTextField();
                JTextField widthField = new JTextField();

                inputPanel.add(new JLabel("New height:"));
                inputPanel.add(heightField);
                inputPanel.add(new JLabel("New width:"));
                inputPanel.add(widthField);


                int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter new length", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        double height = Double.parseDouble(heightField.getText());
                        double width = Double.parseDouble(widthField.getText());

                        if (height <= 0 || width <= 0) {
                            JOptionPane.showMessageDialog(this, "Please enter positive values for height and width", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Map<String, Double> properties = new HashMap<>();
                            properties.put("Height", height);
                            properties.put("Width", width);
                            rectangle.setProperties(properties);
                            drawingPanel.changeDimension();
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + shape);
        }
    }

    private void showDeleteInputDialog(DrawingPanel drawingPanel) {
        Shape shape = (Shape) comboBox1.getSelectedItem();
        if (shape != null) {
            drawingPanel.removeShape(shape);
            comboBox1.removeItem(shape);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a shape", "Null Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showColorizeInputDialog(DrawingPanel drawingPanel) {
        Shape shape = (Shape) comboBox1.getSelectedItem();
        if (shape != null) {
            Color selectedColor = JColorChooser.showDialog(this, "Choose Color", Color.WHITE);
            System.out.println(selectedColor);
            if (shape instanceof Line) {
                shape.setColor(selectedColor);
                drawingPanel.changeColor();
            } else if (selectedColor != null) {
                String[] options = {"Fill", "Outline"};
                int choice = JOptionPane.showOptionDialog(this, "Would you like to fill the shape or change the outline color?",
                        "Fill or Outline", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (choice == 0) {
                    shape.getFillColor(selectedColor);
                    drawingPanel.changeColor();
                } else if (choice == 1) {
                    shape.setColor(selectedColor);
                    drawingPanel.changeColor();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No selected color", "exit without choice", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a shape", "Null Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showCircleInputDialog(DrawingPanel drawingPanel) {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();
        JTextField radiusField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("X:"));
        inputPanel.add(xField);
        inputPanel.add(new JLabel("Y:"));
        inputPanel.add(yField);
        inputPanel.add(new JLabel("Radius:"));
        inputPanel.add(radiusField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Enter Circle Properties", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                double radius = Double.parseDouble(radiusField.getText());

                if (x < 0 || y < 0 || radius <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter positive values for X, Y, and Radius.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Point position = new Point(x, y);
                Map<String, Double> properties = new HashMap<>();
                properties.put("radius", radius);
                Circle circle = new Circle();
                circle.setPosition(position);
                circle.setProperties(properties);
                circle.getFillColor(Color.WHITE);
                circle.setColor(Color.BLACK);
                drawingPanel.addShape(circle);
                comboBox1.addItem(circle);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showLineInputDialog(DrawingPanel drawingPanel) {
        JTextField x1Field = new JTextField();
        JTextField y1Field = new JTextField();
        JTextField x2Field = new JTextField();
        JTextField y2Field = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("X1:"));
        inputPanel.add(x1Field);
        inputPanel.add(new JLabel("Y1:"));
        inputPanel.add(y1Field);
        inputPanel.add(new JLabel("X2:"));
        inputPanel.add(x2Field);
        inputPanel.add(new JLabel("Y2:"));
        inputPanel.add(y2Field);
        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Enter Line Properties", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int x1 = Integer.parseInt(x1Field.getText());
                int y1 = Integer.parseInt(y1Field.getText());
                int x2 = Integer.parseInt(x2Field.getText());
                int y2 = Integer.parseInt(y2Field.getText());

                if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
                    JOptionPane.showMessageDialog(this, "Please enter positive values for X, Y, and Radius.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (x1 == x2 && y1 == y2) {
                    JOptionPane.showMessageDialog(this, "Please enter different points", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                }
                Point position1 = new Point(x1, y1);
                Point position2 = new Point(x2, y2);
                Line line = new Line();
                line.setPosition1(position1);
                line.setPosition2(position2);
                line.setColor(Color.BLACK);
                drawingPanel.addShape(line);
                comboBox1.addItem(line);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showSquareInputDialog(DrawingPanel drawingPanel) {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();
        JTextField lengthField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("X:"));
        inputPanel.add(xField);
        inputPanel.add(new JLabel("Y:"));
        inputPanel.add(yField);
        inputPanel.add(new JLabel("length:"));
        inputPanel.add(lengthField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Enter Square Properties", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                double length = Double.parseDouble(lengthField.getText());

                if (x < 0 || y < 0 || length <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter positive values for X, Y, and Length.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Point position = new Point(x, y);
                Map<String, Double> properties = new HashMap<>();
                properties.put("Length", length);
                Square square = new Square();
                square.setPosition(position);
                square.setProperties(properties);
                square.getFillColor(Color.WHITE);
                square.setColor(Color.BLACK);
                drawingPanel.addShape(square);
                comboBox1.addItem(square);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showRectangleInputDialog(DrawingPanel drawingPanel) {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField widthField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("X:"));
        inputPanel.add(xField);
        inputPanel.add(new JLabel("Y:"));
        inputPanel.add(yField);
        inputPanel.add(new JLabel("Height:"));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("Width: "));
        inputPanel.add(widthField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Enter Rectangle Properties", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                double height = Double.parseDouble(heightField.getText());
                double width = Double.parseDouble(widthField.getText());

                if (x < 0 || y < 0 || height <= 0 || width <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter positive values for X, Y, height and width.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Point position = new Point(x, y);
                Map<String, Double> properties = new HashMap<>();
                properties.put("Height", height);
                properties.put("Width", width);
                Rectangle rectangle = new Rectangle();
                rectangle.setPosition(position);
                rectangle.setProperties(properties);
                rectangle.getFillColor(Color.WHITE);
                rectangle.setColor(Color.BLACK);
                drawingPanel.addShape(rectangle);
                comboBox1.addItem(rectangle);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
