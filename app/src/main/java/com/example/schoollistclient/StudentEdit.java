package com.example.schoollistclient;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoollistclient.adapters.MarkAdapter;
import com.example.schoollistclient.adapters.StudentsAdapter;
import com.example.schoollistclient.models.Mark;
import com.example.schoollistclient.models.Student;
import com.example.schoollistclient.models.Subject;
import com.example.schoollistclient.models.Workload;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentEdit extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "StudentInfo";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;
    private ArrayList<Mark> marks;
    View view;
    SwipeRefreshLayout refreshLayout;
    AutoCompleteTextView chooseSubject;
    AutoCompleteTextView chooseWorkload;
    AutoCompleteTextView chooseSubjectMenu;
    EditText ET_value;
    EditText ET_date;
    ArrayList<Subject> subjects;
    ArrayList<Workload> workloads;

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
        chooseSubjectMenu = view.findViewById(R.id.Choose_subject_menu);
        surname.setText(student.getSurname());
        fname.setText(student.getFirst_name());
        lname.setText(student.getLast_name());
        phone.setText(student.getPhone());
        classid.setText(student.getId_class());

        // Получим список всех уч. нагрузок и предметов, чтобы добавить их в выпадающие списки
        getAndShowSubjects();
        getAndShowWorkloads();

        // Получим список оценок ученика и отобразим их
        showStudentMarks(student.getId());

        // Обработка обновления страницы пользователем
        chooseSubjectMenu.clearListSelection();
        refreshLayout = view.findViewById(R.id.Refresh_layout_2);
        refreshLayout.setOnRefreshListener(this);

        // Обработка нажатия клавиши добавления оценки
        addMark(student.getId());

        // Обработка нажатия клавиши удаления
        deleteStudent(student);

        // Если был выбран предмет
        chooseSubjectMenu = view.findViewById(R.id.Choose_subject_menu);
        chooseSubjectMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!chooseSubjectMenu.getText().toString().equals("Любой") && !chooseSubjectMenu.getText().toString().isEmpty()) {
                    showStudentSubjectMarks(student.getId());
                }
                else showStudentMarks(student.getId());
            }
        });

        return view;
    }

    public void showStudentMarks(Integer studentId) {
        Network network = new Network();
        Handler marksHandler = new Handler(msg -> {
            if (msg.what == 200) {
                RecyclerView recyclerView = view.findViewById(R.id.Marks_list); // наш список для отображения учеников
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                marks = (ArrayList<Mark>) msg.obj;
                MarkAdapter markAdapter = new MarkAdapter(getContext(), marks, this); // создаем адаптер
                recyclerView.setAdapter(markAdapter); // устанавливаем адаптер
            }
            return false;
        });
        network.getStudentMarks(marksHandler, studentId);
    }
    public void showStudentSubjectMarks(Integer studentId) {
        Integer subjectId = 0;
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getName().equals(chooseSubjectMenu.getText().toString())) {
                subjectId = subjects.get(i).getId();
                break;
            }
        }
        Network network = new Network();
        Handler marksHandler = new Handler(msg -> {
            if (msg.what == 200) {
                RecyclerView recyclerView = view.findViewById(R.id.Marks_list); // наш список для отображения учеников
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                marks = (ArrayList<Mark>) msg.obj;
                MarkAdapter markAdapter = new MarkAdapter(getContext(), marks, this); // создаем адаптер
                recyclerView.setAdapter(markAdapter); // устанавливаем адаптер
            }
            return false;
        });
        network.getStudentSubjectMarks(marksHandler, studentId, subjectId);
    }

    public void getAndShowSubjects() {
        Network network = new Network();
        Handler subjectsHandler = new Handler(msg -> {
            if (msg.what == 200) {
                subjects = (ArrayList<Subject>) msg.obj;
                ArrayList<String> names = new ArrayList<>();
                for (int i = 0; i < subjects.size(); i++) {
                    names.add(subjects.get(i).getName());
                }
                // выпадающий список, где можно выбирать предмет, по кот. ставится оценка
                chooseSubject = view.findViewById(R.id.Choose_mark_subject);
                // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.select_dialog_singlechoice, names);
                // Определяем разметку для использования при выборе элемента
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                // Добавляем адаптер выпадающему списку
                chooseSubject.setAdapter(adapter);
                chooseSubject.setThreshold(1);

                ArrayList<String> names2 = names;
                // выпадающий список, где можно выбирать предмет, оценки за который будут отображаться
                chooseSubjectMenu = view.findViewById(R.id.Choose_subject_menu);
                names2.add("Любой");
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(), android.R.layout.select_dialog_singlechoice, names2);
                adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                chooseSubjectMenu.setAdapter(adapter2);
                chooseSubjectMenu.setThreshold(1);
            }
            return false;
        });
        network.getSubjects(subjectsHandler);
    }
    public void getAndShowWorkloads() {
        Network network = new Network();
        Handler workloadsHandler = new Handler(msg -> {
            if (msg.what == 200) {
                workloads = (ArrayList<Workload>) msg.obj;
                ArrayList<String> names = new ArrayList<>();
                for (int i = 0; i < workloads.size(); i++) {
                    names.add(workloads.get(i).getName_workload());
                }
                // выпадающий список, где можно выбирать предмет, по кот. ставится оценка
                chooseWorkload = view.findViewById(R.id.Choose_mark_workload);
                // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.select_dialog_singlechoice, names);
                // Определяем разметку для использования при выборе элемента
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                // Добавляем адаптер выпадающему списку
                chooseWorkload.setAdapter(adapter);
                chooseWorkload.setThreshold(1);
            }
            return false;
        });
        network.getWorkloads(workloadsHandler);
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
    public void addMark(Integer studentId) {
        ET_value = view.findViewById(R.id.ET_mark_value);
        ET_date = view.findViewById(R.id.ET_mark_date);
        Button buttonAdd = view.findViewById(R.id.B_add_mark);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chooseSubject.getText().toString().isEmpty() && !chooseWorkload.getText().toString().isEmpty() &&
                !ET_value.getText().toString().isEmpty() && !ET_date.getText().toString().isEmpty()) {

                    Mark mark = new Mark();
                    mark.setId_student(studentId);
                    mark.setValue(Integer.valueOf(ET_value.getText().toString()));
                    mark.setDate(ET_date.getText().toString());
                    Integer subjId = 0;
                    Integer workId = 0;
                    for (int i = 0; i < subjects.size(); i++) {
                        if (subjects.get(i).getName().equals(chooseSubject.getText().toString())) {
                            subjId = subjects.get(i).getId();
                            break;
                        }
                    }
                    for (int i = 0; i < workloads.size(); i++) {
                        if (workloads.get(i).getName_workload().equals(chooseWorkload.getText().toString())) {
                            workId = workloads.get(i).getId();
                            break;
                        }
                    }
                    mark.setSubject_id(subjId);
                    mark.setWorkload_id(workId);

                    Network network = new Network();
                    Handler markHandler = new Handler(msg -> {
                        if (msg.what == 200) {
                            Toast.makeText(getContext(), "Вы успешно добавили оценку!", Toast.LENGTH_LONG).show();
                            showStudentMarks(studentId);
                        }
                        else Toast.makeText(getContext(), "Ошибка добавления оценки: " + msg.what, Toast.LENGTH_LONG).show();
                        return false;
                    });
                    network.saveMark(markHandler, mark);
                }
                else {
                    if (chooseSubject.getText().toString().isEmpty()) chooseSubject.setError("Выберите предмет!");
                    if (chooseWorkload.getText().toString().isEmpty()) chooseWorkload.setError("Выберите уч. нагрузку!");
                    if (ET_value.getText().toString().isEmpty()) ET_value.setError("Оценка не может быть пустой!");
                    if (ET_date.getText().toString().isEmpty()) ET_date.setError("Дата не может быть пустой!");
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        showStudentMarks(((Student) mParam1).getId());
        //if (!chooseSubjectMenu.getText().toString().isEmpty() && !chooseSubjectMenu.getText().toString().equals("Любой")) showStudentSubjectMarks(((Student) mParam1).getId());
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void recyclerViewClickListened(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("MarkInfo", marks.get(position));
        TextView tv1 = view.findViewById(R.id.mark_subject);
        TextView tv2 = view.findViewById(R.id.mark_workload);
        bundle.putString("MarkSubject", tv1.getText().toString());
        bundle.putString("MarkWorkload", tv2.getText().toString());
        bundle.putSerializable("StudentInfo", (Student) mParam1);
        Navigation.findNavController(view).navigate(R.id.action_studentEdit_to_markEdit, bundle);
    }
}