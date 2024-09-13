package org.example.breakout;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem {

    public enum CrushType {
        NoCrush, HorizontalCrush, VerticalCrush
    }

    private static int gridRows = 0;
    private static int gridCols = 0;

    private Color color;

    public static void setGridDimensions(int rows, int cols) {
        gridRows = rows;
        gridCols = cols;
    }

    public Brick(int gridX, int gridY, Color color) {
        this.width = 1.0 / gridCols;
        this.height = 1.0 / gridRows;
        this.x = gridX / (double) gridCols;
        this.y = gridY / (double) gridRows;
        this.color = color;
    }

    public static int getGridCols() {
        return gridCols;
    }

    @Override
    public void draw(GraphicsContext gc) {
        double xPos = x * canvasWidth;
        double yPos = y * canvasHeight;
        double brickWidth = width * canvasWidth;
        double brickHeight = height * canvasHeight;

        gc.setFill(color);
        gc.fillRect(xPos, yPos, brickWidth, brickHeight);

        gc.setStroke(Color.DARKGRAY);
        gc.strokeRect(xPos, yPos, brickWidth, brickHeight);
        gc.strokeLine(xPos, yPos, xPos + brickWidth * 0.1, yPos + brickHeight * 0.1);
        gc.strokeLine(xPos + brickWidth, yPos, xPos + brickWidth * 0.9, yPos + brickHeight * 0.1);
        gc.strokeLine(xPos, yPos + brickHeight, xPos + brickWidth * 0.1, yPos + brickHeight * 0.9);
        gc.strokeLine(xPos + brickWidth, yPos + brickHeight, xPos + brickWidth * 0.9, yPos + brickHeight * 0.9);
    }

    public CrushType checkCrush(double ballTop, double ballBottom, double ballLeft, double ballRight) {
        // Check if the ball intersects with the brick
        boolean intersects = ballLeft < (x + width) && ballRight > x && ballTop < (y + height) && ballBottom > y;

        if (!intersects) {
            return CrushType.NoCrush; // No intersection
        }

        // Determine whether the crush is primarily vertical or horizontal
        boolean horizontalCrush = Math.abs(ballBottom - y) < Math.abs(ballTop - (y + height));
        boolean verticalCrush = Math.abs(ballRight - x) < Math.abs(ballLeft - (x + width));

        if (horizontalCrush) {
            return CrushType.HorizontalCrush;
        } else if (verticalCrush) {
            return CrushType.VerticalCrush;
        } else {
            return CrushType.NoCrush; // No clear crush type
        }
    }

}
