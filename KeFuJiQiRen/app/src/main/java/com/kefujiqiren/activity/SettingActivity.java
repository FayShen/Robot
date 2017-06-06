package com.kefujiqiren.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kefujiqiren.R;
import com.kefujiqiren.adapter.ChatListViewAdapter;
import com.kefujiqiren.util.RetrofitUtil;
import com.kefujiqiren.util.UserInfoSave;
import com.kefujiqiren.widget.PhotoPopupWindow;
import com.kefujiqiren.widget.StateButton;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends Activity implements PhotoPopupWindow.OnDialogListener {

    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.changeOwnerHead)
    RelativeLayout changeOwnerHead;
    @BindView(R.id.changeServiceHead)
    RelativeLayout changeServiceHead;
    @BindView(R.id.changeUserName)
    RelativeLayout changeUserName;
    @BindView(R.id.changePassword)
    RelativeLayout changePassword;
    @BindView(R.id.ownerHead)
    RoundedImageView ownerHead;
    @BindView(R.id.serviceHead)
    RoundedImageView serviceHead;
    @BindView(R.id.changeIP)
    RelativeLayout changeIP;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.IP)
    TextView IP;
    @BindView(R.id.exitLogin)
    StateButton exitLogin;
    //拍照
    private PopupWindow mPopupWindow;
    private Uri photoUri;

    private int flag = 1;

    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_CAMERA = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_LOCAL_PHOTO = 2;

    private static final int CUT_PHOTO = 3;

    public static final int FROM_DETAIL_CHANGE_ACTIVITY = 4;

    private int model = 1;

    public static boolean isExitLogin = false;

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        ((Activity)context).startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mPopupWindow = new PhotoPopupWindow(this, this);
        ownerHead.setImageBitmap(ChatListViewAdapter.bmpOwner);
        serviceHead.setImageBitmap(ChatListViewAdapter.bmpService);
        username.setText(UserInfoSave.getUserName(SettingActivity.this));
        IP.setText(RetrofitUtil.IP);
    }

    @Override
    public void onCamera() {
// 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (!SDState.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            photoUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new ContentValues());
            if (photoUri != null) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, SELECT_PIC_BY_CAMERA);

            } else {
                Toast.makeText(this, "发生意外，无法写入相册1", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(this, "发生意外，无法写入相册", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocalPhoto() {
        // 从相册中取图片
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePictureIntent, SELECT_PIC_BY_LOCAL_PHOTO);
    }

    @Override
    public void onCancel() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PIC_BY_CAMERA:
                    // 选择自拍结果
                    beginCrop(photoUri);
                    break;
                case SELECT_PIC_BY_LOCAL_PHOTO:
                    // 选择图库图片结果
                    beginCrop(intent.getData());
                    break;
                case CUT_PHOTO:
                    handleCrop(intent);
                    break;
                case FROM_DETAIL_CHANGE_ACTIVITY:
                    Bundle bundle = intent.getExtras();
                    if (model == 1) {
                        String uName = bundle.getString("username", "");
                        if (!TextUtils.isEmpty(uName)) {
                            username.setText(uName);
                            UserInfoSave.setUserName(SettingActivity.this, uName);
                        }
                        return;
                    } else if (model == 2) {
                        String password = bundle.getString("password", "");
                        if (!TextUtils.isEmpty(password)) {
                            UserInfoSave.setPassword(SettingActivity.this, password);
                        }
                    } else if (model == 3) {
                        String ip = bundle.getString("IP", "");
                        if (!TextUtils.isEmpty(ip)) {
                            IP.setText(ip);
                            RetrofitUtil.IP = ip;
                        }
                    }

                    break;
            }

        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void beginCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，注意如果return-data=true情况下,其实得到的是缩略图，并不是真实拍摄的图片大小，
        // 而原因是拍照的图片太大，所以这个宽高当你设置很大的时候发现并不起作用，就是因为返回的原图是缩略图，但是作为头像还是够清晰了
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        //返回图片数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_PHOTO);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param result
     */
    private void handleCrop(Intent result) {
        Bundle extras = result.getExtras();
        if (extras != null) {
            try {
                Bitmap bitmap = (Bitmap) extras.get("data");
                if (flag == 1) {
                    ChatListViewAdapter.bmpOwner = bitmap;
                    ownerHead.setImageBitmap(bitmap);

                } else if (flag == 2) {
                    ChatListViewAdapter.bmpService = bitmap;
                    serviceHead.setImageBitmap(bitmap);
                }
                //adapter.notifyDataSetChanged();
                //emotionVoice.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.btnBack, R.id.changeOwnerHead, R.id.changeServiceHead, R.id.changeUserName, R.id.changePassword, R.id.changeIP})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.changeOwnerHead:
                flag = 1;
                mPopupWindow.showAtLocation(changeOwnerHead, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.changeServiceHead:
                flag = 2;
                mPopupWindow.showAtLocation(changeServiceHead, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.changeUserName:
                model = 1;
                DetailChangeActivity.activityForResultStart(SettingActivity.this, model,
                        UserInfoSave.getUserName(SettingActivity.this),
                        "",
                        "");
                break;
            case R.id.changePassword:
                model = 2;
                DetailChangeActivity.activityForResultStart(SettingActivity.this, model,
                        "",
                        UserInfoSave.getPasswrod(SettingActivity.this),
                        "");
                break;
            case R.id.changeIP:
                model = 3;
                DetailChangeActivity.activityForResultStart(SettingActivity.this, model,
                        "",
                        "",
                        RetrofitUtil.IP);
                break;
        }
    }

    @OnClick(R.id.exitLogin)
    public void onViewClicked() {
        isExitLogin = true;
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        LoginActivity.activityStart(SettingActivity.this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
