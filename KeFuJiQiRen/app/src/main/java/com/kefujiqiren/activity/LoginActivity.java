package com.kefujiqiren.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.kefujiqiren.R;
import com.kefujiqiren.fragment.LoginFragment;
import com.kefujiqiren.fragment.RegisterFragment;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements FragmentManager.OnBackStackChangedListener{
    public static boolean mShowingBack = false;
    private FragmentManager fragmentManager;

    public static void activityStart(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        /*password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });*/

        SharedPreferences pref = getSharedPreferences("userinfo", MODE_PRIVATE) ;
        if(pref.getBoolean("hasLogin", false)) {
            MainActivity.activityStart(LoginActivity.this);
            finish() ;
        }else{

            //判断<span style="font-family: Arial, Helvetica, sans-serif;">savedInstanceState 是否为null,如果为null,则说明这是创建的全新的对象，如果不为null,则是重建上一次销毁的对象</span>
            fragmentManager = getFragmentManager();
            if (savedInstanceState == null){
                fragmentManager.beginTransaction().add(R.id.container, new LoginFragment()).commit();
            }else{
                mShowingBack = fragmentManager.getBackStackEntryCount() > 0;
            }
            fragmentManager.addOnBackStackChangedListener(this);
        }
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (fragmentManager.getBackStackEntryCount() > 0);
    }

    public void toRegister(){
        if (LoginActivity.mShowingBack){
            fragmentManager.popBackStack();
            return;
        }
        LoginActivity.mShowingBack = true;
        /**
         * 旋转动画
         */
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.left_in, R.animator.left_out,
                R.animator.right_in, R.animator.right_out).replace(R.id.container, new RegisterFragment())
                .addToBackStack(null).commit();
    }

    public void toLogin(){
        if (LoginActivity.mShowingBack){
            fragmentManager.popBackStack();
            return;
        }
        LoginActivity.mShowingBack = true;
        /**
         * 旋转动画
         */
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.left_in, R.animator.left_out,
                R.animator.right_in, R.animator.right_out).replace(R.id.container, new LoginFragment())
                .addToBackStack(null).commit();
    }

    /*private void attemptLogin() {


        // Reset errors.
        username.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String uName = username.getText().toString();
        String ps = password.getText().toString();

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
        } else if (isEmailValid(uName)) {
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginProgress.setVisibility(View.GONE);
                            if(username.getText().toString().trim().equals("admin") && password.getText().toString().trim().equals("123456")){
                                MainActivity.activityStart(LoginActivity.this);
                                finish();
                            }else{
                                if(username.getText().toString().trim().equals("admin")){
                                    password.setError(getString(R.string.error_incorrect_username));
                                    password.requestFocus();

                                }else {
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

    private boolean isEmailValid(String uName) {
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
    }*/
}

