package com.kefujiqiren.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kefujiqiren.R;
import com.kefujiqiren.activity.LoginActivity;
import com.kefujiqiren.activity.MainActivity;
import com.kefujiqiren.db.UserDB;
import com.kefujiqiren.util.UserInfoSave;
import com.kefujiqiren.widget.StateButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.userHeader)
    ImageView userHeader;
    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnLogin)
    StateButton btnLogin;
    @BindView(R.id.toRegister)
    Button toRegister;
    Unbinder unbinder;

    private FragmentManager fragmentManager ;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).toRegister();
            }
        });

        return view;
    }

    private void attemptLogin() {


        // Reset errors.
        username.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        final String uName = username.getText().toString();
        final String ps = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(ps) && !isPasswordValid(ps)) {
            password.setError(getString(R.string.error_short_password));
            focusView = password;
            cancel = true;
        }

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
                            UserDB db = UserDB.getInstance(getActivity());

                            if (/*uName.equals("admin") && ps.equals("123456")||*/db.hasUserLogin(uName, ps)) {

                                int userid = db.getUserId(uName);
                                if(userid==-1){
                                    Toast.makeText(getActivity(), "获取用户id失败", Toast.LENGTH_SHORT).show();
                                }
                                //Toast.makeText(getActivity(), "获取用户id="+userid, Toast.LENGTH_SHORT).show();
                                UserInfoSave.saveUserInfo(getActivity(), uName, ps, userid);
                                MainActivity.activityStart(getActivity());
                                getActivity().finish() ;

                            } else {
                                if (db.getUserId(uName)>0) {
                                    password.setError(getString(R.string.error_incorrect_password));
                                    password.requestFocus();

                                } else {
                                    username.setError(getString(R.string.error_incorrect_username));
                                    username.requestFocus();
                                }
                            }
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

    @OnClick({R.id.btnLogin, R.id.toRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                attemptLogin();
                break;
            case R.id.toRegister:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
