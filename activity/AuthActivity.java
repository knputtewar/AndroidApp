package com.example.hotelandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hotelandroid.R;
import com.example.hotelandroid.fragment.LoginFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportActionBar().hide();

        addLoginScreen();
    }

    private void addLoginScreen() {
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.frameLayout, loginFragment)
            .commit();
    }
}
