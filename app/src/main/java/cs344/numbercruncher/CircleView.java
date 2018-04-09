package cs344.numbercruncher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

/**
 * Created by wintow on 3/16/2018.
 */

public class CircleView extends AppCompatImageView {

    private int value;
    private int speed;

    public CircleView(Context context){
        super(context);

        this.setImageResource(R.drawable.ic_black_circle);
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

}
