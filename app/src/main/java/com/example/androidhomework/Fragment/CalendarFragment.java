package com.example.androidhomework.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidhomework.Adapter.RecyclerViewAdapter;
import com.example.androidhomework.Dao.SimpleNEUClassDao;
import com.example.androidhomework.Entity.Schedule;
import com.example.androidhomework.Activity.MainActivity;
import com.example.androidhomework.Entity.SimpleNEUClass;
import com.example.androidhomework.R;
import com.example.androidhomework.Utils.dateUtils;
import com.github.gzuliyujiang.dialog.DialogConfig;
import com.github.gzuliyujiang.dialog.DialogStyle;
import com.github.gzuliyujiang.wheelpicker.DatimePicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatimePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity;
import com.github.gzuliyujiang.wheelpicker.widget.DatimeWheelLayout;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.Miui9Calendar;
import com.necer.entity.CalendarDate;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.painter.InnerPainter;
import com.necer.utils.CalendarUtil;

import org.joda.time.LocalDate;

import java.util.List;

public class CalendarFragment extends Fragment {

    private View mainView;
    private Context context;

    SwipeRefreshLayout refresh_layout;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    Miui9Calendar miui9Calendar;
    SimpleNEUClassDao simpleNEUClassDao;
    List<SimpleNEUClass> simpleNEUClassList;
    private boolean isFirstLoading = true;

    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_calendar, container, false);
        context = mainView.getContext();

        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setListener();

        return mainView;
    }

    //    数据初始化
    public void initView() throws Exception {

        simpleNEUClassDao = SimpleNEUClassDao.getInstance();

        refresh_layout = mainView.findViewById(R.id.refresh_layout);
        recyclerView = mainView.findViewById(R.id.recyclerView);
        miui9Calendar = mainView.findViewById(R.id.miui9Calendar);

        mTextMonthDay = mainView.findViewById(R.id.tv_month_day);
        mTextYear = mainView.findViewById(R.id.tv_year);
        mTextLunar = mainView.findViewById(R.id.tv_lunar);
        mTextCurrentDay = mainView.findViewById(R.id.tv_current_day);

        DialogConfig.setDialogStyle(DialogStyle.Two);

        miui9Calendar.setWeekHoldEnable(true);

        InnerPainter innerPainter = (InnerPainter) miui9Calendar.getCalendarPainter();
        innerPainter.setLegalHolidayList(dateUtils.holidayList, dateUtils.workdayList);

//        scheduleDao = ScheduleDao.getInstance();
//        innerPainter.setReplaceLunarStrMap(scheduleDao.getAllName());
//        innerPainter.setReplaceLunarColorMap(scheduleDao.getAllColor());

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewAdapter = new RecyclerViewAdapter(context);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh_layout.setRefreshing(false);
                    }
                }, 1000);

            }
        });
    }

    //    设置监听类
    public void setListener() {

        mainView.findViewById(R.id.fl_current).setOnClickListener((v) -> {
            miui9Calendar.toToday();
        });

        mainView.findViewById(R.id.imageViewAdd).setOnClickListener(v -> {
            showDialog("新日程");
        });

        miui9Calendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {

                try {
                    int day = dateUtils.daysBetween(localDate.toDate());
                    simpleNEUClassList = simpleNEUClassDao.selectClassArray(dateUtils.getWeek(day),dateUtils.getWeekDay(day));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String str = localDate + "课程有:";

                for(SimpleNEUClass simpleNEUClass:simpleNEUClassList){
                    str += "\n"+simpleNEUClass.getName();
                }

                Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
                toast.show();

                mTextMonthDay.setText(localDate.getMonthOfYear() + "月" + localDate.getDayOfMonth() + "日");
                mTextYear.setText(year + "");

                CalendarDate calendarDate = CalendarUtil.getCalendarDate(localDate);
                if (calendarDate.lunarHoliday != "") {
                    mTextLunar.setText(calendarDate.lunarHoliday);
                } else if (calendarDate.solarHoliday != "") {
                    mTextLunar.setText(calendarDate.solarHoliday);
                } else if (calendarDate.solarTerm != null) {
                    mTextLunar.setText(calendarDate.solarTerm);
                } else {
                    mTextLunar.setText(calendarDate.lunar.lunarDayStr);
                }

                mTextCurrentDay.setText(localDate.getDayOfMonth() + "");
            }

        });

    }

    private void showTime(EditText editText) {

        DatimePicker picker = new DatimePicker((MainActivity) getActivity());
        DatimeWheelLayout wheelLayout = picker.getWheelLayout();
        picker.setOnDatimePickedListener(new OnDatimePickedListener() {
            @Override
            public void onDatimePicked(int year, int month, int day, int hour, int minute, int second) {
                String month1 = month + "";
                if(month < 10){
                    month1 = "0" + month;
                }
                String day1 = day + "";
                if(day < 10){
                    day1 = "0" + day;
                }
                String hour1 = hour + "";
                if(hour < 10){
                    hour1 = "0" + hour;
                }
                String minute1 = minute + "";
                if(minute < 10){
                    minute1 = "0" + minute1;
                }


                String text = year + "-" + month1 + "-" + day1 + " " + hour1 + ":" + minute1;
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                editText.setText(text);
            }
        });
        wheelLayout.setDateMode(0);
        wheelLayout.setTimeMode(0);
        wheelLayout.setRange(DatimeEntity.now(), DatimeEntity.yearOnFuture(10));
        wheelLayout.setDateLabel("年", "月", "日");
        wheelLayout.setTimeLabel("时", "分", "秒");
        picker.show();

    }

    //初始化并弹出添加对话框方法
    private void showDialog(String name) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();

        TextView textView = view.findViewById(R.id.testViewTitle);
        textView.setText(name);

        EditText editTextName = view.findViewById(R.id.editTextName);
        EditText editTextTime = view.findViewById(R.id.editTIme);

        Button btn_cancel_high_opion = view.findViewById(R.id.btn_agree);
        Button btn_agree_high_opion = view.findViewById(R.id.btn_cancel);

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTime(editTextTime);

            }
        });

        btn_cancel_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    recyclerViewAdapter.addItem(new Schedule(editTextName.getText().toString(),editTextTime.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });

        btn_agree_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //... To-do
                dialog.dismiss();
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
        dialog.getWindow().setLayout((context.getResources().getDisplayMetrics().widthPixels / 4 * 3), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
            try {
                recyclerViewAdapter = new RecyclerViewAdapter(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(recyclerViewAdapter);
        }

        isFirstLoading = false;
    }
}
