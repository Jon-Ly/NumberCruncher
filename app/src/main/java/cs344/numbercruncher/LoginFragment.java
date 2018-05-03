package cs344.numbercruncher;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Jon on 4/4/2018.
 */

public class LoginFragment extends Fragment {

    private Database db;

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

        final Button loginButton = (Button) getActivity().findViewById(R.id.login_button);
        final CheckBox registerCheckbox = (CheckBox) getActivity().findViewById(R.id.register_checkbox);

        registerCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText(registerCheckbox.isChecked() ? "Register" : "Login");
            }
        });

        db = new Database(getContext(), getActivity());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username_field = (EditText) getActivity().findViewById(R.id.username_field);
                EditText password_field = (EditText) getActivity().findViewById(R.id.password_field);

                String username = username_field.getText().toString();
                String password = password_field.getText().toString();

                if (registerCheckbox.isChecked()) {
                    if (username.length() < 4) {
                        // 4 character long check
                        Toast.makeText(getContext(), "Username must be at least 4 characters long", Toast.LENGTH_SHORT).show();
                    } else if (!Pattern.compile(".*(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d].*").matcher(password).matches()) {
                        // password check
                        Toast.makeText(getContext(), "Password must contain 1 uppercase, 1 lowercase, and 1 number", Toast.LENGTH_LONG).show();
                    } else {
                        //successful register
                        db.RegisterUser(username, password);
                    }
                } else {
                    //Login


                    db.Login(username, password);

//                        Intent gameIntent = new Intent(LoginActivity.this, MainActivity.class);
//                        gameIntent.putExtra("USERNAME", username);
//                        startActivity(gameIntent);
                }
            }

        });
    }
}
