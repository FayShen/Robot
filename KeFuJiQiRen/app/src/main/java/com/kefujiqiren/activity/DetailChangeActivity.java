package com.kefujiqiren.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kefujiqiren.R;
import com.kefujiqiren.util.UserInfoSave;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailChangeActivity extends AppCompatActivity {

    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.changeUserName)
    LinearLayout changeUserName;
    @BindView(R.id.original)
    EditText original;
    @BindView(R.id.changePassword1)
    LinearLayout changePassword1;
    @BindView(R.id.newPassword)
    EditText newPassword;
    @BindView(R.id.changePassword2)
    LinearLayout changePassword2;
    @BindView(R.id.confirm)
    EditText confirm;
    @BindView(R.id.changePassword3)
    LinearLayout changePassword3;
    @BindView(R.id.editIp)
    EditText editIp;
    @BindView(R.id.changeIP)
    LinearLayout changeIP;
    @BindView(R.id.btnOK)
    Button btnOK;
    @BindView(R.id.activity_detail_change)
    LinearLayout activityDetailChange;

    private int model;

    public static void activityForResultStart(Context context, int _model, String _username, String _password, String _IP) {
        Intent intent = new Intent(context, DetailChangeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("model", _model);
        bundle.putString("username", _username);
        bundle.putString("password", _password);
        bundle.putString("IP", _IP);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, SettingActivity.FROM_DETAIL_CHANGE_ACTIVITY);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_change);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        model = bundle.getInt("model", 1);
        if(model==1){
            hideAll();
            changeUserName.setVisibility(View.VISIBLE);
            username.setText(bundle.getString("username",""));
        }else if(model==2){
            hideAll();
            changePassword1.setVisibility(View.VISIBLE);
            changePassword2.setVisibility(View.VISIBLE);
            changePassword3.setVisibility(View.VISIBLE);
        }else if(model==3){
            hideAll();
            changeIP.setVisibility(View.VISIBLE);
            editIp.setText(bundle.getString("IP", ""));
        }
    }

    private void hideAll(){
        changeUserName.setVisibility(View.GONE);
        changePassword1.setVisibility(View.GONE);
        changePassword2.setVisibility(View.GONE);
        changePassword3.setVisibility(View.GONE);
        changeIP.setVisibility(View.GONE);
    }

    @OnClick({R.id.btnBack, R.id.btnOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnOK:
                if(model==2){
                    if(!original.getText().toString().equals(UserInfoSave.getPasswrod(DetailChangeActivity.this))){
                        Toast.makeText(DetailChangeActivity.this, "原密码错误！！！", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                    if(TextUtils.isEmpty(newPassword.getText().toString()) || newPassword.getText().toString().length()<6){
                        Toast.makeText(DetailChangeActivity.this, "新密码不能小于六位！！！", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                    if(!newPassword.getText().toString().equals(confirm.getText().toString().trim())){
                        Toast.makeText(DetailChangeActivity.this, "两次密码不统一！！！", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                if(model==1) {
                    bundle.putString("username", username.getText().toString().trim());
                }else if(model==2) {
                    bundle.putString("password", newPassword.getText().toString().trim());
                }else if(model==3){
                    bundle.putString("IP", editIp.getText().toString().trim());
                }
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
