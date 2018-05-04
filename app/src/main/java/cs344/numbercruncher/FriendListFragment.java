package cs344.numbercruncher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by wintow on 3/16/2018.
 */

public class FriendListFragment extends Fragment{

    private Database db;

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

        db = new Database(getContext());

        ArrayList<Friend> friends = db.GetFriends(shared_pref.getString("USERNAME", ""));

        LinearLayout friend_list_linear_layout = getActivity().findViewById(R.id.friend_list_linear_layout);



    }
}
