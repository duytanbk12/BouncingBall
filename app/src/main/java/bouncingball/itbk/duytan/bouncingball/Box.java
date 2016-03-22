package bouncingball.itbk.duytan.bouncingball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public abstract class Box {

    private float x;
    private float y;
    private float width;
    private float height;
    private Paint paint;

    public Box() {
        this.x = 0;
        this.y = 0;
        this.width = 100;
        this.height = 100;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.RED);
    }

    public boolean inArea(PointF p) {
        if (p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height) {
            return true;
        }
        return false;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    abstract void drawb(Canvas canvas);
}
