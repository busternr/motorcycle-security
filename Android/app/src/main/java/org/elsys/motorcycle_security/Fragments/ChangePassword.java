package org.elsys.motorcycle_security.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.activities.Main;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends Fragment {
    private EditText newPasswordInput;
    private EditText emailInput;
    private TextView errorsText;

    public ChangePassword() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View fragmentMap = getActivity().findViewById(R.id.map);
        fragmentMap.setVisibility(View.GONE);
        newPasswordInput = getActivity().findViewById(R.id.NewPassInput);
        newPasswordInput.setTypeface(Typeface.DEFAULT);
        emailInput = getActivity().findViewById(R.id.EmailInput);
        errorsText = getActivity().findViewById(R.id.ErrorsChangePassText);
        Button changePasswordButton = getActivity().findViewById(R.id.ChangePassBtn);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                if (newPasswordInput.getText().toString().length() == 0) errorsText.setText("New password field can't be blank");
                else if (emailInput.getText().toString().length() == 0) errorsText.setText("Email field can't be blank");
                else if (newPasswordInput.getText().toString().length() < 6) errorsText.setText("New password is too short (Minimum 6 characters)");
                else {
                    final Api api = Api.RetrofitInstance.create(getContext());
                    api.getUserAccount(Globals.authorization, emailInput.getText().toString()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                User user = response.body();
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
                                if (user.getEmail().equals(sharedPreferences.getString("Email", ""))) {
                                    api.updatePassword(emailInput.getText().toString(), newPasswordInput.getText().toString(), Globals.authorization).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if(response.isSuccessful()) {
                                                Toast toast = Toast.makeText(getActivity(), "Password change successful", Toast.LENGTH_LONG);
                                                toast.show();
                                                Intent myIntent = new Intent(v.getContext(), Main.class);
                                                startActivity(myIntent);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                        }
                                    });
                                }
                                else errorsText.setText("Wrong email.");
                            }
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                        }
                    });
                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
    }
}

