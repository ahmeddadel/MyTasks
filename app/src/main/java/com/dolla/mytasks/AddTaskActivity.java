package com.dolla.mytasks;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dolla.mytasks.pojo.TaskModel;

import es.dmoral.toasty.Toasty;

public class AddTaskActivity extends AppCompatActivity {

    EditText etTitle, etBody;
    Button btAdd;
    ImageView ivBack;
    TaskModel taskData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.open_left2right, R.anim.open_right2left);
        setContentView(R.layout.activity_add_task);

        etTitle = findViewById(R.id.etTitle);
        etBody = findViewById(R.id.etBody);
        btAdd = findViewById(R.id.btAdd);
        ivBack = findViewById(R.id.ivBack);

        btAdd.setOnClickListener(view -> addTask());
        ivBack.setOnClickListener(view -> finish());
    }

    private void addTask() {
        String title = etTitle.getText().toString().trim();
        String body = etBody.getText().toString().trim();
        if (title.isEmpty() && body.isEmpty())
            Toasty.warning(this, "Task can't be Empty!", Toast.LENGTH_SHORT, true).show();
        else {
            taskData = new TaskModel(title, body);
            HomeActivity.list.add(taskData);
            Toasty.success(this, "Successfully Added!", Toast.LENGTH_SHORT, true).show();
            finish();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(R.anim.close_right2left, R.anim.close_left2right);
        }
    }

}