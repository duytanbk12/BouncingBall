package bouncingball.itbk.duytan.bouncingball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class MainGameView extends SurfaceView {

    private SurfaceHolder holder;
    private GameThread gameThread;
    private  Bitmap bitmap;
    Display display = MainActivity.display;
    MoveBox moveBox;
    public static   ArrayList<Brick> brickList;
    ArrayList<Ball> ballList;
    ArrayList<Brick> collisionBrickList;
    ArrayList<Ball> collisionBallList;

    public MainGameView(Context context) {
        super(context);
        ballList = new ArrayList<>();
        Ball ball = new Ball(500, 500, 15);
        ballList.add(ball);

        moveBox = new MoveBox(display.getWidth() / 2, display.getHeight() - 100, 100, 20);;

        brickList = new ArrayList<>();
        bitmap = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.background);
        for (int j = 0; j<5 ; j++)
            for (int i = 0; i < 9; i++) {
                int t;
                t=100*i;
                if (j%2==1) t-=50;
                Brick b = new Brick(t, j*90/2-10, 1000 / 10, 100);
                brickList.add(b);
            }
        collisionBrickList = new ArrayList<>();
        collisionBallList = new ArrayList<>();

        gameThread = new GameThread(this);
        holder = this.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameThread.setRunning(true);
                gameThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                gameThread.setRunning(false);
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();

        bitmap = Bitmap.createScaledBitmap(bitmap,getWidth(),getHeight(), false);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        brickList.removeAll(collisionBrickList);
        collisionBrickList.clear();

        ballList.removeAll(collisionBallList);
        collisionBallList.clear();

        moveBox.draw(canvas);

        int t=ballList.size();
        for (int i=0;i<t;i++) {
            Ball ball = ballList.get(i);
            if (ball.checkCollisionBackground(this)) MainActivity.soundManager.playHit();
            if (ball.checkCollisionBackgroundBelow(this)) {
                collisionBallList.add(ball);
                t--;
            }

            if (ball.checkCollision(moveBox, false)) {
                MainActivity.soundManager.playHit();
                if (Math.abs(moveBox.getDeltaX()) > Math.abs(ball.getVelocityX())) {
                    ball.setX(ball.getX() + moveBox.getDeltaX());
                }
            }
            ball.moveBall();
            ball.drawBitmap(canvas);

            for (int a = 0; a<t;a++){
                Ball bal= ballList.get(a);

                if ( a!=i){
                    if ((Math.pow(bal.getX() - ball.getX(),2)+(Math.pow(bal.getY()-ball.getY(),2)) <=900)){
                        collisionBallList.add(bal);
                        MainActivity.soundManager.playHitl();
                        t--;
                    }
                }
            }

            for (int j = 0; j < brickList.size(); j++) {
                Brick b = brickList.get(j);
                b.drawb(canvas);
                if (ball.checkCollision(b)) {
                    collisionBrickList.add(b);
                    MainActivity.soundManager.playHit();
                    Random ran = new Random();
                    int r = ran.nextInt(3);
                    if ((r == 2)&& (ballList.size()<4)) {
                        int h=ran.nextInt(200)+400;
                        int w=ran.nextInt(display.getWidth());
                        Ball ball1 = new Ball(h, w, 15);
                        ballList.add(ball1);
                        t++;
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event){
        moveBox.onTouchEvent(event);
        return true;
    }
}