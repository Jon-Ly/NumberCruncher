package cs344.numbercruncher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = (Button)findViewById(R.id.play_button);
        Button challengeButton = (Button)findViewById(R.id.challenge_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });

        challengeButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent friendsIntent = new Intent(MainActivity.this, FriendListFragment.class);
                startActivity(friendsIntent);
            }
        }));
    }
}
