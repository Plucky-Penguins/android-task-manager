package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Controller.MainActivity;
import com.example.myapplication.Controller.TaskActivity;
import com.example.myapplication.Model.Subtask;
import com.example.myapplication.R;
import com.example.myapplication.SharedPref;

import java.util.Collections;
import java.util.List;

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.ViewHolder> {

    private final LayoutInflater mInflater;
    private final List<Subtask> mData;

    public SubtaskAdapter(Context context, List<Subtask> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.recyclerview_subtask_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subtask stask = mData.get(position);
        holder.subtaskNameView.setText(stask.getName());

        if(stask.isCompleted()) {
            holder.subtaskNameView.setBackgroundColor(Color.parseColor("#1abf00"));

        }
        else {
            holder.subtaskNameView.setBackgroundColor(Color.parseColor("#C4C4C4"));
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

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addSubtask(Subtask st) {
        mData.add(st);
        sortTaskList();
        notifyItemInserted(mData.size());
        MainActivity.adapter.notifyDataSetChanged();
        TaskActivity.updateProgressBar();
        SharedPref.writeToTasks();
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void deleteSubtask(Subtask st) {
        mData.remove(st);
        sortTaskList();
        TaskActivity.updateProgressBar();
        notifyDataSetChanged();
        MainActivity.adapter.notifyDataSetChanged();
        SharedPref.writeToTasks();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final TextView subtaskNameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subtaskNameView = itemView.findViewById(R.id.subtaskName);
            subtaskNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20*MainActivity.textScale);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            mData.get(getAdapterPosition()).setCompleted(!mData.get(getAdapterPosition()).isCompleted());
            SharedPref.writeToTasks();
            TaskActivity.adapter.notifyItemChanged(getAdapterPosition());
            TaskActivity.updateProgressBar();
            MainActivity.adapter.notifyDataSetChanged();

        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onLongClick(View view) {
            Subtask st = mData.get(getAdapterPosition());
            TaskActivity.deleteTaskDialog(st);
            return false;
        }
    }

}
