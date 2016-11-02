package com.tags.sw.mylabel;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * 我也是个菜鸟，正好项目用到就自己简单写的，也没有什么高深的技术
 * 还希望能与大家沟通交流，不足的地方还请大家提出来，有好的东西也能一起分享
 * 代码比较乱，- -！
 * 750837523@qq.com这是我的邮箱
 *
 * */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * @fieldName: mListTxtViews
     * @fieldType: List<TextView>
     * @Description: 用来存放所有TextView
     */
    private List<TextView> mListTxtViews;
    /**
     * @fieldName: mAccordTxtViews
     * @fieldType: List<TextView>
     * @Description: 用户选过的TextView
     */
    private List<TextView> mAccordTxtViews;
    /**
     * @fieldName: mTagList
     * @fieldType: List<Map<String,Object>>
     * @Description: json中的用户所选标签集合
     */
    private List<Map<String, Object>> mTagList;
    /**
     * @fieldName: tagList
     * @fieldType: List<Map<String,Object>>
     * @Description: json中所有标签集合
     */
    private List<Map<String, Object>> tagList;
    /**
     * @fieldName: mTagId
     * @fieldType: List<String>
     * @Description: 用来保存用户已选及新选的标签Id
     */
    private List<String> mTagId;
    /**
     * @fieldName: mAllId
     * @fieldType: List<String>
     * @Description: 所有标签Id
     */
    private List<String> mAllId;
    /**
     * @fieldName: sb
     * @fieldType: StringBuffer
     * @Description: 用来拼接要保存的Id
     */
    private StringBuffer sb;
    /**
     * @fieldName: mArray
     * @fieldType: int[]
     * @Description: 用来随机数
     */
    private int[] mArray;

    /**
     * @fieldName: num
     * @fieldType: int
     * @Description: 防止点击换一批时刷掉用户刚选的标签
     */
    private int num = 0;

    private int nnn = 1;//仅用于点击换一批切换数据效果，实际应用是去掉

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initData();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListTxtViews = new ArrayList<TextView>();
        mAccordTxtViews = new ArrayList<TextView>();
        mTagList = new ArrayList<Map<String, Object>>();
        tagList = new ArrayList<Map<String, Object>>();
        mTagId = new ArrayList<String>();
        mAllId = new ArrayList<String>();
        initView();
        initData();
    }

    private void initData() {
        nnn += 1;
        if (nnn % 2 == 0) {
            if (tagList.size() > 0)
                tagList.clear();
            for (int i = 0; i < 8; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", i);
                map.put("title", "土豪金" + i);
                tagList.add(map);
            }
        } else {
            if (tagList.size() > 0)
                tagList.clear();
            for (int i = 10; i < 18; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", i);
                map.put("title", "雅致黑" + i);
                tagList.add(map);
            }
        }


        mArray = getRandom();
// 这是处理网络数据中用户已选择的标签 mTagList是选的集合，我这里用不到
//        num += 1;
//        if (num == 1) {
//            // 给用户选过的标签赋值及更改背景颜色
//            for (int i = 0; i < mTagList.size(); i++) {
//                if (i < 4) {
//                    TextView view = mAccordTxtViews.get(i);
//                    Map<String, Object> map = mTagList.get(i);
//                    // 在这里给textview添加文字描述以及背景颜色
//                    view.setVisibility(View.VISIBLE);
//                    view.setText(map.get("title") == null ? "" : map.get("title").toString());
//                    view.setTag(map.get("id") == null ? "" : map.get("id").toString());
//                    mTagId.add(map.get("id").toString());
//                    setBgForTxt(view, mArray[i]);
//                }
//            }
//        }
        // 在所有的标签中拿出8个显示。供用户选择
        for (int i = 0; i < tagList.size(); i++) {
            mAllId.add(tagList.get(i).get("id").toString());
            if (i > 7)
                break;
            TextView view = mListTxtViews.get(i);
            Map<String, Object> map = tagList.get(i);
            // 在这里给textview添加数据
            view.setText(map.get("title") == null ? "" : map.get("title").toString());
            view.setTag(map.get("id") == null ? "" : map.get("id").toString());
            // 初始化页面数据的背景
            if (mTagId != null && mTagId.size() > 0) {
                for (String d : mAllId) {
                    for (String s : mTagId) {
                        if (d.equals(s)) {
                            // 如果用户选取过，则添加上灰色背景
                            setBgForTxt(view, 100);
                            break;
                        } else {
                            // 如果用户没有选取过，则添加上随机背景
                            setBgForTxt(view, mArray[i]);
                        }
                    }
                }
            } else {
                setBgForTxt(view, mArray[i]);
            }
        }


    }

    private void initView() {

        TextView tv_label1 = (TextView) findViewById(R.id.tv_label1);
        TextView tv_label2 = (TextView) findViewById(R.id.tv_label2);
        TextView tv_label3 = (TextView) findViewById(R.id.tv_label3);
        TextView tv_label4 = (TextView) findViewById(R.id.tv_label4);
        mAccordTxtViews.add(tv_label1);
        mAccordTxtViews.add(tv_label2);
        mAccordTxtViews.add(tv_label3);
        mAccordTxtViews.add(tv_label4);
        tv_label1.setOnClickListener(this);
        tv_label2.setOnClickListener(this);
        tv_label3.setOnClickListener(this);
        tv_label4.setOnClickListener(this);
        TextView topleft = (TextView) findViewById(R.id.id_hot_top_left);
        TextView topcenter = (TextView) findViewById(R.id.id_hot_top_center);
        TextView topright = (TextView) findViewById(R.id.id_hot_top_right);
        TextView centerleft = (TextView) findViewById(R.id.id_hot_center_left);
        TextView centerright = (TextView) findViewById(R.id.id_hot_center_right);
        TextView bottomleft = (TextView) findViewById(R.id.id_hot_bottom_left);
        TextView bottomcenter = (TextView) findViewById(R.id.id_hot_bottom_center);
        TextView bottomright = (TextView) findViewById(R.id.id_hot_bottom_right);
        TextView refresh = (TextView) findViewById(R.id.tv_change);
        refresh.setOnClickListener(this);
        findViewById(R.id.tv_save).setOnClickListener(this);
        mListTxtViews.add(topleft);
        mListTxtViews.add(topcenter);
        mListTxtViews.add(topright);
        topleft.setOnClickListener(this);
        topcenter.setOnClickListener(this);
        topright.setOnClickListener(this);
        mListTxtViews.add(centerleft);
        mListTxtViews.add(centerright);
        centerleft.setOnClickListener(this);
        centerright.setOnClickListener(this);
        mListTxtViews.add(bottomleft);
        mListTxtViews.add(bottomcenter);
        mListTxtViews.add(bottomright);
        bottomleft.setOnClickListener(this);
        bottomcenter.setOnClickListener(this);
        bottomright.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_save:
                if (mTagId.size() < 1)
                    Toast.makeText(MainActivity.this, "至少选择一个才可以保存！", Toast.LENGTH_SHORT).show();
                else
                    saveTags();
                break;
            // 换一批按钮
            case R.id.tv_change:
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);

                break;
            // 随机标签上左
            case R.id.id_hot_top_left:
                addTxtViews(v, 8);
                break;
            // 随机标签上中
            case R.id.id_hot_top_center:
                addTxtViews(v, 1);
                break;
            // 随机标签上右
            case R.id.id_hot_top_right:
                addTxtViews(v, 2);
                break;
            // 随机标签中左
            case R.id.id_hot_center_left:
                addTxtViews(v, 3);
                break;
            // 随机标签中右
            case R.id.id_hot_center_right:
                addTxtViews(v, 4);
                break;
            // 随机标签下左
            case R.id.id_hot_bottom_left:
                addTxtViews(v, 5);
                break;
            // 随机标签下中
            case R.id.id_hot_bottom_center:
                addTxtViews(v, 6);
                break;
            // 随机标签下右
            case R.id.id_hot_bottom_right:
                addTxtViews(v, 7);
                break;
            // 用户选取的第一个
            case R.id.tv_label1:
                selectedTxtViews(0);
                // 用户选取的第二个
            case R.id.tv_label2:
                selectedTxtViews(1);
                break;
            // 用户选取的第三个
            case R.id.tv_label3:
                selectedTxtViews(2);
                break;
            // 用户选取的第四个
            case R.id.tv_label4:
                selectedTxtViews(3);
                break;
            default:
                break;
        }
    }

    /**
     * @param tag
     * @Title: selectedTxtViews
     * @Description: 处理点击已选取的标签
     * @return: void
     */
    @SuppressLint("NewApi")
    private void selectedTxtViews(int tag) {
        try {
            // 是否删除
            boolean isDelete = false;
            if (mAccordTxtViews != null) {
                // 获取要删除的view
                TextView textView = mAccordTxtViews.get(tag);
                textView.setVisibility(View.GONE);
                // 遍历出相同的标签，从mTagId中删除，改变相应背景
                for (TextView view : mListTxtViews) {
                    if (view.getTag().toString().equals(textView.getTag().toString())) {
                        mTagId.remove(view.getTag().toString());
                        isDelete = true;
                        Drawable mDrawable = textView.getBackground();
                        view.setBackgroundDrawable(mDrawable);
                    }
                }
                // 如果当前页面不包含，直接删除，不用改变背景
                if (isDelete == false) {
                    mTagId.remove(textView.getTag().toString());
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param view
     * @param tag
     * @Title: addTxtViews
     * @Description: 处理点击的个性标签
     * @return: void
     */
    @SuppressWarnings("deprecation")
    private void addTxtViews(View view, int tag) {
        // 判断是否包含当前点击的标签
        if (!mTagId.contains(view.getTag().toString())) {
            // 如果不包含当前点击的标签，且选取的标签不够4个
            if (mTagId.size() < 4) {
                Drawable drawable = view.getBackground();
                setBgForTxt((TextView) view, 100);
                mTagId.add(view.getTag().toString());
                if (mAccordTxtViews.get(0).getVisibility() == View.GONE) {
                    mAccordTxtViews.get(0).setVisibility(View.VISIBLE);
                    mAccordTxtViews.get(0).setBackgroundDrawable(drawable);
                    mAccordTxtViews.get(0).setText(((TextView) view).getText().toString());
                    mAccordTxtViews.get(0).setTag(view.getTag().toString());
                } else if (mAccordTxtViews.get(1).getVisibility() == View.GONE) {
                    mAccordTxtViews.get(1).setVisibility(View.VISIBLE);
                    mAccordTxtViews.get(1).setBackgroundDrawable(drawable);
                    mAccordTxtViews.get(1).setText(((TextView) view).getText().toString());
                    mAccordTxtViews.get(1).setTag(view.getTag().toString());
                } else if (mAccordTxtViews.get(2).getVisibility() == View.GONE) {
                    mAccordTxtViews.get(2).setVisibility(View.VISIBLE);
                    mAccordTxtViews.get(2).setBackgroundDrawable(drawable);
                    mAccordTxtViews.get(2).setText(((TextView) view).getText().toString());
                    mAccordTxtViews.get(2).setTag(view.getTag().toString());
                } else if (mAccordTxtViews.get(3).getVisibility() == View.GONE) {
                    mAccordTxtViews.get(3).setVisibility(View.VISIBLE);
                    mAccordTxtViews.get(3).setBackgroundDrawable(drawable);
                    mAccordTxtViews.get(3).setText(((TextView) view).getText().toString());
                    mAccordTxtViews.get(3).setTag(view.getTag().toString());
                }
            } else {
                Toast.makeText(MainActivity.this, "最多选取4个!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "已经选取过了!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param view
     * @param round
     * @Title: setBgForTxt
     * @Description: 给TextView 设置背景样式
     * @return: void
     */
    @SuppressLint("ResourceAsColor")
    private void setBgForTxt(TextView view, int round) {
        switch (round) {
            case 0:
                view.setBackgroundResource(R.drawable.cat_bg1);
                break;
            case 1:
                view.setBackgroundResource(R.drawable.cat_bg2);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.cat_bg3);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.cat_bg4);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.cat_bg5);
                break;
            case 5:
                view.setBackgroundResource(R.drawable.cat_bg6);
                break;
            case 6:
                view.setBackgroundResource(R.drawable.cat_bg7);
                break;
            case 7:
                view.setBackgroundResource(R.drawable.cat_bg8);
                break;
            case 8:
                view.setBackgroundResource(R.drawable.cat_bg8);
                break;
            default:
                view.setBackgroundResource(R.drawable.cat_bg_sel);
        }
    }

    /**
     * @Title: saveTags
     * @Description: 保存用户选择的标签
     * @return: void
     */
    private void saveTags() {
        sb = new StringBuffer();
        /** 此循环用来拼接选中的个性标签中的id */
        int count = 0;
        // 当前选取的标签个数
        for (int j = 0; j < mTagId.size(); j++) {
            String tagId = mTagId.get(j);
            if (count == 0) {
                sb.append(tagId);
            } else {
                sb.append("," + tagId);
            }
            count++;
        }
        Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 随机数生成器
     *
     * @return int[]
     */
    public static int[] getRandom() {
        int[] array = new int[8];
        Random rm = new Random();// 随机数生成器
        for (int i = 0; i < array.length; i++) {
            array[i] = rm.nextInt(8);
            for (int j = 0; j < i; j++) {
                while (array[i] == array[j]) {
                    if (i != 0 && array[i] != 0) {
                        i--;
                    } else {
                        i = 0;
                    }
                    break;
                }
            }
        }
        return array;
    }

}
