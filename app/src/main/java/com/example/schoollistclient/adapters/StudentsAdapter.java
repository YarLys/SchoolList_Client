package com.example.schoollistclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.schoollistclient.R;
import com.example.schoollistclient.RecyclerViewClickListener;
import com.example.schoollistclient.models.Student;

import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Student> students;
    private static RecyclerViewClickListener itemListener;

    public StudentsAdapter(Context context, ArrayList<Student> students, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.students = students;
        this.itemListener = recyclerViewClickListener;
    }

    @Override
    public StudentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentsAdapter.ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.first_name.setText(student.getFirst_name());
        holder.surname.setText(student.getSurname());
        holder.last_name.setText(student.getLast_name());
        holder.phone.setText(student.getPhone());
        holder.id_class.setText(student.getId_class());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView first_name;
        TextView surname;
        TextView last_name;
        TextView phone;
        TextView id_class;
        Button edit;
        public ViewHolder(View itemView) {
            super(itemView);
            first_name = itemView.findViewById(R.id.student_fname);
            surname = itemView.findViewById(R.id.student_surname);
            last_name = itemView.findViewById(R.id.student_lname);
            phone = itemView.findViewById(R.id.student_phone);
            id_class = itemView.findViewById(R.id.student_class);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewClickListened(v, this.getPosition());
        }
    }
}
