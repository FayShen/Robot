package com.kefujiqiren.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kefujiqiren.R;
import com.kefujiqiren.adapter.ChatListViewAdapter;
import com.kefujiqiren.bean.Msg;
import com.kefujiqiren.widget.NoScrollViewPager;
import com.kefujiqiren.widget.StateButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chatListView)
    ListView chatListView;
    @BindView(R.id.emotionVoice)
    ImageButton emotionVoice;
    @BindView(R.id.editChatText)
    EditText editChatText;
    @BindView(R.id.txtVoice)
    TextView txtVoice;
    @BindView(R.id.btnEmotion)
    ImageButton btnEmotion;
    @BindView(R.id.emotionAdd)
    ImageView emotionAdd;
    @BindView(R.id.btnSend)
    StateButton btnSend;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.emotionLayout)
    RelativeLayout emotionLayout;
    @BindView(R.id.content_main)
    LinearLayout contentMain;
    private ChatListViewAdapter adapter;
    private List<Msg> msgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        msgList.add(new Msg("今天是个好日子，[微笑][微笑][猪头][猪头]", Msg.TYPE_RECEIVED));
        adapter = new ChatListViewAdapter(this, R.layout.item_custom_service, msgList);
        chatListView.setAdapter(adapter);
//       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnEmotion, R.id.emotionAdd, R.id.btnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEmotion:
                break;
            case R.id.emotionAdd:
                break;
            case R.id.btnSend:
                msgList.add(new Msg(editChatText.getText().toString(), Msg.TYPE_SENT));
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
