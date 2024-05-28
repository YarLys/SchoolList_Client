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
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoollistclient.models.Student;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentEdit extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "StudentInfo";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;
    View view;

    public StudentEdit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment StudentEdit.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentEdit newInstance(Serializable param1) {
        StudentEdit fragment = new StudentEdit();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_student_edit, container, false);
        Student student = (Student) mParam1;
        TextView surname = view.findViewById(R.id.TV_student_surname);
        TextView fname = view.findViewById(R.id.TV_student_fname);
        TextView lname = view.findViewById(R.id.TV_student_lname);
        TextView phone = view.findViewById(R.id.TV_student_phone);
        TextView classid = view.findViewById(R.id.TV_student_classid);
        surname.setText(student.getSurname());
        fname.setText(student.getFirst_name());
        lname.setText(student.getLast_name());
        phone.setText(student.getPhone());
        classid.setText(student.getId_class());

        // Обработка нажатия клавиши удаления
        deleteStudent(student);

        return view;
    }

    public void deleteStudent(Student student) {
        Button buttonDelete = view.findViewById(R.id.B_delete_student);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Network network = new Network();
                Handler studentHandler = new Handler(msg -> {
                    if (msg.what == 200) {
                        Log.d("Delete_student", "SUCCESS");
                        Toast.makeText(getContext(), "Вы успешно удалили ученика!", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.action_studentEdit_to_list_edit);
                    }
                    else Log.d("Delete_student", "ERROR");
                    return false;
                });
                network.deleteStudentById(studentHandler, student.getId());
            }
        });
    }
}