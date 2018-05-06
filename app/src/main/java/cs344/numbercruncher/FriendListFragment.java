package cs344.numbercruncher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by Jon on 3/16/2018.
 */

public class FriendListFragment extends Fragment{

    private ArrayList<Friend> friends;

    private RequestQueue queue;
    private String url;

    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.friend_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);

        final SharedPreferences shared_pref = getActivity().getPreferences(Context.MODE_PRIVATE);

        final String username = shared_pref.getString("USERNAME", "");

        url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?username=" + username + "&friends=1";

        friends = new ArrayList<>();

        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout friend_list_linear_layout = getActivity().findViewById(R.id.friend_scroll_layout);

        this.queue = Volley.newRequestQueue(this.getContext());
        StringRequest string_request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        String clean_response = "";

                        for(int i = 0; i < response.length(); i++){
                            if(response.charAt(i) != '[' && response.charAt(i) != ']' &&
                                    response.charAt(i) != '"'){
                                clean_response += response.charAt(i);
                            }
                        }

                        String[] parts = clean_response.split(",");

                        for(int i = 0; i < parts.length; i += 3) {
                            friends.add(new Friend(parts[i], Integer.parseInt(parts[i + 1]),
                                    Integer.parseInt(parts[i + 2])));

                            //Linear layout for each friend
                            LinearLayout friend_information = new LinearLayout(getContext());
                            friend_information.setOrientation(LinearLayout.HORIZONTAL);
                            friend_information.setWeightSum(4);

                            //Objects in the linear layout
                            TextView username_view = new TextView(getContext());
                            TextView wins_view = new TextView(getContext());
                            TextView loss_view = new TextView(getContext());
                            Button challenge_button = new Button(getContext());

                            username_view.setText(friends.get(i/3).getUsername() + "");
                            wins_view.setText("WINS: " + friends.get(i/3).getWins() + "");
                            loss_view.setText("LOSSES: "+ friends.get(i/3).getLosses() + "");
                            challenge_button.setText("Challenge");

                            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(0, 100);
                            layout_params.weight = 0.9f;

                            username_view.setLayoutParams(layout_params);
                            wins_view.setLayoutParams(layout_params);
                            loss_view.setLayoutParams(layout_params);

                            layout_params = new LinearLayout.LayoutParams(0, 150);
                            layout_params.weight = 1.3f;

                            challenge_button.setLayoutParams(layout_params);

                            friend_information.addView(username_view);
                            friend_information.addView(wins_view);
                            friend_information.addView(loss_view);
                            friend_information.addView(challenge_button);

                            View view = friend_information;

                            friend_list_linear_layout.addView(view);
                        }
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError er) {
                Toast.makeText(getContext(), "Could not display friends.", Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(string_request);
    }
}
