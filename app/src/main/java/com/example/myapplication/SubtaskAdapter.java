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

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Subtask> mData;

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
            holder.subtaskNameView.setBackgroundColor(Color.parseColor("#24FF00"));

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addSubtask(Subtask st) {
        mData.add(st);
        sortTaskList();
        notifyItemInserted(mData.size());
        MainActivity.adapter.notifyDataSetChanged();
        TaskActivity.updateProgressBar();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView subtaskNameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subtaskNameView = itemView.findViewById(R.id.subtaskName);
            itemView.setOnClickListener(this);
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (mData.get(getAdapterPosition()).isCompleted()) {
                mData.get(getAdapterPosition()).setCompleted(false);
            } else {
                mData.get(getAdapterPosition()).setCompleted(true);
            }
            TaskActivity.adapter.notifyItemChanged(getAdapterPosition());
            TaskActivity.updateProgressBar();
            MainActivity.adapter.notifyDataSetChanged();
        }


    }

}
