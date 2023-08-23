package com.example.androidhomework.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidhomework.Activity.MainActivityPerson;
import com.example.androidhomework.Dao.UserDao;
import com.example.androidhomework.Entity.User;
import com.example.androidhomework.R;


public class PersonFragment extends Fragment {

    private View mainView;
    private Context context;
    private boolean isFirstLoading = true;

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    TextView textView1;
    TextView textView2;

    UserDao userDao;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_person, container, false);
        context = mainView.getContext();

        button1 = mainView.findViewById(R.id.button1);
        button2 = mainView.findViewById(R.id.button2);
        button3 = mainView.findViewById(R.id.button3);
        button4 = mainView.findViewById(R.id.button4);

        textView1 = mainView.findViewById(R.id.textViewName);
        textView2 = mainView.findViewById(R.id.textViewId);

        userDao = UserDao.getInstance();

//        try {
//            userDao.setUsers(new User("相似","20206658","软件学院","18936190656"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            user = userDao.getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        textView1.setText("   "+user.getName());
        textView2.setText("     学号："+user.getId());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivityPerson.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, "该功能还在设计中", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, "该功能还在设计中", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, "该功能还在设计中", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
            try {
                user = userDao.getUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
            textView1.setText("   "+user.getName());
            textView2.setText("     学号："+user.getId());
        }

        isFirstLoading = false;
    }

}