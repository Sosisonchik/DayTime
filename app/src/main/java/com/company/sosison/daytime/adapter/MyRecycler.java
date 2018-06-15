package com.company.sosison.daytime.adapter;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.company.sosison.daytime.DBhelper;
import com.company.sosison.daytime.R;
import com.company.sosison.daytime.pojo.Task;

import java.util.ArrayList;

public class MyRecycler extends RecyclerView.Adapter<MyRecycler.MyViewHolder> {
    ArrayList<Task> taskArrayList = new ArrayList<Task>();

    public void add(Task task){
        taskArrayList.add(task);
    }

    public void clear(){
       taskArrayList.clear();
    }





    @NonNull
    @Override
    public MyRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyRecycler.MyViewHolder holder,int position) {
        holder.bind(taskArrayList.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBhelper dBhelper = new DBhelper(holder.itemView.getContext());
                SQLiteDatabase db = dBhelper.getWritableDatabase();

                db.delete(DBhelper.TABLE_TASK, DBhelper.KEY_TASK+"='"+taskArrayList.get(holder.getAdapterPosition()).getText()+"'",null);
                taskArrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), taskArrayList.size());
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int checked;
                if (isChecked){
                    checked = 1;
                }else {
                    checked = 0;
                }
                DBhelper dBhelper = new DBhelper(holder.itemView.getContext());
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put(DBhelper.KEY_CHECK,checked);
                db.update(DBhelper.TABLE_TASK,contentValues,DBhelper.KEY_TASK+"='"+taskArrayList.get(holder.getAdapterPosition()).getText()+"'",null);
            }
        });

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked;
                Task task = taskArrayList.get(holder.getAdapterPosition());
                if (holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);
                    checked = 0;
                }else{
                    holder.checkBox.setChecked(true);
                    checked = 1;
                }

                DBhelper dBhelper = new DBhelper(holder.itemView.getContext());
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put(DBhelper.KEY_CHECK,checked);
                db.update(DBhelper.TABLE_TASK,contentValues,DBhelper.KEY_TASK+"='"+taskArrayList.get(holder.getAdapterPosition()).getText()+"'",null);

            }
        });

    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public void bind(Task task){
            text.setText(task.getText());
            date.setText(task.getDate());
            mark.setText(task.getMark());
            checkBox.setChecked(task.isCheck());
        }

        TextView text;
        TextView date;
        TextView mark;
        CheckBox checkBox;
        Button delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.task_text);
            date = itemView.findViewById(R.id.date);
            mark = itemView.findViewById(R.id.mark_view);
            checkBox = itemView.findViewById(R.id.checkbox);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
