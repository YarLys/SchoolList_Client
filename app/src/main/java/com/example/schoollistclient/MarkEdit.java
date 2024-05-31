package com.example.schoollistclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoollistclient.models.Mark;
import com.example.schoollistclient.models.Student;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarkEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarkEdit extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "MarkInfo";
    private static final String ARG_PARAM2 = "MarkSubject";
    private static final String ARG_PARAM3 = "MarkWorkload";
    private static final String ARG_PARAM4 = "StudentInfo";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;
    private String mParam2;
    private String mParam3;
    private Serializable mParam4;
    private Mark mark;
    View view;

    public MarkEdit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MarkEdit.
     */
    // TODO: Rename and change types and number of parameters
    public static MarkEdit newInstance(Mark param1, String param2, String param3, Serializable param4) {
        MarkEdit fragment = new MarkEdit();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putSerializable(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getSerializable(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mark_edit, container, false);

        TextView subject = view.findViewById(R.id.TV_mark_subject);
        TextView workload = view.findViewById(R.id.TV_mark_workload);
        TextView value = view.findViewById(R.id.TV_mark_value);
        TextView date = view.findViewById(R.id.TV_mark_date);

        mark = (Mark) mParam1;
        subject.setText(mParam2);
        workload.setText(mParam3);
        value.setText(mark.getValue().toString());
        date.setText(mark.getDate());

        // Обработка нажатия клавиши обновления оценки
        updateMark();

        // Обработка нажатия клавиши удаления оценки
        deleteMark(mark);

        return view;
    }

    public void updateMark() {
        Button buttonUpdate = view.findViewById(R.id.B_update_mark);
        EditText new_value = view.findViewById(R.id.ET_new_mark_value);
        EditText new_date = view.findViewById(R.id.ET_new_mark_date);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new_value.getText().toString().isEmpty() && !new_date.getText().toString().isEmpty()) {
                    mark.setValue(Integer.valueOf(new_value.getText().toString()));
                    mark.setDate(new_date.getText().toString());
                    Network network = new Network();
                    Handler markHandler = new Handler(msg -> {
                        if (msg.what == 200) {
                            Log.d("Update_mark", "SUCCESS");
                            Toast.makeText(getContext(), "Вы успешно обновили оценку!", Toast.LENGTH_LONG).show();
                            TextView value = view.findViewById(R.id.TV_mark_value);
                            value.setText(new_value.getText().toString());
                            TextView date = view.findViewById(R.id.TV_mark_date);
                            date.setText(new_date.getText().toString());
                        }
                        else Log.d("Update_mark", "ERROR");
                        return false;
                    });
                    network.updateMark(markHandler, mark);
                }
                else {
                    if (new_value.getText().toString().isEmpty()) new_value.setError("Оценка не может быть пустой!");
                    if (new_date.getText().toString().isEmpty()) new_date.setError("Дата не может быть пустой!");
                }
            }
        });
    }

    public void deleteMark(Mark mark) {
        Button buttonDeleteMark = view.findViewById(R.id.B_delete_mark);
        buttonDeleteMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Network network = new Network();
                Handler markHandler = new Handler(msg -> {
                    if (msg.what == 200) {
                        Log.d("Delete_mark", "SUCCESS");
                        Toast.makeText(getContext(), "Вы успешно удалили оценку!", Toast.LENGTH_LONG).show();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("StudentInfo", (Student) mParam4);
                        Navigation.findNavController(view).navigate(R.id.action_markEdit_to_studentEdit);
                    }
                    else Log.d("Delete_mark", "ERROR");
                    return false;
                });
                network.deleteMarkById(markHandler, mark.getId());
            }
        });
    }
}