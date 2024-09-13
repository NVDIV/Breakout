package org.example.breakout;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GraphicsItem {

    private Point2D moveVector;
    private double velocity;

    public Ball() {
        this.width = 0.03;
        this.height = this.width;
        this.velocity = 0.75;
        this.moveVector = new Point2D(1, -1).normalize();
    }

    public void setPosition(Point2D position) {
        this.x = position.getX() - (this.width / 2);
        this.y = position.getY() - (this.height / 2);
    }

    public void updatePosition(double deltaTime) {
        this.x += moveVector.getX() * velocity * deltaTime;
        this.y += moveVector.getY() * velocity * deltaTime;
    }

    public void bounceHorizontally() {
        this.moveVector = new Point2D(-moveVector.getX(), moveVector.getY()).normalize();
    }

    public void bounceVertically() {
        this.moveVector = new Point2D(moveVector.getX(), -moveVector.getY()).normalize();
    }

    public void bounceFromPaddle(double hitPosition) {
        hitPosition = Math.max(-1, Math.min(1, hitPosition));

        double minBounceAngle = 30;
        double maxBounceAngle = 60;

        double bounceAngle = minBounceAngle + (maxBounceAngle - minBounceAngle) * (0.5 + 0.5 * hitPosition);
        double angleRadians = Math.toRadians(bounceAngle);

        this.moveVector = new Point2D(Math.sin(angleRadians), -Math.cos(angleRadians)).normalize();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x * canvasWidth, y * canvasHeight, width * canvasWidth, height * canvasHeight);
    }

    public double getTop() {
        return y;
    }

    public double getBottom() {
        return y + height;
    }

    public double getLeft() {
        return x;
    }

    public double getRight() {
        return x + width;
    }
}
