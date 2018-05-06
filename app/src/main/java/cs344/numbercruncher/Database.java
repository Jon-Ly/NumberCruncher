package cs344.numbercruncher;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Jon on 5/1/2018.
 */

public class Database {

    private Context context;
    private String url;
    private RequestQueue queue;

    public Database(Context context) {
        this.context = context;
        this.url = "";
    }

    public void RecordGame(String user_id, String friend_id, int user_score, int friend_score) {
        if (!friend_id.equals("-1")) {
            url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?userId=" + user_id +
                    "&friendId=" + friend_id + "&userScore=" + user_score + "&friendScore=" + friend_score;
        } else {
            url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?userId=" + user_id
                    + "&userScore=" + user_score;
        }

        System.out.println(user_id + " | " + friend_id + " | " + user_score + "  | " + friend_score);

        this.queue = Volley.newRequestQueue(this.context);
        StringRequest string_request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("INSERTED THE GAME " + response);
                    }

                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError er) {
                Toast.makeText(context, "Could not record game information.", Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(string_request);
    }

    public void AcceptFriendRequest(int user_id, int friend_id) {
        url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?userId=" + user_id
                + "&friendId=" + friend_id + "&check=1";

        this.queue = Volley.newRequestQueue(this.context);
        StringRequest string_request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError er) {
                Toast.makeText(context, "Could not display friends.", Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(string_request);
    }

}
