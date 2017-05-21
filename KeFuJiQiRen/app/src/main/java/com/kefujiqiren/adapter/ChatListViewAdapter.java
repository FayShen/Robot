package com.kefujiqiren.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kefujiqiren.R;
import com.kefujiqiren.bean.Msg;

import java.util.List;

/**
 * Created by 殇痕 on 2017/5/3.
 *
 */

public class ChatListViewAdapter extends ArrayAdapter<Msg> {
    private int resourceId;
    public ChatListViewAdapter(Context context, int resource, List<Msg> objects) {
        super(context,resource,objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View view;
        ViewHolder holder;
        Msg msg = getItem(position);
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null, false);
            holder = new ViewHolder();
//            holder.headLeft = (RoundedImageView) view.findViewById(R.id.headLeft);
//            holder.headRight = (RoundedImageView) view.findViewById(R.id.headRight);
            holder.left = (RelativeLayout) view.findViewById(R.id.left);
            holder.right = (RelativeLayout) view.findViewById(R.id.right);
            holder.txtLeft = (TextView) view.findViewById(R.id.txtLeft);
            holder.txtRight = (TextView) view.findViewById(R.id.txtRight);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        if(msg.getType() == Msg.TYPE_RECEIVED){
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
//            holder.headLeft.setImageResource(msg.get());
            holder.txtLeft.setText(msg.getContent());
        }else if(msg.getType() == Msg.TYPE_SENT){
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
            holder.txtRight.setText(msg.getContent());
        }
        return view;
    }
    class ViewHolder{
        RelativeLayout left;
        RelativeLayout right;
//        RoundedImageView headLeft;
//        RoundedImageView headRight;
        TextView txtLeft;
        TextView txtRight;
    }
}
