package bouncingball.itbk.duytan.bouncingball;

import android.graphics.Canvas;

public class GameThread extends Thread {

    private int fps = 40;
    private MainGameView view;
    private boolean running;

    public GameThread(MainGameView view) {
        this.view = view;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    if (c != null) view.draw(c);
                }

            } finally {
                if (c != null) view.getHolder().unlockCanvasAndPost(c);
            }

            try {
                this.sleep(1000 / fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}


