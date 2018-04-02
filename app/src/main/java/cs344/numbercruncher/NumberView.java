package cs344.numbercruncher;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Random;

/**
 * Created by wintow on 3/16/2018.
 */

public class NumberView extends View{

    private int value;
    private int speed;
    private int width;
    private int initLeft;
    private int initTop;
    private final int OFFSET = 50;

    private Rect bound;
    private Random rand;

    public NumberView(Context context, DisplayMetrics displayMetrics, int value){
        super(context);
        this.rand = new Random();
        this.value = value;
        this.speed = 8;
        this.width = 50;
        this.initLeft = rand.nextInt(displayMetrics.widthPixels - 50);
        this.initTop = -rand.nextInt((int)(displayMetrics.heightPixels * 1.5));
        this.bound = new Rect(this.initLeft, this.initTop, this.initLeft + 50, this.initTop + 50);
    }

    public boolean isCollision(View view){
        if(view.getX() > this.bound.left && view.getX() < this.bound.right && view.getY() > this.bound.top &&
                view.getY() < this.bound.bottom){
            return true;
        }

        return false;
    }

    public int getValue(){
        return this.value;
    }
    public void setValue(int newValue){
        this.value = newValue;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    public Rect getBound(){
        return this.bound;
    }

}
