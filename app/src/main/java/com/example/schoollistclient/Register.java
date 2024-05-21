package com.example.schoollistclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoollistclient.models.Teacher;
import com.example.schoollistclient.retrofit.TeacherAPI;
import com.example.schoollistclient.retrofit.retrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        Button button = view.findViewById(R.id.BREGTOPROF);
        //button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_register_to_profile));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText first_name = view.findViewById(R.id.ETfname);
                EditText surname = view.findViewById(R.id.ETsurname);
                EditText last_name = view.findViewById(R.id.ETlname);
                EditText phone = view.findViewById(R.id.ETphone);
                EditText email = view.findViewById(R.id.ETemail);
                EditText password = view.findViewById(R.id.ETpassword);

                if (Validator.checkFirstName(first_name) && Validator.checkSurname(surname)
                    && Validator.checkPhone(phone) && Validator.checkEmail(email) && Validator.checkPassword(password)) {

                    retrofitService retrofitService = new retrofitService();
                    TeacherAPI teacherAPI = retrofitService.getRetrofit().create(TeacherAPI.class);

                    Teacher teacher = new Teacher();
                    teacher.setFirst_name(first_name.getText().toString());
                    teacher.setSurname(surname.getText().toString());
                    teacher.setLast_name(last_name.getText().toString());
                    teacher.setPhone(phone.getText().toString());
                    teacher.setEmail(email.getText().toString());
                    teacher.setPassword(password.getText().toString());
                    teacherAPI.saveTeacher(teacher)
                            .enqueue(new Callback<Teacher>() {
                            @Override
                            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                                String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                                Log.d("Response_Code", response_code);
                                if (response_code.equals("200")) {
                                    Navigation.findNavController(view).navigate(R.id.action_register_to_profile);
                                    Toast.makeText(getContext(), "Вы успешно зарегистрированы!", Toast.LENGTH_LONG).show();
                                }
                                else { // если уже есть аккаунт с таким логином/телефоном
                                    Toast.makeText(getContext(), "Ошибка сервера " + response_code, Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Teacher> call, Throwable t) { // если что-то с подключением/самим сервером и тд
                                Toast.makeText(getContext(), "Не удалось завершить регистрацию! Проблемы с сервером.", Toast.LENGTH_LONG).show();
                                Log.d("SERVER_ERROR", ""+t);
                            }});
                }
            }
        });
        return view;
    }
}