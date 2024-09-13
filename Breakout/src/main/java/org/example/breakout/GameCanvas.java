package org.example.breakout;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameCanvas extends Canvas {

    private GraphicsContext gc;
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private boolean running;
    private AnimationTimer gameLoop;
    private long lastFrameTime;

    public GameCanvas(double width, double height) {
        super(width, height);
        GraphicsItem.setCanvasDimensions(width, height);

        gc = this.getGraphicsContext2D();
        paddle = new Paddle();
        ball = new Ball();
        bricks = new ArrayList<>();
        running = false;

        this.setOnMouseMoved(this::handleMouseMoved);
        this.setOnMouseClicked(this::handleMouseClicked);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastFrameTime > 0) {
                    // Calculate time elapsed since the last frame (in seconds)
                    double deltaTime = (now - lastFrameTime) / 1_000_000_000.0;

                    if (running) {
                        // Update the ball's position
                        ball.updatePosition(deltaTime);

                        // Check for bounces from walls
                        if (shouldBallBounceHorizontally()) {
                            ball.bounceHorizontally();
                        }
                        if (shouldBallBounceVertically()) {
                            ball.bounceVertically();
                        }

                        // Check if the ball hits the bottom wall (game over condition)
                        if (ball.getBottom() >= 1) {  // Ball hits the bottom of the screen
                            handleLoss();  // Call the method to handle game over
                        }

                        // Check for paddle collision
                        if (shouldBallBounceFromPaddle()) {
                            ball.bounceVertically(); // Bounce off the paddle
                        }

                        // Check for collisions with bricks
                        checkBrickCollision();
                    }
                }

                // Redraw the canvas
                draw();

                // Update last frame time
                lastFrameTime = now;
            }
        };

        draw();
    }

    private void handleLoss() {
        running = false;  // Stop the game loop
    }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        paddle.movePaddle(mouseEvent.getX());

        if (!running) {
            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double ballY = paddle.getY() - ball.getHeight();
            ball.setPosition(new Point2D(paddleCenterX, ballY));
        }

        draw();
    }

    private void handleMouseClicked(MouseEvent mouseEvent) {
        if (!running) {
            running = true;
            gameLoop.start();
            lastFrameTime = System.nanoTime();
        }
    }

    private boolean shouldBallBounceHorizontally() {
        return ball.getX() <= 0 || (ball.getX() + ball.getWidth()) >= 1;
    }

    private boolean shouldBallBounceVertically() {
        return ball.getY() <= 0;
    }

    private boolean shouldBallBounceFromPaddle() {
        boolean ballAtPaddleLevel = (ball.getBottom() >= paddle.getY());
        boolean ballWithinPaddleWidth = (ball.getRight() >= paddle.getX() && ball.getLeft() <= paddle.getX() + paddle.getWidth());

        return ballAtPaddleLevel && ballWithinPaddleWidth;
    }

    private double calculateHitPosition() {
        double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
        double ballCenterX = ball.getX() + ball.getWidth() / 2;
        return (ballCenterX - paddleCenterX) / (paddle.getWidth() / 2);
    }

    private void checkBrickCollision() {
        List<Brick> bricksToRemove = new ArrayList<>();

        for (Brick brick : bricks) {
            Brick.CrushType crushType = brick.checkCrush(
                    ball.getTop(), ball.getBottom(), ball.getLeft(), ball.getRight()
            );

            if (crushType != Brick.CrushType.NoCrush) {
                switch (crushType) {
                    case HorizontalCrush:
                        ball.bounceHorizontally();
                        break;
                    case VerticalCrush:
                        ball.bounceVertically();
                        break;
                }

                // Add the brick to the removal list
                bricksToRemove.add(brick);
            }
        }

        // Remove bricks after collision is processed
        bricks.removeAll(bricksToRemove);
    }



    // Method to load the level
    public void loadLevel() {
        // Set grid dimensions
        Brick.setGridDimensions(20, 10);  // 20 columns and 10 rows

        bricks = new ArrayList<>();  // Initialize the bricks list

        // Colors for different rows
        Color[] rowColors = new Color[] {
                Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.INDIGO
        };

        // Populate the grid with bricks
        for (int row = 2; row <= 7; row++) {  // Rows 2 to 7 will have bricks
            Color color = rowColors[row - 2];  // Choose color based on row
            for (int col = 0; col < Brick.getGridCols(); col++) {
                // Create and add bricks to the grid
                bricks.add(new Brick(col, row, color));
            }
        }
    }


    public void draw() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        paddle.draw(gc);
        ball.draw(gc);

        for (Brick brick : bricks) {
            brick.draw(gc);
        }
    }
}
