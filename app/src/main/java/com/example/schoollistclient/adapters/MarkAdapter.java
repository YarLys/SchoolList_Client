package com.example.schoollistclient.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoollistclient.Network;
import com.example.schoollistclient.R;
import com.example.schoollistclient.RecyclerViewClickListener;
import com.example.schoollistclient.models.Mark;
import com.example.schoollistclient.models.Student;
import com.example.schoollistclient.models.Subject;
import com.example.schoollistclient.models.Workload;

import java.util.ArrayList;

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Mark> marks;
    private static RecyclerViewClickListener itemListener;

    public MarkAdapter(Context context, ArrayList<Mark> marks, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.marks = marks;
        this.itemListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public MarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.mark, parent, false);
        return new MarkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkAdapter.ViewHolder holder, int position) {
        Mark mark = marks.get(position);
        holder.value.setText(mark.getValue().toString());
        holder.date.setText(mark.getDate());
        // Получим название предмета
        Network network = new Network();
        Handler subjectHandler = new Handler(msg -> {
            if (msg.what == 200) {
                Subject subject = (Subject) msg.obj;
                holder.subject.setText(subject.getName());
            }
            return false;
        });
        network.getSubjectById(subjectHandler, mark.getSubject_id());
        // Получим название уч. нагрузки
        Handler workloadHandler = new Handler(msg -> {
            if (msg.what == 200) {
                Workload workload = (Workload) msg.obj;
                holder.workload.setText(workload.getName_workload());
            }
            return false;
        });
        network.getWorkloadById(workloadHandler, mark.getWorkload_id());
    }

    @Override
    public int getItemCount() {
        return marks.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView subject;
        TextView workload;
        TextView value;
        TextView date;
        Button edit;
        public ViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.mark_subject);
            workload = itemView.findViewById(R.id.mark_workload);
            value = itemView.findViewById(R.id.mark_value);
            date = itemView.findViewById(R.id.mark_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewClickListened(v, this.getPosition());
        }
    }
}
