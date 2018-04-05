package cs344.numbercruncher;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jon on 4/4/2018.
 */

public class MenuFragment extends Fragment{

    public MenuFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Button playButton = (Button)getView().findViewById(R.id.play_button);
        Button challengeButton = (Button)getView().findViewById(R.id.challenge_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(getActivity(), GameActivity.class);
                startActivity(gameIntent);
            }
        });

        challengeButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent friendsIntent = new Intent(getActivity(), FriendListFragment.class);
                startActivity(friendsIntent);


                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                FriendListFragment friendFragment = new FriendListFragment();

                ft.add(friendFragment, "").commit();

            }
        }));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }

}
