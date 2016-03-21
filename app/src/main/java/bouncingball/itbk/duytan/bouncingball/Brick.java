package bouncingball.itbk.duytan.bouncingball;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick extends Box {
    private Paint paint;
    private Bitmap bitmap;

    public Brick(float x, float y, float width, float height) {
        super();
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        bitmap = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.brick);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) getWidth(), (int) getHeight(), false);
    }

    @Override
    public void drawb(Canvas canvas) {;
        canvas.drawBitmap(bitmap, getX(), getY(), paint);
    }
}


