package org.example.breakout;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends GraphicsItem {

    public Paddle() {
        // Setting the paddle's width and height (relative to canvas size)
        this.width = 0.2;  // 20% of the canvas width
        this.height = 0.05; // 5% of the canvas height

        // Initial position (bottom center of the screen)
        this.x = 0.4; // Centered horizontally
        this.y = 0.95; // Near the bottom of the canvas
    }

    public void movePaddle(double x) {
        // Set the x position based on the mouse's x-coordinate, ensuring the paddle stays centered
        this.x = (x / canvasWidth) - (this.width / 2);

        // Clamp the x position so the paddle stays within canvas bounds
        if (this.x < 0) this.x = 0;
        if (this.x + this.width > 1) this.x = 1 - this.width;
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Draw the Paddle
        gc.setFill(Color.BLUE);
        gc.fillRect(x * canvasWidth, y * canvasHeight, width * canvasWidth, height * canvasHeight);
    }
}
