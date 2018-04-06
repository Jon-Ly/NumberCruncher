package cs344.numbercruncher;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by wintow on 4/6/2018.
 */

public class Player implements GameObject {

    private Rect player;

    public Player(Rect rect){
        super();
        this.player = rect;
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        canvas.drawRect(player, paint);
    }

    @Override
    public void update(){

    }

    public Rect getBounds(){
        return this.player;
    }

}
