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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jonathan Ly on 3/12/2018.
 */

public class GameView extends View{

    private final int PHASE_1_SCORE = 750;
    private final int PHASE_2_SCORE = 1500;
    private final int PHASE_3_SCORE = 2250;

    //counter & score
    private TextView score_view;
    private TextView counter_view;
    private int counter_reset;

    //display metrics
    private DisplayMetrics display_metrics;
    private Random rand;

    // circles
    private ArrayList<CircleView> collectable_circle_views;

    private ImageView player;

    // thread
    private Handler handler;
    private final int FRAME_RATE = 1;

    private boolean is_player_move;
    private boolean is_game_over;

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);

        counter_reset = 0;

        handler = new Handler();
        rand = new Random();

        collectable_circle_views = new ArrayList<>();

        is_player_move = false;
        is_game_over = false;

        display_metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(display_metrics);

        for(int i = 0; i < 12; i++) {
            collectable_circle_views.add(new CircleView(this.getContext()));
            collectable_circle_views.get(i).setY((-display_metrics.heightPixels * rand.nextFloat()));
            collectable_circle_views.get(i).setX(display_metrics.widthPixels * rand.nextFloat());
        }

        player = new ImageView(this.getContext());
        player.setImageResource(R.drawable.ic_player);
        player.setScaleType(ImageView.ScaleType.FIT_CENTER);
        player.setAdjustViewBounds(true);
        player.setY((int)(display_metrics.heightPixels * 0.7));
        player.setX(display_metrics.widthPixels/2);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (CircleView circ : collectable_circle_views)
            ((RelativeLayout) getRootView().findViewById(R.id.game_area)).addView(circ);

        ((RelativeLayout) getRootView().findViewById(R.id.game_area)).addView(player);

        score_view = getRootView().findViewById(R.id.score);
        counter_view = getRootView().findViewById(R.id.counter);

        score_view.setText("0");
        counter_view.setText("30");
    }

    private Runnable r = new Runnable(){
        @Override
        public void run(){
            invalidate();
        }
    };

    @Override
    public void onDraw(Canvas canvas){
        for(CircleView circ : collectable_circle_views) {
            circ.setY(circ.getY() + circ.getSpeed());

            if(isPlayerCollision(circ)){
                counter_view.setText((Integer.parseInt(counter_view.getText().toString()) + circ.getValue()) + "");
            }
            if(Integer.parseInt(counter_view.getText().toString()) <= 0){
                is_game_over = true;
                return;
            }
            if(circ.getY() > display_metrics.heightPixels || isPlayerCollision(circ)) {
                circ.setY(-25);
                circ.setX(display_metrics.widthPixels * rand.nextFloat());
                circ.setRandomValue();
            }
            if(Integer.parseInt(score_view.getText().toString()) < 25){
                circ.setSpeed((int)(display_metrics.heightPixels * 0.009));
            }
            if(Integer.parseInt(score_view.getText().toString()) == PHASE_1_SCORE){ //phase 1
                circ.setSpeed((int)(display_metrics.heightPixels * 0.012));
            }
            if(Integer.parseInt(score_view.getText().toString()) == PHASE_2_SCORE){ //phase 2
                circ.setSpeed((int)(display_metrics.heightPixels * 0.015));
            }
        }

        score_view.setText((Integer.parseInt(score_view.getText().toString()) + 1) + "");
        counter_reset++;

        if(counter_reset >= 25 && Integer.parseInt(score_view.getText().toString()) < PHASE_1_SCORE){
            counter_view.setText((Integer.parseInt(counter_view.getText().toString()) - 1) + "");
            counter_reset = 0;
        }else if(counter_reset >= 20 && Integer.parseInt(score_view.getText().toString()) > PHASE_1_SCORE
                  && Integer.parseInt(score_view.getText().toString()) < PHASE_2_SCORE){
            counter_view.setText((Integer.parseInt(counter_view.getText().toString()) - 1) + "");
            counter_reset = 0;
        }else if(counter_reset >= 15 && Integer.parseInt(score_view.getText().toString()) > PHASE_2_SCORE){
            counter_view.setText((Integer.parseInt(counter_view.getText().toString()) - 1) + "");
            counter_reset = 0;
        }else if(counter_reset >= 12 && Integer.parseInt(score_view.getText().toString()) > PHASE_3_SCORE){
            counter_view.setText((Integer.parseInt(counter_view.getText().toString()) - 1) + "");
            counter_reset = 0;
        }

        handler.postDelayed(r, FRAME_RATE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean handled = false;

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                is_player_move = this.isPlayerTapped(event) && !is_game_over;
                handled = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                handled = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(is_player_move)
                    player.setX((int)event.getX()-player.getWidth()/2);
                handled = true;
                break;
            case MotionEvent.ACTION_UP:
                is_player_move = false;
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

    public boolean isPlayerCollision(CircleView circ){

        Rect circ_temp_rect = new Rect((int)(circ.getX()), (int)(circ.getY()), (int)(circ.getX() + circ.getWidth()), (int)(circ.getY() + circ.getHeight()));
        Rect player_rect = new Rect((int)(player.getX()), (int)(player.getY()), (int)(player.getX() + player.getWidth()), (int)(player.getY() + player.getHeight()));

        return player_rect.intersect(circ_temp_rect);
    }

    public boolean isPlayerTapped(MotionEvent event){
        if(event.getX() > player.getX() && event.getX() < player.getX() + player.getWidth() && event.getY() < player.getY() + player.getHeight()
                && event.getY() > player.getY()) {
            return true;
        }
        return false;
    }

}
