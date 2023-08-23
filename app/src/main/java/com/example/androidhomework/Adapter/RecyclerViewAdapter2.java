package com.example.androidhomework.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhomework.Dao.SimpleNEUClassDao;
import com.example.androidhomework.Entity.Schedule;
import com.example.androidhomework.Entity.SimpleNEUClass;
import com.example.androidhomework.R;
import com.example.androidhomework.Utils.JsonUtil;
import com.example.androidhomework.Utils.dateUtils;

import java.text.ParseException;
import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder> {

    private Context context;
    private SimpleNEUClassDao simpleNEUClassDao;
    private static int day = 0;
    List<SimpleNEUClass> simpleNEUClassList;

    public RecyclerViewAdapter2(Context context) throws Exception {
        this.context = context;
        simpleNEUClassDao = SimpleNEUClassDao.getInstance();
        int day1 = dateUtils.daysBetween(day);
        simpleNEUClassList = simpleNEUClassDao.selectClassArray(dateUtils.getWeek(day1),dateUtils.getWeekDay(day1));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_2, parent, false));
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        TextView textView1 = holder.textView1;
        TextView textView2 = holder.textView2;
        TextView textView3 = holder.textView3;

        Button button = holder.button;

        SimpleNEUClass simpleNEUClass = simpleNEUClassList.get(position);
        textView1.setText(simpleNEUClass.getName());
        textView2.setText(simpleNEUClass.getPosition());
        textView3.setText(simpleNEUClass.getTime());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        int size = 0;
        try {
            size = simpleNEUClassList.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public void tomorrow(){
        day++;
    }

    public void yesterday(){
        day--;
    }

    public void today(){
        day = 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;

        Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.tv1);
            textView2 = (TextView) itemView.findViewById(R.id.tv2);
            textView3 = (TextView) itemView.findViewById(R.id.tv3);

            button = (Button)itemView.findViewById(R.id.btn);

        }
    }

    private void showDialog(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog2, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();

        EditText editTextName = view.findViewById(R.id.editTextName);
        EditText editTextTime = view.findViewById(R.id.editTIme);
        EditText editTextPlace = view.findViewById(R.id.editPlace);
        EditText editTextTeacher = view.findViewById(R.id.editTeacher);

        Button btn_cancel_high_opion = view.findViewById(R.id.btn_agree);

        SimpleNEUClass simpleNEUClass = simpleNEUClassList.get(position);
        editTextName.setText(simpleNEUClass.getName());
        editTextTime.setText(simpleNEUClass.getTime());
        editTextPlace.setText(simpleNEUClass.getPosition());
        editTextTeacher.setText(simpleNEUClass.getTeacher());


        btn_cancel_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
        dialog.getWindow().setLayout((context.getResources().getDisplayMetrics().widthPixels / 4 * 3), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

}
