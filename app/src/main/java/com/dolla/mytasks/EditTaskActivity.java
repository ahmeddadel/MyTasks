package com.dolla.mytasks;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dolla.mytasks.pojo.TaskModel;

import es.dmoral.toasty.Toasty;

public class EditTaskActivity extends AppCompatActivity {

    EditText etTitle_Edit, etBody_Edit;
    Button btEdit;
    ImageView ivBack;
    TaskModel taskData;
    int position;
    String title, body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.close_right2left, R.anim.close_left2right);
        setContentView(R.layout.activity_edit_task);

        etTitle_Edit = findViewById(R.id.etTitle_Edit);
        etBody_Edit = findViewById(R.id.etBody_Edit);
        btEdit = findViewById(R.id.btEdit);
        ivBack = findViewById(R.id.ivBack);

        position = getIntent().getIntExtra("position", 0);

        title = HomeActivity.list.get(position).getTitle();
        body = HomeActivity.list.get(position).getBody();
        etTitle_Edit.setText(title);
        etBody_Edit.setText(body);

        btEdit.setOnClickListener(view -> editTask());
        ivBack.setOnClickListener(view -> finish());


    }


    private void editTask() {
        taskData = HomeActivity.list.get(position);
        title = etTitle_Edit.getText().toString().trim();
        body = etBody_Edit.getText().toString().trim();
        if (title.isEmpty() && body.isEmpty())
            Toasty.warning(this, "Task can't be Empty!", Toast.LENGTH_SHORT, true).show();
        else {
            taskData.setTitle(title);
            taskData.setBody(body);
            Toasty.success(this, "Successfully Edited!", Toast.LENGTH_SHORT, true).show();
            finish();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(R.anim.open_left2right, R.anim.open_right2left);
        }
    }

}