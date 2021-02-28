package com.example.remindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> mTask;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public TaskAdapter(Context context, List<Task> task) {
        this.mInflater = LayoutInflater.from(context);
        this.mTask = task;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.task_list, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String task = mTask.get(position).getTitle();
        Boolean checked = mTask.get(position).isChecked();

        if(mTask.get(position).getImportance() == 0)
            holder.myImageView.setImageResource(R.drawable.ic_high_star);
        if(mTask.get(position).getImportance() == 1)
            holder.myImageView.setImageResource(R.drawable.ic_mid_star);
        if(mTask.get(position).getImportance() == 2)
            holder.myImageView.setImageResource(R.drawable.ic_low_star);

        holder.myTextView.setText(task);
        holder.myCheckBox.setChecked(checked);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mTask.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        CheckBox myCheckBox;
        ImageView myImageView;
        LinearLayout myLayout;

        ViewHolder(View itemView) {
            super(itemView);
            myCheckBox = itemView.findViewById(R.id.checkBox);
            myTextView = itemView.findViewById(R.id.task);
            myImageView = itemView.findViewById(R.id.imageView);
            myLayout = itemView.findViewById(R.id.viewLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mTask.get(id).getTime();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}