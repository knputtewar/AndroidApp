package com.example.hotelandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotelandroid.R;
import com.example.hotelandroid.utils.Constants;
import com.example.hotelandroid.utils.Utils;
//import com.example.mytour.R;
//import com.example.mytour.utils.Constants;
//import com.example.mytour.utils.Utils;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterFragment extends Fragment {

    @BindView(R.id.editName) EditText editName;
    @BindView(R.id.editPhone)EditText editPhone;
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
        View view = inflater.inflate(R.layout.fragment_register, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.buttonBack)
    public void onButtonBackClicked() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, new LoginFragment())
                .commit();
    }

    @OnClick(R.id.buttonRegister)
    public void onButtonRegisterClicked() {
        final String custname = editName.getText().toString();
        final String Mobno =editPhone.getText().toString();
        final String email = editEmail.getText().toString();
        final String password = editPassword.getText().toString();



        if (custname.length() == 0) {
            Toast.makeText(getActivity(), "Enter  custname", Toast.LENGTH_SHORT).show();
        } else if (Mobno.length() == 0) {
            Toast.makeText(getActivity(), "Enter", Toast.LENGTH_SHORT).show();

        } else if (email.length() == 0) {
            Toast.makeText(getActivity(), "Enter email", Toast.LENGTH_SHORT).show();
        } else if (password.length() == 0) {
            Toast.makeText(getActivity(), "Enter password", Toast.LENGTH_SHORT).show();
       }else {

            JsonObject body = new JsonObject();
            body.addProperty("custname", custname);
            body.addProperty("Mobno", Mobno);

            body.addProperty("email", email);
            body.addProperty("password", password);



            final String url = Utils.getUrl(Constants.ROUTE_USER + "/signup");
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
                                    Toast.makeText(getActivity(), "Successfully registered user", Toast.LENGTH_SHORT).show();
                                    onButtonBackClicked();
                                } else {
                                    Toast.makeText(getActivity(), "Error while registering a user", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }
}