package com.kefujiqiren.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kefujiqiren.R;
import com.kefujiqiren.adapter.ChatListViewAdapter;
import com.kefujiqiren.adapter.EmotionGridViewAdapter;
import com.kefujiqiren.adapter.EmotionPagerAdapter;
import com.kefujiqiren.bean.Msg;
import com.kefujiqiren.util.AppServcie;
import com.kefujiqiren.util.EmotionUtils;
import com.kefujiqiren.util.RetrofitUtil;
import com.kefujiqiren.util.Utils;
import com.kefujiqiren.widget.IndicatorView;
import com.kefujiqiren.widget.StateButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kefujiqiren.adapter.ChatListViewAdapter.bmpOwner;
import static com.kefujiqiren.adapter.ChatListViewAdapter.bmpService;

public class MainActivity extends AppCompatActivity{


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
    @BindView(R.id.vpEmotion)
    ViewPager vpEmotion;
    //    @BindView(R.id.emotionPositionContainer)
//    LinearLayout emotionPositionContainer;
    @BindView(R.id.linEmotion)
    LinearLayout linEmotion;
    @BindView(R.id.content_main)
    LinearLayout contentMain;
    @BindView(R.id.dotGroup)
    IndicatorView dotGroup;

    private ChatListViewAdapter adapter;
    private List<Msg> msgList = new ArrayList<>();

    // 7列3行
    private int columns = 6;
    private int rows = 4;
    private List<View> pageView = new ArrayList<View>();
    private EmotionPagerAdapter emotionPagerAdapter;



    public static void activityStart(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);




        bmpOwner = BitmapFactory.decodeResource(getResources(), R.mipmap.head_default);
        bmpService = BitmapFactory.decodeResource(getResources(), R.mipmap.service_head_default);


        msgList.add(new Msg("你好有什么可以帮助你的，[微笑][微笑][猪头][猪头]", Utils.getCurrentTime(), Msg.TYPE_RECEIVED));
        adapter = new ChatListViewAdapter(this, R.layout.item_custom_service, msgList);
        chatListView.setAdapter(adapter);
        chatListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (linEmotion.getVisibility() == View.VISIBLE) {
                        linEmotion.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
        InitViewPager();

//       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void InitViewPager() {
        //设置下边滑动点
        vpEmotion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotGroup.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        // 获取屏幕宽度
        int screenWidth = curMetrics.widthPixels;
        // item的间距
        int spacing = Utils.dp2px(this, 12);
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - spacing * 8) / 7;
        //动态计算gridview的总高度
        int gvHeight = (int)(itemWidth * 3.5 + spacing * 6);

        List<GridView> emotionViews = new ArrayList<>();
        List<String> emotionNames = new ArrayList<>();
        // 遍历所有的表情的key
        for (String emojiName : EmotionUtils.EMOTION_STATIC_MAP.keySet()) {
            emotionNames.add(emojiName);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 23) {
                GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                emotionViews.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<>();
            }
        }

        // 判断最后是否有不足23个表情的剩余情况
        if (emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
            emotionViews.add(gv);
        }

        //初始化指示器
        dotGroup.initIndicator(emotionViews.size());
        // 将多个GridView添加显示到ViewPager中
        emotionPagerAdapter = new EmotionPagerAdapter(emotionViews);
        vpEmotion.setAdapter(emotionPagerAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        vpEmotion.setLayoutParams(params);
    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(this);
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置8列
        gv.setNumColumns(8);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding * 2);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        EmotionGridViewAdapter adapter = new EmotionGridViewAdapter(this, emotionNames, itemWidth);
        gv.setAdapter(adapter);
        //设置全局点击事件
        //gv.setOnItemClickListener(GlobalOnItemClickManagerUtils.getInstance(this).getOnItemClickListener());
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAdapter = parent.getAdapter();

                if (itemAdapter instanceof EmotionGridViewAdapter) {
                    // 点击的是表情
                    EmotionGridViewAdapter emotionGvAdapter = (EmotionGridViewAdapter) itemAdapter;

                    if (position == emotionGvAdapter.getCount() - 1) {
                        // 如果点击了最后一个回退按钮,则调用删除键事件
                        editChatText.dispatchKeyEvent(new KeyEvent(
                                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    } else {
                        // 如果点击了表情,则添加到输入框中
                        String emotionName = emotionGvAdapter.getItem(position);

                        // 获取当前光标位置,在指定位置上添加表情图片文本
                        int curPosition = editChatText.getSelectionStart();
                        StringBuilder sb = new StringBuilder(editChatText.getText().toString());
                        sb.insert(curPosition, emotionName);

                        // 特殊文字处理,将表情等转换一下
                        editChatText.setText(Utils.getEmotionContent(MainActivity.this, editChatText, sb.toString()));

                        // 将光标设置到新增完表情的右侧
                        editChatText.setSelection(curPosition + emotionName.length());
                    }
                }
            }
        });
        return gv;
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

        switch (id){
            case R.id.menuSettings:
                SettingActivity.activityStart(MainActivity.this);
                break;
            case R.id.menuClean:
                msgList.clear();
                adapter.notifyDataSetChanged();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @OnClick({R.id.btnEmotion, R.id.emotionAdd, R.id.btnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEmotion:
                //点击表情时隐藏软盘
                InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
                if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getCurrentFocus() != null)
                        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                //打开可关闭linEmotion
                if (linEmotion.getVisibility() == View.GONE) {
                    linEmotion.setVisibility(View.VISIBLE);
                } else {
                    linEmotion.setVisibility(View.GONE);
                }

                break;
            case R.id.emotionAdd:
                break;
            case R.id.btnSend:
                if(TextUtils.isEmpty(editChatText.getText())){
                    return ;
                }
                if(msgList.size()>=1 && Utils.isShowTime(msgList.get(msgList.size()-1).getTime(), Utils.getCurrentTime())){
                    msgList.add(new Msg(editChatText.getText().toString(), Utils.getCurrentTime(), Msg.TYPE_TIME));
                }
                msgList.add(new Msg(editChatText.getText().toString(), Utils.getCurrentTime(), Msg.TYPE_SENT));
                adapter.notifyDataSetChanged();
                try {
                    toGetResponse();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "出错"+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                editChatText.setText("");
                break;
        }
    }

    @OnClick(R.id.editChatText)
    public void onViewClicked() {
        if (linEmotion.getVisibility() == View.VISIBLE) {
            linEmotion.setVisibility(View.GONE);
        }
    }

    private void toGetResponse(){
        Log.d("999999999999999999999", editChatText.getText().toString().trim());
        Call<String> call = RetrofitUtil.retrofit.create(AppServcie.class).getResponse(editChatText.getText().toString().trim());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("----------",response.body().toString());
                //String answer = parseXMLWithPull(response.body().toString());
                //answer = answer.replaceAll(System.getProperty("line.separator"),"");
                String answer = response.body().toString().replaceAll(System.getProperty("line.separator"),"");
                Log.d("----------",answer);
                msgList.add(new Msg(answer,Utils.getCurrentTime(), Msg.TYPE_RECEIVED));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "访问失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("_————————", "onFailure: "+t.getMessage());
            }
        });
    }

    //使用Pull解析xml
    private String parseXMLWithPull(String xmlData){
        //Log.d("MainActivity", "parseXMLWithPull(String xmlData)");
        String answer="";
        try{
            //获取到XmlPullParserFactory的实例，并借助这个实例得到XmlPullParser对象
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            //调用XmlPullParser的setInput方法将服务器返回的xml数据设置进去开始解析
            xmlPullParser.setInput(new StringReader(xmlData));
            //通过getEventType()方法得到当前解析事件
            int eventType=xmlPullParser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                //通过getName()方法得到当前节点的名字，如果发现节点名等于answer
                //就调用nextText()方法来获取结点具体的内容，每当解析完一个app结点就将获取到的内容打印出来
                String nodeName=xmlPullParser.getName();
                //Log.d("MainActivity",""+eventType+ " nodeName= "+nodeName);
                switch(eventType){
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:{
                        if("Answer".equals(nodeName)){
                            answer=xmlPullParser.nextText();
                            //Log.d("MainActivity", "answer is "+answer);
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if("Answer".equals(nodeName)){
                           // Log.d("MainActivity", "answer is "+answer);
                        }
                        break;
                    }
                    default:
                        break;
                }
                //调用next()方法获取到下一个解析事件
                eventType=xmlPullParser.next();
            }
        }catch(Exception e){
            e.printStackTrace();
            Log.d("parseXMLWithPull:", "errrrrrrrrrrrrrrrrrrrrr"+e.getMessage());
        }
        return answer;
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                //强制退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 0:
                    //Toast.makeText(MainActivity.this, "requsetcode", Toast.LENGTH_SHORT).show();
                    if(SettingActivity.isExitLogin){
                        SettingActivity.isExitLogin=false;
                        finish();
                    }else{
                        adapter.notifyDataSetChanged();
                    }
            }
        }
    }
}
