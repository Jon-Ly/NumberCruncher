package cs344.numbercruncher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jonathan Ly on 3/12/2018.
 */

public class GameView extends View{

    private DisplayMetrics displayMetrics;
    private Random rand;

    private ArrayList<Number> collectableNumbers;

    private Paint paint;
    private Player player;
    private Paint playerPaint;

    private Handler h;

    private final int FRAME_RATE = 1;

    private boolean isPlayerMove;

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);

        collectableNumbers = new ArrayList<>();

        isPlayerMove = false;

        paint = new Paint();
        playerPaint = new Paint();
        h = new Handler();

        rand = new Random();

        displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        for(int i = 0; i < 20; i++) {
            int initLeft = rand.nextInt(displayMetrics.widthPixels - 50);
            int initTop = -rand.nextInt((int)(displayMetrics.heightPixels * 1.5));
            collectableNumbers.add(new Number(new Rect(initLeft, initTop, initLeft + 50, initTop + 50), rand.nextInt(10)));
        }

        player = new Player(new Rect(displayMetrics.widthPixels/2 - 50, displayMetrics.heightPixels - 300,
                            displayMetrics.widthPixels/2 + 50, displayMetrics.heightPixels - 200));
    }

    private Runnable r = new Runnable(){
        @Override
        public void run(){
            invalidate();
        }
    };

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.BLUE);
        canvas.drawRect(player.getBounds(), playerPaint);

        for (Number num: collectableNumbers) {
            Rect number = num.getBound();
            paint.setAlpha(0);
            canvas.drawRect(number, paint);
            number.offsetTo(number.left, number.top + 10);
            paint.setTextSize(50);
            paint.setColor(Color.GREEN);
            canvas.drawText(num.getValue() + "", number.left + number.width()/4, number.top + number.height()/2+10, paint);

            if(number.top > displayMetrics.heightPixels){ //reset numbers after it moves off the screen
                number.offsetTo(displayMetrics.widthPixels - rand.nextInt(displayMetrics.widthPixels), - rand.nextInt(displayMetrics.heightPixels));
                num.setValue(rand.nextInt(10));
            }

            if(num.isPlayerCollision(player))
                System.out.println("COLLIDE");
        }

        h.postDelayed(r, FRAME_RATE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean handled = false;

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                isPlayerMove = this.isPlayerTapped(event);
                handled = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                handled = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(isPlayerMove)
                    player.getBounds().offsetTo((int)event.getX()-player.getBounds().width()/2, player.getBounds().top);
                handled = true;
                break;
            case MotionEvent.ACTION_UP:
                isPlayerMove = false;
                handled = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                handled = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                handled = true;
                break;
        }

        return super.onTouchEvent(event) || handled;
    }

    public boolean isPlayerTapped(MotionEvent event){
        if(event.getX() > player.getBounds().left && event.getX() < player.getBounds().right && event.getY() < player.getBounds().bottom
                && event.getY() > player.getBounds().top) {
            return true;
        }
        return false;
    }

    public boolean isNumberCollision(){
        return false;
    }

}
