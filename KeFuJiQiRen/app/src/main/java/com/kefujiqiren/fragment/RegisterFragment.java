package com.kefujiqiren.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kefujiqiren.R;
import com.kefujiqiren.activity.LoginActivity;
import com.kefujiqiren.widget.StateButton;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.userHeader)
    RoundedImageView userHeader;
    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm)
    EditText confirm;
    @BindView(R.id.btnRegister)
    StateButton btnRegister;
    @BindView(R.id.toLogin)
    Button toLogin;
    Unbinder unbinder;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        confirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).toRegister();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void attemptLogin() {


        // Reset errors.
        username.setError(null);
        password.setError(null);
        confirm.setError(null);

        // Store values at the time of the login attempt.
        final String uName = username.getText().toString().trim();
        final String ps = password.getText().toString().trim();
        String cf = confirm.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username.
        if (TextUtils.isEmpty(uName)) {
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            cancel = true;
        } else if (isUserNameValid(uName)) {
            username.setError(getString(R.string.error_invalid_username));
            focusView = username;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(ps) && !isPasswordValid(ps)) {
            password.setError(getString(R.string.error_short_password));
            focusView = password;
            cancel = true;
        } else if (!ps.equals(cf)) {
            confirm.setError(getString(R.string.error_incorrect_confirm));
            cancel = true;
            focusView = confirm;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            loginProgress.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginProgress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "注册成功~~~///(^v^)\\\\\\~~~", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();

        }
    }

    private boolean isUserNameValid(String uName) {
        //TODO: Replace this with your own logic
        return uName.contains("-");
    }

    private boolean isPasswordValid(String ps) {
        //TODO: Replace this with your own logic
        return ps.length() >= 6;
    }

    @OnClick({R.id.btnBack, R.id.btnRegister, R.id.toLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                ((LoginActivity)getActivity()).toLogin();
                break;
            case R.id.btnRegister:
                attemptLogin();
                break;
            case R.id.toLogin:
                ((LoginActivity)getActivity()).toLogin();
                break;
        }
    }
}
