package bouncingball.itbk.duytan.bouncingball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MenuView extends View {

    Bitmap menu, play;
    private Paint paint;
    private Bitmap bitmap;

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        paint = new Paint();
//        menu = Bitmap.createScaledBitmap(AssetLoader.menu, AssetLoader.width, AssetLoader.height, false);
//        play = AssetLoader.play;

        bitmap = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.menu);
        bitmap = Bitmap.createScaledBitmap(bitmap, 0, 0, false);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(menu, 0, 0, paint);
//        canvas.drawBitmap(play, (AssetLoader.width - play.getWidth()) / 2,
//                (AssetLoader.height - play.getHeight()) / 2, paint);

        bitmap = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.play);
        bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
    }
}

