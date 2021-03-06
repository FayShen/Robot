package com.kefujiqiren.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kefujiqiren.R;
import com.kefujiqiren.bean.Msg;
import com.kefujiqiren.util.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 殇痕 on 2017/5/3.
 */

public class ChatListViewAdapter extends ArrayAdapter<Msg> {
    private int resourceId;
    public static Bitmap bmpOwner;
    public static Bitmap bmpService;

    public ChatListViewAdapter(Context context, int resource, List<Msg> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        Msg msg = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        if (msg.getType() == Msg.TYPE_RECEIVED) {
            holder.txtTime.setVisibility(View.GONE);
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
//            holder.headLeft.setImageResource(msg.get());
            SpannableString ss = Utils.getEmotionContent(getContext(), holder.txtRight, msg.getContent());
            holder.txtLeft.setText(ss);
            holder.headLeft.setImageBitmap(bmpService);
        } else if (msg.getType() == Msg.TYPE_SENT) {
            holder.txtTime.setVisibility(View.GONE);
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
            SpannableString ss = Utils.getEmotionContent(getContext(), holder.txtRight, msg.getContent());
            holder.txtRight.setText(ss);
            holder.headRight.setImageBitmap(bmpOwner);
        }else if(msg.getType() == Msg.TYPE_TIME){
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.GONE);
            holder.txtTime.setVisibility(View.VISIBLE);
            holder.txtTime.setText(msg.getTime());
        }
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    class ViewHolder {
        @BindView(R.id.txtTime)
        TextView txtTime;
        @BindView(R.id.headLeft)
        RoundedImageView headLeft;
        @BindView(R.id.txtLeft)
        TextView txtLeft;
        @BindView(R.id.left)
        RelativeLayout left;
        @BindView(R.id.headRight)
        RoundedImageView headRight;
        @BindView(R.id.txtRight)
        TextView txtRight;
        @BindView(R.id.right)
        RelativeLayout right;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
