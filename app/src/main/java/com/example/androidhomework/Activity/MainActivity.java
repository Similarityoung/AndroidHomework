package com.example.androidhomework.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidhomework.Adapter.SectionsPagerAdapter;
import com.example.androidhomework.Dao.SimpleNEUClassDao;
import com.example.androidhomework.Fragment.CalendarFragment;
import com.example.androidhomework.Fragment.PersonFragment;
import com.example.androidhomework.Fragment.ScheduleFragment;
import com.example.androidhomework.Fragment.ShareFragment;
import com.example.androidhomework.Fragment.TimeTableFragment;
import com.example.androidhomework.R;
import com.github.gzuliyujiang.wheelpicker.DatimePicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatimePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity;
import com.github.gzuliyujiang.wheelpicker.widget.DatimeWheelLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout myTab;
    private ViewPager2 myPager2;
    List<String> titles=new ArrayList<>();
    List<Integer> icons=new ArrayList<>();
    List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉导航栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        myTab = findViewById(R.id.tab);
        myPager2 = findViewById(R.id.viewpager2);

//        禁止滑动
        myPager2.setUserInputEnabled(false);

        initView();

    }

    public void initView(){
//添加标题
        titles.add("日程");
        titles.add("课程表");
//        titles.add("分享");
        titles.add("我的");

        icons.add(R.drawable.baseline_calendar);
        icons.add(R.drawable.baseline_schedule);
//        icons.add(R.drawable.baseline_share);
        icons.add(R.drawable.baseline_person);

        //添加Fragment进去
        fragments.add(new CalendarFragment());
        try {
            if(SimpleNEUClassDao.getInstance().getClassArray() != null){
                fragments.add(new TimeTableFragment());
            }else {
                fragments.add(new ScheduleFragment());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        fragments.add(new ShareFragment());
        fragments.add(new PersonFragment());

        myTab.setTabMode(TabLayout.MODE_FIXED);

        //实例化适配器
        SectionsPagerAdapter sectionsPagerAdapter =new SectionsPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);

        //设置适配器
        myPager2.setAdapter(sectionsPagerAdapter);

        //TabLayout和Viewpager2进行关联
        new TabLayoutMediator(myTab, myPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
                tab.setIcon(icons.get(position));
            }
        }).attach();

    }
}