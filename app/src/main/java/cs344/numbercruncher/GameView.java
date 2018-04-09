package cs344.numbercruncher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jonathan Ly on 3/12/2018.
 */

public class GameView extends View{

    private DisplayMetrics displayMetrics;
    private Random rand;

    private ArrayList<CircleView> collectableCircleViews;

    private Handler handler;

    private final int FRAME_RATE = 1;

    private boolean isPlayerMove;

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);

        collectableCircleViews = new ArrayList<>();

        isPlayerMove = false;

        handler = new Handler();

        rand = new Random();

        displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        for(int i = 0; i < 10; i++) {
            collectableCircleViews.add(new CircleView(this.getContext()));
            collectableCircleViews.get(i).setY((displayMetrics.heightPixels * rand.nextFloat()));
            collectableCircleViews.get(i).setX(displayMetrics.widthPixels * rand.nextFloat());
        }

        System.out.println(collectableCircleViews);

//        player = new Player(new Rect(displayMetrics.widthPixels/2 - 50, displayMetrics.heightPixels - 300,
//                            displayMetrics.widthPixels/2 + 50, displayMetrics.heightPixels - 200));
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        for(CircleView circ : collectableCircleViews)
            ((RelativeLayout)getRootView().findViewById(R.id.game_area)).addView(circ);
    }

    private Runnable r = new Runnable(){
        @Override
        public void run(){
            invalidate();
        }
    };

    @Override
    public void onDraw(Canvas canvas){
        for(CircleView circ : collectableCircleViews) {
            circ.setY(circ.getY() + 10);

            if(circ.getY() > displayMetrics.heightPixels) {
                circ.setY(-25);
                circ.setX(displayMetrics.widthPixels * rand.nextFloat());
            }
        }

        handler.postDelayed(r, FRAME_RATE);
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
//                    player.getBounds().offsetTo((int)event.getX()-player.getBounds().width()/2, player.getBounds().top);
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
//        if(event.getX() > player.getBounds().left && event.getX() < player.getBounds().right && event.getY() < player.getBounds().bottom
//                && event.getY() > player.getBounds().top) {
//            return true;
//        }
        return false;
    }

}
