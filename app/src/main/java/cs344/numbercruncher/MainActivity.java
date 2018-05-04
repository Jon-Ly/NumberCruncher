package cs344.numbercruncher;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.menu_placeholder, new MenuFragment());
        ft.commit();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        SharedPreferences shared_pref = getPreferences(Context.MODE_PRIVATE);

        shared_pref.edit().clear().commit();
    }
}
