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
import android.widget.Toast;

import com.example.schoollistclient.adapters.StudentsAdapter;
import com.example.schoollistclient.models.Student;
import com.example.schoollistclient.models.Teacher;
import com.example.schoollistclient.retrofit.StudentAPI;
import com.example.schoollistclient.retrofit.TeacherAPI;
import com.example.schoollistclient.retrofit.retrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link list_edit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class list_edit extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    SwipeRefreshLayout refreshLayout;
    AutoCompleteTextView chooseClass;
    View view;
    ArrayList<Student> students = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    public list_edit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment list_edit.
     */
    // TODO: Rename and change types and number of parameters
    public static list_edit newInstance(String param1, String param2) {
        list_edit fragment = new list_edit();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_edit, container, false);

        // Получим список студентов и отобразим его
        showStudents();

        // Обработка обновления страницы пользователем
        refreshLayout = view.findViewById(R.id.Refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        // Обработка нажатия клавиши для добавления студента
        addStudent();

        // Если был выбран класс
        chooseClass = view.findViewById(R.id.Choose_class);
        chooseClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!chooseClass.getText().toString().equals("Любой")) {
                    String classId = chooseClass.getText().toString();
                    showStudentsByClass(classId); // отобразим учеников по классу
                }
                else showStudents();
            }
        });

        return view;
    }
    private void addStudent() {
        Button buttonAddStudent = view.findViewById(R.id.AddStudent);
        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText surname = view.findViewById(R.id.ET_student_surname);
                EditText f_name = view.findViewById(R.id.ET_student_fname);
                EditText l_name = view.findViewById(R.id.ET_student_lname);
                EditText phone = view.findViewById(R.id.ET_student_phone);
                EditText class_id = view.findViewById(R.id.ET_student_classid);
                if (Validator.checkSurname(surname) && Validator.checkFirstName(f_name) && Validator.checkPhone(phone)
                        && !class_id.getText().toString().isEmpty()) { // если данные корректны
                    Student student = new Student(f_name.getText().toString(), surname.getText().toString(), l_name.getText().toString(),
                            phone.getText().toString(), class_id.getText().toString());
                    Network network = new Network();
                    Handler studentHandler = new Handler(msg -> {
                        if (msg.what == 200) {
                            Toast.makeText(getContext(), "Вы успешно добавили ученика!", Toast.LENGTH_LONG).show();
                            showStudents();
                        }
                        else Toast.makeText(getContext(), "Ошибка добавления ученика: " + msg.what, Toast.LENGTH_LONG).show();
                        return false;
                    });
                    network.saveStudent(studentHandler, student);
                }
                else {
                    if (class_id.getText().toString().isEmpty()) class_id.setError("Класс не может быть пустым!");
                }
            }
        });
    }

    private void showStudents() {
        Network network = new Network();
        Handler studentsHandler = new Handler(msg -> {
            if (msg.what == 200) {
                RecyclerView recyclerView = view.findViewById(R.id.Students_list); // наш список для отображения учеников
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                students = (ArrayList<Student>) msg.obj;
                StudentsAdapter studentsAdapter = new StudentsAdapter(getContext(), students, this); // создаем адаптер
                recyclerView.setAdapter(studentsAdapter); // устанавливаем адаптер

                // Раз получили список студентов, можем отобразить возможные классы в выпадающем списке
                ArrayList<String> studentsClasses = new ArrayList<>();
                studentsClasses.add("Любой");
                for (int i = 0; i < students.size(); i++) {
                    if (!studentsClasses.contains(students.get(i).getId_class())) {
                        studentsClasses.add(students.get(i).getId_class());
                    }
                }
                // выпадающий список, где можно выбирать класс учеников
                chooseClass = view.findViewById(R.id.Choose_class);
                chooseClass.setText("Любой");
                // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.select_dialog_singlechoice, studentsClasses);
                // Определяем разметку для использования при выборе элемента
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                // Добавляем адаптер выпадающему списку
                chooseClass.setAdapter(adapter);
                chooseClass.setThreshold(1);
            }
            return false;
        });
        network.getStudents(studentsHandler);
    }

    private void showStudentsByClass(String classId) {
        Network network = new Network();
        Handler studentsHandler = new Handler(msg -> {
            if (msg.what == 200) {
                RecyclerView recyclerView = view.findViewById(R.id.Students_list); // наш список для отображения учеников
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                students = (ArrayList<Student>) msg.obj;
                StudentsAdapter studentsAdapter = new StudentsAdapter(getContext(), students, this); // создаем адаптер
                recyclerView.setAdapter(studentsAdapter); // устанавливаем адаптер
            }
            return false;
        });
        network.getStudentsByClass(studentsHandler, classId);
    }

    @Override
    public void onRefresh() {
        showStudents(); // обновим при возвращении, всё норм будет
        if (chooseClass.getText().toString().equals("Любой") || chooseClass.getText().toString().isEmpty()) showStudents();
        else showStudentsByClass(chooseClass.getText().toString());
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void recyclerViewClickListened(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("StudentInfo", students.get(position));
        Navigation.findNavController(view).navigate(R.id.action_list_edit_to_studentEdit, bundle);
    }
}