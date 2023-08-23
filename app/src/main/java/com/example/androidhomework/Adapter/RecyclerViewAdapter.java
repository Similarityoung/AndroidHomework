package com.example.androidhomework.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhomework.Dao.ScheduleDao;
import com.example.androidhomework.Entity.Schedule;
import com.example.androidhomework.R;
import com.example.androidhomework.Utils.AlarmTimer;
import com.example.androidhomework.Utils.dateUtils;
import com.github.gzuliyujiang.wheelpicker.DatimePicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatimePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity;
import com.github.gzuliyujiang.wheelpicker.widget.DatimeWheelLayout;

import java.text.ParseException;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    ScheduleDao scheduleDao;

    public RecyclerViewAdapter(Context context) throws Exception {

        this.context = context;
        scheduleDao = ScheduleDao.getInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TextView textView1 = holder.textView1;
        TextView textView2 = holder.textView2;
        TextView textView3 = holder.textView3;

        Button buttonUpdate = holder.buttonUpdate;
        Button buttonDelete = holder.buttonDelete;
        Schedule schedule = null;

        try {
            schedule = (Schedule) scheduleDao.getSchedules().get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        textView1.setText(schedule.getName());
        textView2.setText(schedule.getDate());
        try {
            textView3.setText(dateUtils.differenceInDays(schedule.getDate())+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    showDialog("修改日程",position);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    removeItem(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        try {
            AlarmTimer.setAlarmTimer(context,schedule);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        
        int size = 1;
        try {
            size = scheduleDao.getSchedules().size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    //添加一个item
    public void addItem(Schedule schedule) throws Exception {
        scheduleDao.add(schedule);
        int position =scheduleDao.getPosition(schedule);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,scheduleDao.getSchedules().size()-position);
    }

    //删除一个item
    public void removeItem(final int position) throws Exception {
        scheduleDao.delete(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,scheduleDao.getSchedules().size()-position);
    }

    //修改一个item
    public void updateItem(Schedule schedule,final int position) throws Exception {
        removeItem(position);
        addItem(schedule);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;

        Button buttonUpdate;
        Button buttonDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.tv1);
            textView2 = (TextView) itemView.findViewById(R.id.tv2);
            textView3 = (TextView) itemView.findViewById(R.id.tv3);

            buttonUpdate = (Button)itemView.findViewById(R.id.btnUpdate);
            buttonDelete = (Button)itemView.findViewById(R.id.btnDelete);
        }
    }

    private void showDialog(String name,int position) throws Exception {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();

        TextView textView = view.findViewById(R.id.testViewTitle);
        textView.setText(name);

        EditText editTextName = view.findViewById(R.id.editTextName);
        EditText editTextTime = view.findViewById(R.id.editTIme);

        Button btn_cancel_high_opion = view.findViewById(R.id.btn_agree);
        Button btn_agree_high_opion = view.findViewById(R.id.btn_cancel);

        Schedule schedule = (Schedule)scheduleDao.getSchedules().get(position);

        editTextName.setText(schedule.getName());
        editTextTime.setText(schedule.getDate());

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
                    updateItem(new Schedule(editTextName.getText().toString(),editTextTime.getText().toString()),position);
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

    private void showTime(EditText editText) {

        DatimePicker picker = new DatimePicker(getActivity());
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


    private Activity getActivity() {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

}




