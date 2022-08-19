package com.dolla.mytasks.pojo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolla.mytasks.R;

import java.util.ArrayList;


public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private final ArrayList<TaskModel> list;
    ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public TaskListAdapter(Context context, ArrayList<TaskModel> list) {
        this.list = list;
        activity = (ItemClicked) context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
            itemView.setOnClickListener(view -> activity.onItemClicked(list.indexOf((TaskModel) view.getTag())));
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel model = list.get(position);
        holder.itemView.setTag(list.get(position));
        holder.tvTitle.setText(model.getTitle());
        String body = model.getBody();
        if (body.length() > 35)
            holder.tvBody.setText(body.substring(0, 30) + "...");
        else
            holder.tvBody.setText(body);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
