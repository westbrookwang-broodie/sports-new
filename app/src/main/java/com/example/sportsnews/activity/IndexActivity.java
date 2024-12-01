package com.example.sportsnews.activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sportsnews.R;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends ActivityGroup {

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab_club, tab_forum, tab_field, tab_mine;
    private List<View> mViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();


        // 对单选按钮进行监听，选中则切换页面
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.club:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.forum:
                        mViewPager.setCurrentItem(1);
                        // 切回收藏页面时要startActivity更新列表
//                        getLocalActivityManager().startActivity("CollectionActivity", new Intent(IndexActivity.this, CollectionActivity.class));
                        break;
                    case R.id.field:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.mine:
                        mViewPager.setCurrentItem(3);
                        break;
                }
            }
        });

        int tabToSelect = getIntent().getIntExtra("tabToSelect", 0);
        if (tabToSelect == 1) {
            mRadioGroup.check(R.id.forum);// Automatically select the Forum tab
            mViewPager.setCurrentItem(1);
        }
    }

    /* 初始化控件 */
    private void initView()
    {
        mViewPager = findViewById(R.id.viewpager);
        mRadioGroup = findViewById(R.id.rg_tab_bar);
        tab_club = findViewById(R.id.club);
        tab_forum = findViewById(R.id.forum);
        tab_field = findViewById(R.id.field);
        tab_mine = findViewById(R.id.mine);


        mViews = new ArrayList<View>();     // 添加视图
//        LayoutInflater li = getLayoutInflater();
//        mViews.add(li.inflate(R.layout.activity_news,null,false));
//        mViews.add(li.inflate(R.layout.activity_collection,null,false));
//        mViews.add(li.inflate(R.layout.activity_profile,null,false));

        View newsView = getLocalActivityManager().startActivity("NewsActivity", new Intent(IndexActivity.this, NewsListActivity.class)).getDecorView();
        View collectionView = getLocalActivityManager().startActivity("FieldActivity", new Intent(IndexActivity.this, FieldActivity.class)).getDecorView();
        View forumView = getLocalActivityManager().startActivity("ForumActivity", new Intent(IndexActivity.this, ForumActivity.class)).getDecorView();
//        View forumDetailView = getLocalActivityManager().startActivity("ForumDetailActivity", new Intent(IndexActivity.this, ForumDetailActivity.class)).getDecorView();

//        View forumDetailView = getLocalActivityManager().startActivity("ForumDetailActivity", intent).getDecorView();

//        Intent intent = new Intent(IndexActivity.this, ProfileActivity.class);
//        intent.putExtra("username", getIntent().getStringExtra("username"));
        View profileView = getLocalActivityManager().startActivity("CustomizeJerseyActivity", new Intent(IndexActivity.this, CustomizeJerseyActivity.class)).getDecorView();
        mViews.add(newsView);
        mViews.add(forumView);
        mViews.add(collectionView);
        mViews.add(profileView);
//        mViews.add(forumDetailView);

        mViewPager.setAdapter(new MyPagerAdapter());        // 设置一个适配器
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());   //添加切换界面的监听器


    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //让viewpager滑动的时候，下面的图标跟着变动
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    tab_club.setChecked(true);
                    tab_field.setChecked(false);
                    tab_forum.setChecked(false);
                    tab_mine.setChecked(false);
                    break;
                case 1:
                    tab_club.setChecked(false);
                    tab_forum.setChecked(true);
                    tab_field.setChecked(false);
                    tab_mine.setChecked(false);
                    break;
                case 2:
                    tab_club.setChecked(false);
                    tab_forum.setChecked(false);
                    tab_field.setChecked(true);
                    tab_mine.setChecked(false);
                    break;
                case 3:
                    tab_club.setChecked(false);
                    tab_forum.setChecked(false);
                    tab_field.setChecked(false);
                    tab_mine.setChecked(true);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViews.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }
    }
}

