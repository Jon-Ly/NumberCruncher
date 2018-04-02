package cs344.numbercruncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by wintow on 3/16/2018.
 */

public class FriendListActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent mainIntent = new Intent(FriendListActivity.this, MainActivity.class);
            startActivity(mainIntent);
            return true;
        }
        return false;
    }
}
