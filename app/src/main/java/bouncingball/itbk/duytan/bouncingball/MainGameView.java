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
    Ball ball;
    int sizeBall;
    Display display = MainActivity.display;
    MoveBox moveBox;
    ArrayList<Brick> brickList;
    ArrayList<Ball> ballList;
    ArrayList<Brick> collisionBrickList;
    ArrayList<Ball> collisionBallList;

    public MainGameView(Context context) {
        super(context);

        createBackground_moveBox();
        createBall();
        createBick();

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

        sizeBall=ballList.size();
        for (int i=0;i<sizeBall;i++) {
            ball = ballList.get(i);

            checkBallCollisionBackground();
            checkBallCollisionMovebox();
            ball.moveBall();
            ball.drawBitmap(canvas);
            checkBallCollisionBall(i);
            checkBallCollisionBrick(canvas);

        }
    }

    public void createBick() {
        brickList = new ArrayList<>();
        collisionBrickList = new ArrayList<>();
        for (int i = 0; i<5 ; i++)
            for (int j = 0; j < 9; j++) {
                int temp;
                temp=100*j;
                if (i%2==1) temp-=50;
                Brick brick = new Brick(temp, i*90/2-10, 1000 / 10, 100);
                brickList.add(brick);
            }
    }
    public void createBall() {
        ballList = new ArrayList<>();
        collisionBallList = new ArrayList<>();
        Ball ball = new Ball(display.getWidth()/2, display.getHeight()/2, 15);
        ballList.add(ball);


    }
    public void  createBackground_moveBox(){
        bitmap = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.background);
        moveBox = new MoveBox(display.getWidth() / 2, display.getHeight() - 100, 100, 20);

    }
    public void checkBallCollisionBackground(){
        if (ball.checkCollisionBackground(this)) MainActivity.soundManager.playHit();
        if (ball.checkCollisionBackgroundBelow(this)) {
            collisionBallList.add(ball);
            sizeBall--;
        }
    }
    public void checkBallCollisionMovebox(){
        if (ball.checkCollision(moveBox, false)) {
            MainActivity.soundManager.playHit();
            if (Math.abs(moveBox.getDeltaX()) > Math.abs(ball.getVelocityX())) {
                ball.setX(ball.getX() + moveBox.getDeltaX());
            }
        }

    }
    public void checkBallCollisionBall(int ballcheck) {
        for (int i = 0; i<sizeBall;i++){
            Ball bal= ballList.get(i);

            if ( i!=ballcheck){
                if ((Math.pow(bal.getX() - ball.getX(),2)+(Math.pow(bal.getY()-ball.getY(),2)) <=900)){
                    collisionBallList.add(bal);
                    MainActivity.soundManager.playHitl();
                    sizeBall--;
                }
            }
        }

    }
    public void checkBallCollisionBrick(Canvas canvas) {
        for (int i = 0; i < brickList.size(); i++) {
            Brick brickCheck = brickList.get(i);
            brickCheck.drawb(canvas);
            if (ball.checkCollision(brickCheck)) {
                collisionBrickList.add(brickCheck);
                MainActivity.soundManager.playHit();
                Random ran = new Random();
                int r = ran.nextInt(3);
                if ((r == 2)&& (ballList.size()<4)) {
                    int h=ran.nextInt(200)+400;
                    int w=ran.nextInt(display.getWidth());
                    Ball ball1 = new Ball(h, w, 15);
                    ballList.add(ball1);
                    sizeBall++;
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