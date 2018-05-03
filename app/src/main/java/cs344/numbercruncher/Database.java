package cs344.numbercruncher;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by wintow on 5/1/2018.
 */

public class Database {

    private Context context;
    private Activity activity;
    private String url;
    private RequestQueue queue;

    private String successful_login;

    public Database(Context context) {
        this.context = context;
        this.url = "";
    }

    public Database(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void RecordGame(int user_id, int friend_id, int user_score, int friend_score) {
        if (friend_id == -1) {
            url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?userId=" + user_id +
                    "&friendId=" + friend_id + "&userScore=" + user_score + "&friendScore=" + friend_score;
        }else{
            url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?userId=" + user_id
                    + "&userScore=" + user_score;
        }

        this.queue = Volley.newRequestQueue(this.context);
        StringRequest string_request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                    }

                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError er) {
                Toast.makeText(context, "Could not record game information.", Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(string_request);
    }

    public void RegisterUser(String username, String password){

        url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?username=" + username
                    + "&password=" + password + "&register=1";

        this.queue = Volley.newRequestQueue(this.context);
        StringRequest string_request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        if(response.equals("duplicate")){
                            Toast.makeText(context, "That username has been taken already.",
                                            Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "You've been registered! Please login.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError er) {
                Toast.makeText(context, "Could not register, an error occurred", Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(string_request);
    }

    public ArrayList<Friend> GetFriends(String username){

        url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?username=" + username + "&friends=1";

        this.queue = Volley.newRequestQueue(this.context);
        StringRequest string_request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError er) {
                Toast.makeText(context, "Could not display friends.", Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(string_request);

        return new ArrayList<>();
    }

    public boolean Login(String username, String password){

        url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?username=" + username
                    + "&password=" + password + "&check=1";

        boolean successful = false;

        this.queue = Volley.newRequestQueue(this.context);
        StringRequest string_request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        System.out.println(response);
                        SharedPreferences shared_pref = activity.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared_pref.edit();
                        editor.putString("USERNAME ", response);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError er) {
                Toast.makeText(context, "Could not display friends.", Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(string_request);

        return successful;
    }

    public void AcceptFriendRequest(int user_id, int friend_id){
        url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?userId=" + user_id
                + "&friendId=" + friend_id + "&check=1";

        boolean successful = false;

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
