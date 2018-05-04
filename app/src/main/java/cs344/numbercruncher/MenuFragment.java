package cs344.numbercruncher;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jon on 4/4/2018.
 */

public class MenuFragment extends Fragment {

    private TextView greeting_text_view;

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        greeting_text_view = getActivity().findViewById(R.id.greeting_text_view);

        final SharedPreferences shared_pref = getActivity().getPreferences(Context.MODE_PRIVATE);

        if(!shared_pref.getString("USERNAME", "").equals(""))
            greeting_text_view.setText("Welcome " + shared_pref.getString("USERNAME", ""));
        else
            greeting_text_view.setText("Login to Challenge!");

        Button playButton = (Button) getView().findViewById(R.id.play_button);
        final Button challengeButton = (Button) getView().findViewById(R.id.challenge_button);
        Button quitButton = (Button) getView().findViewById(R.id.quit_button);
        Button login_button = (Button) getView().findViewById(R.id.login_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(getActivity(), GameActivity.class);
                startActivity(gameIntent);
            }
        });

        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String logged_in_username = shared_pref.getString("USERNAME", "");

                System.out.println("CURRENT USER: " + logged_in_username);

                if (!logged_in_username.equals("")) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    FriendListFragment friend_list_fragment = new FriendListFragment();
                    ft.addToBackStack("");
                    ft.remove(getFragmentManager().getFragments().get(0));
                    ft.add(R.id.menu_placeholder, friend_list_fragment).commit();
                } else {
                    Toast.makeText(getContext(), "You must register or login to challenge.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                LoginFragment loginFrag = new LoginFragment();
                ft.addToBackStack("");
                ft.remove(getFragmentManager().getFragments().get(0));
                ft.add(R.id.menu_placeholder, loginFrag).commit();
            }
        });
    }
}
