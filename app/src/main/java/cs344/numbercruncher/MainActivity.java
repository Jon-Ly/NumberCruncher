package cs344.numbercruncher;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.menu_placeholder, new MenuFragment());
        ft.commit();

        String user = "u", pass = "p";

        System.out.println("hello");

        String link = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?username=u&password=p&getUsers=1";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest string_request = new StringRequest(Request.Method.GET, link,
                new Response.Listener<String>(){

            public void onResponse(String response){
                System.out.println(response);
            }

        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError er){
                er.printStackTrace();
            }
        }
        );

        queue.add(string_request);
        System.out.println(string_request);
    }
}
