package cs344.numbercruncher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Pattern;

/**
 * Created by Jon on 4/4/2018.
 */

public class LoginFragment extends Fragment {

    private Database db;
    private String url;
    private RequestQueue queue;
    private boolean is_successful;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Button login_button = (Button) getActivity().findViewById(R.id.login_button);
        final CheckBox register_checkbox = (CheckBox) getActivity().findViewById(R.id.register_checkbox);

        register_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_button.setText(register_checkbox.isChecked() ? "Register" : "Login");
            }
        });

        db = new Database(getContext());

        is_successful = false;

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username_field = (EditText) getActivity().findViewById(R.id.username_field);
                EditText password_field = (EditText) getActivity().findViewById(R.id.password_field);

                final String username = username_field.getText().toString();
                final String password = password_field.getText().toString();

                if (register_checkbox.isChecked()) {
                    if (username.length() < 4) {
                        // 4 character long check
                        Toast.makeText(getContext(), "Username must be at least 4 characters long", Toast.LENGTH_SHORT).show();
                    } else if (!Pattern.compile(".*(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d].*").matcher(password).matches()) {
                        // password check
                        Toast.makeText(getContext(), "Password must contain 1 uppercase, 1 lowercase, and 1 number", Toast.LENGTH_LONG).show();
                    } else {
                        //successful register
                        url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?username=" + username
                                + "&password=" + password + "&register=1";

                        queue = Volley.newRequestQueue(getContext());
                        StringRequest string_request = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {

                                    public void onResponse(String response) {
                                        if (response.equals("duplicate")) {
                                            Toast.makeText(getContext(), "That username has been taken already.",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "You've been registered! Please login.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError er) {
                                Toast.makeText(getContext(), "Could not register, an error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                        );
                        queue.add(string_request);
                    }
                } else {
                    //Login
//                    if (db.Login(username, password) || db.Login(username, password)) {
//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        MenuFragment menu_fragment = new MenuFragment();
//                        ft.addToBackStack("");
//                        ft.remove(getFragmentManager().getFragments().get(0));
//                        ft.add(R.id.menu_placeholder, menu_fragment).commit();
//                    }

                    url = "http://webdev.cs.uwosh.edu/students/lyj47/procedures.php?username=" + username
                            + "&password=" + password + "&check=1";

                    queue = Volley.newRequestQueue(getContext());
                    StringRequest string_request = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(!response.contains("unsuccessful")) {
                                        SharedPreferences shared_pref = getActivity().getPreferences(Context.MODE_PRIVATE);

                                        SharedPreferences.Editor editor = shared_pref.edit();

                                        editor.putString("USERNAME", response).commit();

                                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                        MenuFragment menu_frag = new MenuFragment();
                                        ft.addToBackStack("");
                                        ft.remove(getFragmentManager().getFragments().get(0));
                                        ft.add(R.id.menu_placeholder, menu_frag).commit();
                                    }else{
                                        Toast.makeText(getContext(), "Your information is incorrect.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError er) {
                            er.printStackTrace();
                            Toast.makeText(getContext(), "Could not login.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    );
                    queue.add(string_request);

                }
            }
        });
    }
}
