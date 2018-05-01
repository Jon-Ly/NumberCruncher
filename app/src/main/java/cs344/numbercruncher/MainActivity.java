package cs344.numbercruncher;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.menu_placeholder, new MenuFragment());
        ft.commit();

        String user = "lyj47", pass = "hello";
        HttpURLConnection urlConnection = null;

        try{
            String link = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?username="+user+"&password="+pass;

            URL url = new URL(link);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                System.out.print(current);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("IT DIDN'T WORK");
        }finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
