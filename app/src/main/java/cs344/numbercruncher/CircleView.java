package cs344.numbercruncher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import java.util.Random;

/**
 * Created by wintow on 3/16/2018.
 */

public class CircleView extends AppCompatImageView {

    /*
    * Teal = 1
    * Blue = 2
    * Green = 3
    * Purple = 5
    *
    * Yellow = -1
    * Orange = -2
    * Red = -3
    * Black = -5
     */
    private int value;
    private int speed;

    public CircleView(Context context) {
        super(context);

        this.speed = 10;
        this.setRandomValue();
    }

    public void setRandomValue() {
        Random rand = new Random();

        double i = rand.nextDouble();

        if (i < 0.2) {//teal
            this.value = 1;
            this.setImageResource(R.drawable.ic_teal_circle);
        } else if (i < 0.3) {//blue
            this.value = 2;
            this.setImageResource(R.drawable.ic_blue_circle);
        } else if (i < 0.37) {// green
            this.value = 3;
            this.setImageResource(R.drawable.ic_green_circle);
        } else if (i < 0.62) {//yellow
            this.value = -1;
            this.setImageResource(R.drawable.ic_yellow_circle);
        } else if (i < 0.72) {//orange
            this.value = -2;
            this.setImageResource(R.drawable.ic_orange_circle);
        } else if (i < 0.79) {//red
            this.value = -3;
            this.setImageResource(R.drawable.ic_red_circle);
        } else if (i < 0.90) {//black
            this.value = -5;
            this.setImageResource(R.drawable.ic_black_circle);
        } else if (i < 1.00) {//purple
            this.value = 5;
            this.setImageResource(R.drawable.ic_purple_circle);
        }
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

}
