package cs344.numbercruncher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverDialog extends Dialog {

    private Button okay_button;
    private Activity activity;
    private int score;
    private String username;
    private Database db;

    public GameOverDialog(Activity activity, int score, String username){
        super(activity);
        this.activity = activity;
        this.score = score;
        this.username = username;
        this.db = new Database(getContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gameover_dialog);

        okay_button = (Button) findViewById(R.id.okay_button);

        TextView gameover_message = (TextView) findViewById(R.id.gameover_message);
        gameover_message.setText("Your final score: " + score);

        okay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordGame();
                Intent gameIntent = new Intent(activity, MainActivity.class);
                getContext().startActivity(gameIntent);
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.RecordGame();
        Intent gameIntent = new Intent(activity, MainActivity.class);
        getContext().startActivity(gameIntent);
    }

    public void RecordGame(){
        if(!username.equals("")) {
            db.RecordGame(username, "-1", score, 0);
        }else{
            Toast.makeText(getContext(), "Login to record your games!", Toast.LENGTH_SHORT).show();
        }
    }
}
