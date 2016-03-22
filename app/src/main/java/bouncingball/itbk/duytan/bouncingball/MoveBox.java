package bouncingball.itbk.duytan.bouncingball;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.Display;
import android.view.MotionEvent;

public class MoveBox{

    Display display = MainActivity.display;
    private float x;
    private float y;
    private float width;
    private float height;
    private Paint paint;
    private float previousX;
    private float deltaX = 0;
    private Bitmap bitmap;

    public MoveBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        previousX = x;
        bitmap = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.bat);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)getWidth(), (int)getHeight(), true);
    }

    public void onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                deltaX = currentX - previousX;
                if (getX() + getWidth() + deltaX < display.getWidth() && getX() + deltaX > 0)
                    setX(getX() + deltaX);
                break;
        }
        previousX = currentX;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getX(), getY(), getPaint());

    }

    public boolean inArea(PointF locationBall  ) {
        if (locationBall.x >= x && locationBall.x <= x + getWidth() && locationBall.y >= y && locationBall.y <= y + getHeight()) {
            return true;
        }
        return false;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public Paint getPaint() {
        return paint;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width*2;
    }

    public float getHeight() {
        return height*2;
    }


    public float getDeltaX() {
        return deltaX;
    }

   }


