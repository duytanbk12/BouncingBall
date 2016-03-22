package bouncingball.itbk.duytan.bouncingball;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

public class Ball {

    private float x;
    private float y;
    private float radius;
    private float velocityX;
    private float velocityY;
    private float speedBall;
    private double alpha;
    private Paint paint;
    private Bitmap bitmap;

    public Ball(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        speedBall = 15;
        alpha = Math.PI / 4.0;
        velocityX = (float) (speedBall * Math.sin(alpha));
        velocityY = (float) (speedBall * Math.cos(alpha));
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.ball);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (radius * 4), (int) (radius * 4), false);

        }

    public void moveBall() {
        x = x + velocityX;
        y = y + velocityY;
    }

    public void drawBitmap(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - radius, y - radius, paint);
    }

    public boolean checkCollisionBackground(View view) {
        if (x + radius + velocityX > view.getWidth() || x - radius + velocityX < 0) {
            updateAlpha(-alpha);
            return true;
        } else if  (y - radius + velocityY < 0) {
            updateAlpha(Math.PI - alpha);
            return true;
        }
        return false;
    }

    public boolean checkCollisionBackgroundBelow(View view) {

         if (y + radius + velocityY > view.getHeight() ) {
            return true;
        }
        return false;
    }

    public boolean checkCollision(Box box) {
        boolean check = false;

        PointF locationBall1 = new PointF(x + velocityX, y + radius + velocityY);
        PointF locationBall2 = new PointF(x + velocityX, y - radius + velocityY);
        if (box.inArea(locationBall1) || box.inArea(locationBall2)) {
            moveBall();
            updateAlpha(Math.PI - alpha);
            check = true;
        }
        locationBall1 = new PointF(x + radius + velocityX, y + velocityY);
        locationBall2 = new PointF(x - radius + velocityX, y + velocityY);
        if (box.inArea(locationBall1) || box.inArea(locationBall2)) {
            moveBall();
            updateAlpha(-alpha);
            check = true;
        }
        return check;

    }

    public boolean checkCollision(MoveBox moveBox, boolean top) {
        boolean check = false;

        PointF p1 = new PointF(x + velocityX, y + radius + velocityY);
        PointF p2 = new PointF(x + velocityX, y - radius + velocityY);
        if (moveBox.inArea(p1) || moveBox.inArea(p2)) {
            moveBall();
            double deltaX = (x - (moveBox.getX() + moveBox.getWidth() / 2));
            double sinAlpha = (deltaX * 1.9) / moveBox.getWidth();
            double changeAlpha = Math.asin(sinAlpha) + Math.PI / 18;
            Log.d("GOC ALPHA", "DeltaX : " + (float) deltaX + "--- Alpha : " + (float) (changeAlpha * 180 / Math.PI));
            if (top) updateAlpha(changeAlpha);
            else updateAlpha(Math.PI - changeAlpha);
            check = true;
        }

        return check;
    }

    private void updateAlpha(double changeAlpha) {
        alpha = changeAlpha;
        velocityX = (float) (speedBall * Math.sin(alpha));
        velocityY = (float) (speedBall * Math.cos(alpha));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public float getVelocityX() {
        return velocityX;
    }

}
