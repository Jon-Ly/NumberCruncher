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

    private ArrayList<NumberView> collectableNumbers;

    private Paint paint;
    private Rect player;
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
            collectableNumbers.add(new NumberView(context, this.displayMetrics, rand.nextInt(10)));
        }

        player = new Rect(displayMetrics.widthPixels/2 - 50, displayMetrics.heightPixels - 300,
                            displayMetrics.widthPixels/2 + 50, displayMetrics.heightPixels - 200);
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

        canvas.drawRect(player, playerPaint);

        for (NumberView num: collectableNumbers) {

            Rect bound = num.getBound();
            paint.setAlpha(0);
            canvas.drawRect(bound, paint);
            bound.offsetTo(bound.left, bound.top + 10);
            paint.setTextSize(50);
            paint.setColor(Color.GREEN);
            canvas.drawText(num.getValue() + "", bound.left+bound.width()/4, bound.top+bound.height()/2+10, paint);

            if(bound.top > displayMetrics.heightPixels){
                bound.offsetTo(displayMetrics.widthPixels - rand.nextInt(displayMetrics.widthPixels), - rand.nextInt(displayMetrics.heightPixels));
                num.setValue(rand.nextInt(10));
            }
        }

        h.postDelayed(r, FRAME_RATE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean handled = false;

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                isPlayerMove = this.isPlayerTapped(event);
                System.out.println("Action Down: " + isPlayerMove);
                handled = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                System.out.println("Action Pointer Down");
                handled = true;
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Action Move");
                System.out.println(isPlayerMove);
                if(isPlayerMove)
                    player.offsetTo((int)event.getX()-player.width()/2, player.top);
                handled = true;
                break;
            case MotionEvent.ACTION_UP:
                isPlayerMove = false;
                System.out.println("Action Up");
                handled = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                System.out.println("Action Pointer Up");
                handled = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("Action Cancel");
                handled = true;
                break;
        }

        return super.onTouchEvent(event) || handled;
    }

    public boolean isPlayerTapped(MotionEvent event){
        if(event.getX() > player.left && event.getX() < player.right && event.getY() < player.bottom
                && event.getY() > player.top) {
            return true;
        }
        return false;
    }

    public boolean isNumberCollision(){
        return false;
    }

}
