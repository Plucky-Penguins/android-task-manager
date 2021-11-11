package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Task> mData;


    public TaskAdapter(Context context, List<Task> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public Task getTaskAtPosition(int i) {
        return this.mData.get(i);
    }

    public List<Task> getmData() {
        return mData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.recyclerview_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mData.get(position);
        holder.taskNameView.setText(task.getName());

        int completedCount = 0;
        for (Subtask t : task.getSubtasks()) {
            if (t.isCompleted()) {
                completedCount++;
            }
        }


        if (completedCount == task.getSubtasks().size()) {
            task.setCompleted(true);
        } else {
            task.setCompleted(false);
        }



        holder.totalSubtaskView.setText(Integer.toString(completedCount) + "/" + Integer.toString(task.getSubtasks().size()) + " subtasks");

        if(task.isCompleted()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#24FF00"));
            holder.daysUntilDue.setText("Task Completed");
        }
        else if(task.getDaysRemaining() < 0) {
            Long overdue = Math.abs(task.getDaysRemaining());
            holder.itemView.setBackgroundColor(Color.parseColor("#FF7474"));
            holder.daysUntilDue.setText(Long.toString(overdue) + " days overdue!");
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#E1E1E1"));
            holder.daysUntilDue.setText(Long.toString(task.getDaysRemaining()) + " days remaining");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void sortTaskList() {
        Collections.sort(mData);
        notifyItemInserted(mData.size());
    }

    public void addTask(Task t) {
        mData.add(t);
        sortTaskList();
        notifyItemInserted(mData.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void deleteTask(Task t) {
        mData.remove(t);
        sortTaskList();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView taskNameView;
        TextView totalSubtaskView;
        TextView daysUntilDue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNameView = itemView.findViewById(R.id.taskName);
            totalSubtaskView = itemView.findViewById(R.id.totalSubtask);
            daysUntilDue = itemView.findViewById(R.id.daysUntilDue);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MainActivity.taskClicked(this.getAdapterPosition());
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onLongClick(View view) {
            Log.d("helpme", "long clicked");
            Task t = mData.get(getAdapterPosition());
            MainActivity.deleteTaskDialog(t);
            return false;
        }
    }
}
