package com.kefujiqiren.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.kefujiqiren.R;

import butterknife.Unbinder;

/**
 * Created by 殇痕 on 2017/6/21.
 */

public class PhotoPopupWindow extends PopupWindow implements View.OnClickListener{
    private LinearLayout camera;
    private LinearLayout localPhoto;
    private LinearLayout close;

    private Unbinder unbinder;
    private OnDialogListener listener;
    private View mPopView;
    private Context context;


    public PhotoPopupWindow(final Context context, OnDialogListener listener) {
        super(context);
        this.listener = listener;
        this.context = context;
        mPopView = LayoutInflater.from(context).inflate(R.layout.get_photo, null);
        camera = (LinearLayout) mPopView.findViewById(R.id.camera);
        localPhoto = (LinearLayout) mPopView.findViewById(R.id.localPhoto);
        close = (LinearLayout) mPopView.findViewById(R.id.close);

        camera.setOnClickListener(this);
        localPhoto.setOnClickListener(this);
        close.setOnClickListener(this);

        this.setContentView(mPopView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.ChoosePhotoAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);//0xb0000000
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);//半透明颜色
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                listener.onCamera();
                break;
            case R.id.localPhoto:
                listener.onLocalPhoto();
                break;
            case R.id.close:
                listener.onCancel();
                break;
        }
        dismiss();
    }


    /**
     * Dialog按钮回调接口
     */
    public interface OnDialogListener {

        public void onCamera();//照相

        public void onLocalPhoto();//选择本地照片

        public void onCancel();//取消

    }
}
