package cs344.numbercruncher;

import android.graphics.Canvas;

/**
 * Created by wintow on 4/6/2018.
 */

/**
 * Interface for all objects in the Game activity.
 */
public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
