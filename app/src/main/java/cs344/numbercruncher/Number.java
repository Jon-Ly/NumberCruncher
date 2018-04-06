package cs344.numbercruncher;

import android.graphics.Rect;

import java.util.Random;

/**
 * Created by wintow on 3/16/2018.
 */

public class Number {

    private int value;
    private int speed;

    private Rect bound;
    private Random rand;

    public Number(Rect rect, int value){
        this.rand = new Random();
        this.value = value;
        this.speed = 8;
        this.bound = rect;
    }

    public boolean isPlayerCollision(Player player){
        return player.getBounds().intersect(this.bound);
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
