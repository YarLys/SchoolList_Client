package com.example.schoollistclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoollistclient.models.Group;
import com.example.schoollistclient.models.Teacher;
import com.example.schoollistclient.retrofit.GroupAPI;
import com.example.schoollistclient.retrofit.LoginAPI;
import com.example.schoollistclient.retrofit.TeacherAPI;
import com.example.schoollistclient.retrofit.retrofitService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Functions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Functions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "TeacherInfo";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;

    private Button buttonAddClass;
    private Button buttonAddSubject;
    private Button buttonAddWorkload;
    private EditText className;
    private EditText classCount;
    private AutoCompleteTextView selectedTeacher;

    public Functions() {
        // Required empty public constructor
    }
    public static Functions newInstance(Serializable param1) {
        Functions fragment = new Functions();
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
        View view = inflater.inflate(R.layout.fragment_functions, container, false);

        buttonAddClass = view.findViewById(R.id.Functions_add_class);
        buttonAddSubject = view.findViewById(R.id.Functions_add_discipline);
        buttonAddWorkload = view.findViewById(R.id.Functions_add_workload);
        className = view.findViewById(R.id.ET_classname);
        classCount = view.findViewById(R.id.ET_classcount);

        // Получим список учителей. Создадим из него массив строк, хранящих ФИО каждого и поместим его в адаптер спиннера
        ArrayList<String> teachersFIO = new ArrayList<>();
        getTeachers(teachersFIO); // Вызовем метод, сохраняющий в массив ФИО всех учителей. Посылает запрос на получение всех Teachers на сервер

        // Короче надо будет переписать на выпадающий список, где можно выбирать, тк это не соответствует задумке
        selectedTeacher = view.findViewById(R.id.Choose_teacher);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice, teachersFIO);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Добавляем адаптер выпадающему списку
        selectedTeacher.setAdapter(adapter);
        selectedTeacher.setThreshold(1);
        /*selectedTeacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Item selected", "OK" + parent.getItemAtPosition(position).toString());
                // Получим выбранного учителя
                //selectedTeacher.setText(parent.getItemAtPosition(position).toString());
            }
        });*/

        // Теперь обработаем нажатие на кнопку "Добавить класс" и в нём пошлём запрос и обработаем ответ сервера
        addClassListener();

        return view;
    }

    public void addClassListener() { // функция обработчик для кнопки добавления класса. посылает запрос на добавление группы
        buttonAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перед этим проверим, всё ли заполнил пользователь
                if (!className.getText().toString().isEmpty() && (!classCount.getText().toString().isEmpty() || classCount.getText().toString().equals("0")) &&
                        !selectedTeacher.getText().toString().isEmpty()) { // Переписать через класс Validator
                    retrofitService retrofitService = new retrofitService();
                    GroupAPI groupAPI = retrofitService.getRetrofit().create(GroupAPI.class);

                    Group group = new Group();
                    group.setId(className.getText().toString());
                    String teacher = selectedTeacher.getText().toString();
                    Log.d("CHOSEN_TEACHER", teacher.substring(0, teacher.indexOf(" ")));
                    group.setId_teacher(Integer.valueOf(teacher.substring(0, teacher.indexOf(" "))));
                    group.setCount(Integer.valueOf(classCount.getText().toString()));
                    groupAPI.saveGroup(group)
                            .enqueue(new Callback<Group>() { // запрос
                                @Override
                                public void onResponse(Call<Group> call, Response<Group> response) {
                                    Log.d("ADD_CLASS", response.body().toString());
                                    String response_code = response.toString().substring(response.toString().indexOf("code=") + 5, response.toString().indexOf("code=") + 8);
                                    if (response_code.equals("200")) {
                                        Log.d("ADD_CLASS", "SUCCESSFUL");
                                        Toast.makeText(getContext(), "Вы успешно добавили группу!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Log.d("ADD_CLASS_ERR", response_code);
                                        Toast.makeText(getContext(), "Ошибка добавления группы: " + response_code, Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Group> call, Throwable t) {
                                    Log.d("ADD_CLASS_SERV_ERR", t.toString());
                                    Toast.makeText(getContext(), "Ошибка на сервере: " + t, Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else {
                    if (className.getText().toString().isEmpty()) className.setError("Название класса не может быть пустым!");
                    if (classCount.getText().toString().isEmpty() || classCount.getText().toString().equals("0")) classCount.setError("Количество учеников не может быть пустым!");
                    if (selectedTeacher.getText().toString().isEmpty()) selectedTeacher.setError("Выберите учителя!");
                }
            }
        });
    }

    public void getTeachers(ArrayList<String> teachersFIO) {
        retrofitService retrofitService = new retrofitService();
        TeacherAPI teacherAPI = retrofitService.getRetrofit().create(TeacherAPI.class);
        teacherAPI.getAllTeachers()
                .enqueue(new Callback<List<Teacher>>() {
                    @Override
                    public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                        Log.d("GET_TEACHERS", response.body().toString());
                        Log.d("asdl;kfj", "asdflkjsal;df");
                        String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                        if (response_code.equals("200")) {
                            List<Teacher> teachers = response.body();
                            for (int i = 0; i < teachers.size(); i++) {
                                teachersFIO.add(teachers.get(i).getId() + " " + teachers.get(i).getFirst_name() + " "
                                        + teachers.get(i).getSurname() + " " + teachers.get(i).getLast_name());
                            }
                        }
                        else {
                            Log.d("GET_TEACHERS_ERROR", response_code);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Teacher>> call, Throwable t) {
                        Log.d("GET_TEACH_SERV_ERR", t.toString());
                    }
                });
    }
}