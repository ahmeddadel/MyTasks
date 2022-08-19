package com.dolla.mytasks;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.dolla.mytasks.pojo.DatabaseAdapter;
import com.dolla.mytasks.pojo.TaskListAdapter;
import com.dolla.mytasks.pojo.TaskModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements TaskListAdapter.ItemClicked {

    TaskListAdapter myAdapter;
    RecyclerView recyclerView;
    ImageView ivAdd;
    DatabaseAdapter databaseAdapter;
    public static ArrayList<TaskModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.list);
        ivAdd = findViewById(R.id.ivAdd);

        recyclerView.setHasFixedSize(true);

        databaseAdapter = new DatabaseAdapter(this);
        list = databaseAdapter.retrieveEntry();

        myAdapter = new TaskListAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        ivAdd.setOnClickListener(view -> startActivity(new Intent(this, AddTaskActivity.class)));

        // Connect Swipe Features to the RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


    @Override
    protected void onStart() {
        super.onStart();
        // Refresh recyclerview list
        myAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {
        super.onStop();

        // updated Database
        databaseAdapter.insertEntry(list);
    }

    // Swipe Features
    TaskModel deletedTask = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            // No need for now
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:

                    deletedTask = list.get(position);
                    list.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    databaseAdapter.insertEntry(list);

                    Snackbar snackbar = Snackbar.make(recyclerView, deletedTask.getTitle(), Snackbar.LENGTH_LONG);
                    snackbar.setAction("Undo", view -> {
                        list.add(position, deletedTask);
                        myAdapter.notifyItemInserted(position);
                        databaseAdapter.insertEntry(list);
                    });
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                    params.gravity = Gravity.BOTTOM;
                    if (height <= 1780)
                        params.setMargins(0, 0, 0, height / 12);

                    else
                        params.setMargins(0, 0, 0, height / 100);

                    view.setLayoutParams(params);
                    snackbar.show();
                    break;


                case ItemTouchHelper.RIGHT:

                    Intent intent = new Intent(HomeActivity.this, EditTaskActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                    myAdapter.notifyItemChanged(position);
                    break;
            }
        }
    };


    // Show Task
    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(this, ShowTaskActivity.class);

        String title = list.get(index).getTitle();
        String body = list.get(index).getBody();

        TaskModel taskData = new TaskModel(title, body);
        intent.putExtra("taskData", taskData);
        startActivity(intent);
    }

}