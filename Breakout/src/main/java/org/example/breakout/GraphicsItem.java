package org.example.breakout;

import javafx.scene.canvas.GraphicsContext;

public abstract class GraphicsItem {

    // Protected static fields to store the canvas dimensions
    protected static double canvasWidth, canvasHeight;

    // Protected fields for position and size (0 to 1 range)
    protected double x, y;
    protected double width, height;


    // Public setter for canvas dimensions
    public static void setCanvasDimensions(double width, double height) {
        canvasWidth = width;
        canvasHeight = height;
    }

    // Getters and setters for x, y, width, height
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // Abstract method to draw the item
    public abstract void draw(GraphicsContext gc);
}
