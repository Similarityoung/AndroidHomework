package com.example.androidhomework.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidhomework.Dao.UserDao;
import com.example.androidhomework.Entity.User;
import com.example.androidhomework.R;

public class MainActivityPerson extends AppCompatActivity {

    EditText editTetName;
    EditText editTextId;
    EditText editTextCollege;
    EditText editTextTelephone;
    Button button;

    UserDao userDao;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_person);

        editTetName = findViewById(R.id.editName);
        editTextId = findViewById(R.id.editId);
        editTextCollege = findViewById(R.id.editCollege);
        editTextTelephone = findViewById(R.id.editTelephone);
        button = findViewById(R.id.btn_agree);

        userDao = UserDao.getInstance();

        try {
            user = userDao.getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        editTetName.setText(user.getName());
        editTextId.setText(user.getId());
        editTextCollege.setText(user.getCollege());
        editTextTelephone.setText(user.getTelephone());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setName(editTetName.getText().toString());
                user.setId(editTextId.getText().toString());
                user.setCollege(editTextCollege.getText().toString());
                user.setTelephone(editTextTelephone.getText().toString());

                try {
                    userDao.setUsers(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();

            }
        });

    }
}