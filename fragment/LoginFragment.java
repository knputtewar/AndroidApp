package com.example.hotelandroid.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotelandroid.activity.MainActivity;
import com.example.hotelandroid.R;

import com.example.hotelandroid.utils.Constants;
import com.example.hotelandroid.utils.Utils;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment {

    @BindView(R.id.editEmail) EditText editEmail;
    @BindView(R.id.editPassword) EditText editPassword;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.buttonLogin)
    public void onLoginClicked() {
        final String email = editEmail.getText().toString();
        final String password = editPassword.getText().toString();

        if (email.length() == 0) {
            Toast.makeText(getActivity(), "Enter email", Toast.LENGTH_SHORT).show();
        } else if (password.length() == 0) {
            Toast.makeText(getActivity(), "Enter password", Toast.LENGTH_SHORT).show();
        } else {

            JsonObject body = new JsonObject();
            body.addProperty("email", email);
            body.addProperty("password", password);

            final String url = Utils.getUrl(Constants.ROUTE_USER + "/signin");
            Ion.with(getActivity())
                    .load(url)
                    .setJsonObjectBody(body)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                if (result.get("status").getAsString().equals("success")) {
                                    Toast.makeText(getActivity(), "welcome ...", Toast.LENGTH_SHORT).show();

                                    JsonObject data = result.get("data").getAsJsonObject();
                                    int custId = data.get("custId").getAsInt();

                                    SharedPreferences preferences =
                                            PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    preferences
                                            .edit()
                                            .putBoolean("login_status", true)
                                            .putInt("user_id ", custId )
                                            .commit();

                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }

    @OnClick(R.id.buttonRegister)
    public void onRegistrationClicked() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, new RegisterFragment())
                .commit();
    }
}