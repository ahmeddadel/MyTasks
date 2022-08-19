package com.dolla.mytasks;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dolla.mytasks.pojo.TaskModel;

public class ShowTaskActivity extends AppCompatActivity {

    TextView tvShowTitle, tvShowBody;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.open_left2right, R.anim.open_right2left);
        setContentView(R.layout.activity_show_task);

        tvShowTitle = findViewById(R.id.tvShowTitle);
        tvShowBody = findViewById(R.id.tvShowBody);
        ivBack = findViewById(R.id.ivBack);

        TaskModel data = (TaskModel) getIntent().getSerializableExtra("taskData");
        tvShowTitle.setText(data.getTitle());
        tvShowBody.setText(data.getBody());

        ivBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(R.anim.close_right2left, R.anim.close_left2right);
        }
    }

}